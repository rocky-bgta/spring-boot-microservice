package com.javatechie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderServiceConfig {

    @Bean
   // @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}