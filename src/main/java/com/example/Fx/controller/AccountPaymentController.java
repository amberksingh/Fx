package com.example.Fx.controller;

import com.example.Fx.dto.PaymentDto;
import com.example.Fx.feign.PaymentClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.example.Fx.util.Constants.FX_PAYMENT_SERVICE;

@RestController
@RequestMapping("/fx")
@Slf4j
public class AccountPaymentController {

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${payment.service.url}")
    private String fx_paymentBaseUrl;

    @PostMapping("/pay")
    public String pay(@RequestBody PaymentDto paymentDto) {
        log.info("AccountPaymentController pay() method ");
        log.info("Account id {} , amount {}", paymentDto.getId(), paymentDto.getAmount());

        return paymentClient.pay(paymentDto);
        //return "success";
    }

//With circuitbreaker disabled and only timeouts configured, this is what will happen when payment service is down:
//
//spring:
//  cloud:
//    openfeign:
//      client:
//        config:
//          payment-service:
//            connectTimeout: 2000
//            readTimeout: 3000
//Case 1: Payment service is DOWN (not running / connection refused)
//
//Example: payment.service.url = http://localhost:8081 but payment app is stopped.
//
//Feign tries to connect.
//
//OS immediately returns Connection refused (often very fast, < 2s).
//
//Feign throws an exception (typically FeignException / RetryableException wrapping ConnectException).
//
//Your Account controller will fail the request unless you handle it.
//
//Result to client: usually HTTP 500 (or 502/503 if you map it) with stacktrace in logs.
//
//✅ connectTimeout=2000 is basically the max time Feign will wait to establish a connection, but “connection refused” usually fails faster.
//
//Case 2: Payment URL is wrong / host not reachable (hangs)
//
//Example: wrong IP, firewall drop, DNS issue.
//
//Feign attempts connection.
//
//It may “hang” until connect timeout hits.
//
//After 2 seconds, it fails with a connect timeout exception.
//
//Result: Account request fails after ~2s.
//
//Case 3: Payment service is UP but endpoint is slow/hangs
//
//Connection succeeds quickly.
//
//Payment takes too long to respond.
//
//After 3 seconds, Feign fails with a read timeout exception.
//
//Result: Account request fails after ~3s.
//
//Will fallback run (without CB)?
//
//✅ Yes, fallback can still run even without circuit breaker, IF you attached it in @FeignClient.
//
//Example:
//
//@FeignClient(
//  name = "payment-service",
//  url = "${payment.service.url}",
//  fallback = FeignFallBack.class
//)
//public interface PaymentClient {
//  @PostMapping("/fx-payment/pay")
//  String pay(@RequestBody PaymentDto paymentDto);
//}
//If fallback is configured:
//
//Payment down → Feign call throws → fallback method executes → returns your fallback string
//
//Account endpoint returns 200 with that string (unless you change it)
//
//If fallback is NOT configured:
//
//Payment down → exception propagates → Account returns 500
//
//Important “real life” warning
//
//If your fallback returns a normal success-looking string, your Account service might treat it as success.
//
//For payment flows, better to return something like:
//
//"payment service unavailable" and map to 503 OR
//
//return a structured response with status FAILED



    //======using @CircuitBreaker from resilience4j explicitly and fallbackMethod scenario
    @PostMapping("/payCircuitBreakerAnnotation")
    @CircuitBreaker(name = FX_PAYMENT_SERVICE, fallbackMethod = "paymentFallback")
    public String payCircuitBreakerAnnotation(@RequestBody PaymentDto paymentDto) {
        log.info("CB RestTemplate way..");
        log.info("CB AccountPaymentController payCircuitBreakerAnnotation() method ");
        log.info("CB -> Account id {} , amount {}", paymentDto.getId(), paymentDto.getAmount());
        String restPaymentUrl = fx_paymentBaseUrl + "/fx-payment/pay";
        log.info("CB hitting fx-payment service : {}", restPaymentUrl);
        String response = restTemplate.postForObject(restPaymentUrl, paymentDto, String.class);
        log.info("CB fx-payment response = {}", response);
        return response;
    }

    public String paymentFallback(PaymentDto paymentDto, Throwable t) {
        log.info("Inside fallback method for @CircuitBreaker : {}", t.getMessage());
        return "paymentFallback : Payment-service TIMED OUT for paymentDto: " + paymentDto;
    }

    //======  resilience4j urls ==========
    //http://localhost:8080/actuator -> shows available endpoints
    //http://localhost:8080/actuator/info
    //http://localhost:8080/actuator/health
    //http://localhost:8080/actuator/circuitbreakersevents
    //http://localhost:8080/actuator/circuitbreakers
    //http://localhost:8080/actuator/retries

