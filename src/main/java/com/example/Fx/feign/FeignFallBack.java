package com.example.Fx.feign;

import com.example.Fx.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignFallBack implements PaymentClient{

    @Override
    public String pay(PaymentDto paymentDto) {
        log.warn("Feign fallback for pay() method PaymentDto : {}", paymentDto);
        return "Feign fallback for pay() method";
    }
}
