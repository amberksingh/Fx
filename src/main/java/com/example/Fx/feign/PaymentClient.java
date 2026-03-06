package com.example.Fx.feign;

import com.example.Fx.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.example.Fx.util.Constants.FX_PAYMENT_SERVICE;

@FeignClient(
        name = FX_PAYMENT_SERVICE,
        url = "${payment.service.url}",
        //url = "http://10.255.255.1:8081",//to simulate connectTimeout: 4000
        fallback = FeignFallBack.class
)
public interface PaymentClient {

    @PostMapping("/fx-payment/pay")
    String pay(@RequestBody PaymentDto paymentDto);
}
