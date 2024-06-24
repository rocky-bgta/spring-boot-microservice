package com.line.reflection.repository;


import com.line.reflection.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findByOrderId(String orderId);
}
