package com.example.Fx.controller;

import com.example.Fx.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fx")
@Slf4j
public class AccountPaymentController {

    @PostMapping("/pay")
    public String pay(@RequestBody PaymentDto paymentDto) {
        log.info("AccountPaymentController pay() method ");
        log.info("Account id {} , amount {}", paymentDto.getId(), paymentDto.getAmount());

        return "success";
    }
}

//'3'
//'1'
//'23'
//'13'
//'19'
//'21'
//'15'
//'8'
//'10'
//'7'
//'11'
//'6'
//'5'
//'18'
//'4'
//'2'
//'22'
//'20'
//'16'
//'14'
//'17'