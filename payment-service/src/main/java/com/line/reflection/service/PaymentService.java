package com.line.reflection.service;


import com.line.reflection.entity.Payment;
import com.line.reflection.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    public Payment getByOrderId(String orderId) {
        return repository.findByOrderId(orderId);
    }
}
