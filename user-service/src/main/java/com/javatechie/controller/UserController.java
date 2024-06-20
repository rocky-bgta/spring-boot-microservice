package com.javatechie.controller;

import com.javatechie.entity.User;
import com.javatechie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private int port;


    @PostMapping
    public User registerNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        System.out.println("request is landed on port : " + port);
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}/{amount}")
    public User updateUserBalance(@PathVariable int userId, @PathVariable double amount) {
        return userService.updateAccountStatus(userId, amount);
    }
}
