package com.example.messagingapp.controller;

import com.example.messagingapp.dto.UserDTO;
import com.example.messagingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserDTO userDTO) {
        return userService.loginUser(userDTO);
    }
}
