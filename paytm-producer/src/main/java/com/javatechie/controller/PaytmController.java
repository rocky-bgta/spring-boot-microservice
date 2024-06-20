package com.javatechie.controller;

import com.javatechie.dto.PaymentRequest;
import com.javatechie.dto.PaytmRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
public class PaytmController {


    @Value("${paytm.producer.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/publish/{message}")
    public void sendMessage(@PathVariable String message) {
        for(int i =0; i<=100; i++ ) {
            kafkaTemplate.send(topicName, message+i);
        }
    }

    @PostMapping("/paytm/payment")
    public String doPayment(@RequestBody PaytmRequest<PaymentRequest> paytmRequest) {
        PaymentRequest paymentRequest = paytmRequest.getPayload();
        paymentRequest.setTransactionId(UUID.randomUUID().toString());
        paymentRequest.setTxDate(new Date());

        kafkaTemplate.send(topicName, paytmRequest);
        return "payment instantiate successfully";
    }

}
