package com.line.reflection.repository;


import com.line.reflection.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Payment findByOrderId(String orderId);

}