    //=======only @CircuitBreaker alone====
    //scenario 1: Timeout configured in restTemplate .If payment down , then instant fallback method
    //scenario 2: Timeout configured in restTemplate .If payment takes 6000ms , then wait for 4sec then fallback method

    //scenario 3: Timeout NOT configured in restTemplate .If payment takes 6000ms , then wait for full response i.e 6000ms
    // and No fallback method
    //scenario 4: Timeout NOT configured in restTemplate .If payment down , then instant fallback method





    //======using @Retry from resilience4j explicitly and retryPaymentFallback scenario
    int counter = 1;

    @PostMapping("/payRetryAnnotation")
    @Retry(name = FX_PAYMENT_SERVICE, fallbackMethod = "retryPaymentFallback")
    public ResponseEntity<String> payRetryAnnotation(@RequestBody PaymentDto paymentDto) {
        log.info("======attempt===== : {}", counter++);
        log.info("retry RestTemplate way..");
        log.info("retry AccountPaymentController payRetryAnnotation() method ");
        log.info("retry -> Account id {} , amount {}", paymentDto.getId(), paymentDto.getAmount());
        String restPaymentUrl = fx_paymentBaseUrl + "/fx-payment/pay";
        log.info("retry hitting fx-payment service : {}", restPaymentUrl);
        ResponseEntity<String> response = restTemplate.postForEntity(restPaymentUrl, paymentDto, String.class);
        log.info("retry fx-payment response = {}", response);
        return response;
    }

    public ResponseEntity<String> retryPaymentFallback(PaymentDto paymentDto, Throwable t) {
        log.info("Inside retryPaymentFallback method for @Retry : {}", t.getMessage());
        //return "paymentFallback : Payment-service TIMED OUT for paymentDto: " + paymentDto;
        return ResponseEntity
                .status(503) // or 504
                .body("payment service unavailable after retries. paymentDto=" + paymentDto);
    }

    //=======only @Retry alone====
    //        maxAttempts: 3                   # total attempts (1 initial + 2 retries)
    //        waitDuration: 5s                 # delay between retries
    //scenario 1: Timeout configured in restTemplate .If payment down , then retries 3 times and then fallback

    //scenario 2 : readTimeout = 5000ms
    //
    //Then each attempt:
    //
    //connects fast
    //
    //waits up to 5s
    //
    //times out at 5s (because payment responds at 7s)
    //
    //retry waits 5s (waitDuration)
    //
    //repeat…
    //
    //So approximate total time:
    //
    //Attempt1: 5s (timeout) + 5s wait
    //
    //Attempt2: 5s (timeout) + 5s wait
    //
    //Attempt3: 5s (timeout)
    //→ then fallback
    //
    //✅ Total ≈ 15s timeout time + 10s wait time = ~25s (plus small overhead)

    //Scenario 3
    //
    //Timeout NOT configured in RestTemplate + payment down → “retries 3 times then fallback”
    //
    //✅ This is true if the failure happens quickly per attempt, like:
    //
    //Connection refused (service stopped on localhost) → fails immediately
    //
    //DNS error → fails quickly
    //
    //Then Retry will do 3 attempts + waits, then fallback.
    //
    //❌ But if “down” means host unreachable / packets dropped (blackhole), and you have no connectTimeout, each attempt can hang for a long OS-level TCP timeout (can be many seconds). So you will retry, but each attempt may take a long time, making total time huge.
    //
    //So the logic “retries 3 times” is right, but the “down = instant failure” isn’t always true without timeouts.
    //
    //Scenario 4
    //
    //Timeout NOT configured + payment takes 7s
    //
    //✅ Correct:
    //
    //Call succeeds after ~7 seconds
    //
    //No exception is thrown
    //
    //Retry does not retry (because Retry only triggers on failure/exception)
    //
    //So it returns success response
    //
    //One-liner rule
    //
    //Retry happens only on exception.
    //
    //Timeouts are what turn slow calls into exceptions.
    //
    //Without timeouts, slow calls just “wait” and return → no retry.

    @GetMapping("/getBalanceById/{id}")
    public Double getBalance(@PathVariable("id") Long accId) {
        log.info("getBalance() method for accountId {}", accId);
        //log.info("Current correlationId from MDC = {}", MDC.get("correlationId"));

//        Spring replaces {accId} automatically.
//        URI way
//        String url = fx_paymentBaseUrl + "/fx-payment/getBalanceById/{accId}";
//        Map<String, Long> uriVariables = Map.of("accId", accId);
//        Double balance = restTemplate.getForObject(url, Double.class, uriVariables);

        //2nd way
        String url = fx_paymentBaseUrl + "/fx-payment/getBalanceById/{accId}";
        log.info("hitting url : {}", url);

        Double balance = restTemplate.getForObject(url, Double.class, accId);
        log.info("Balance for id {} = {}", accId, balance);
        return balance;
    }
}