package com.javatechie.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.dto.OrderResponseDTO;
import com.javatechie.dto.PaymentDTO;
import com.javatechie.dto.UserDTO;
import com.javatechie.entity.Order;
import com.javatechie.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
//@RefreshScope
public class OrderService {

    public static final String ORDER_SERVICE = "orderService";
    @Autowired
    private OrderRepository repository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${order.producer.topic.name}")
    private String topicName;

    @Autowired
   // @Lazy
    private RestTemplate restTemplate;

//    @Value("${microservice.payment-service.endpoints.fetchPaymentById.uri}")
//    private String fetchPaymentUri;
//
//    @Value("${microservice.user-service.endpoints.fetchUserById.uri}")
//    private String fetchUserUri;
//
//    @Value("${test.input}")
//    private String testValue;

    public String placeAnOrder(Order order) {
        //save a copy in order-service DB

        order.setPurchaseDate(new Date());
        order.setOrderId(UUID.randomUUID().toString().split("-")[0]);
        repository.save(order);
        //send it to payment service using kafka
        try {
            kafkaTemplate.send(topicName, new ObjectMapper().writeValueAsString(order));
        } catch (JsonProcessingException e) {
            e.printStackTrace();//log statement log.error
        }
        return "Your order with (" + order.getOrderId() + ") has been placed ! we will notify once it will confirm";
    }

//    @CircuitBreaker(name = ORDER_SERVICE, fallbackMethod = "getOrderDetails")
//    public OrderResponseDTO getOrder(String orderId) {
//        //own DB -> ORDER
//        System.out.println("****** "+testValue);
//        System.out.println("fetchPaymentUri : "+fetchPaymentUri +" && "+"fetchUserUri: "+fetchUserUri);
//        Order order = repository.findByOrderId(orderId);
//        //PAYMENT-> REST call payment-service
//        PaymentDTO paymentDTO = restTemplate.getForObject(fetchPaymentUri + orderId, PaymentDTO.class);
//        //user-info-> rest call user-service
//        UserDTO userDTO = restTemplate.getForObject(fetchUserUri + order.getUserId(), UserDTO.class);
//        return OrderResponseDTO.builder()
//                .order(order)
//                .paymentResponse(paymentDTO)
//                .userInfo(userDTO)
//                .build();
//
//    }

    public OrderResponseDTO getOrder(String orderId) {
        //you can call a DB
        Order order = repository.findByOrderId(orderId);
        //you can invoke external api
        PaymentDTO paymentDTO = restTemplate.getForObject("http://PAYMENT-SERVICE/payments/" + orderId, PaymentDTO.class);
        //return new OrderResponseDTO("FAILED", null, null, null);
        UserDTO userDTO = restTemplate.getForObject("http://USER-SERVICE/users/" + order.getUserId(), UserDTO.class);
        return OrderResponseDTO.builder()
                .order(order)
                .paymentResponse(paymentDTO)
                .userInfo(userDTO)
                .build();
    }


}
