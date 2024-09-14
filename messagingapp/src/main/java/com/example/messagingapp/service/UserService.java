package com.example.messagingapp.service;

import com.example.messagingapp.dto.UserDTO;
import com.example.messagingapp.model.User;
import com.example.messagingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return userDTO;
    }

    public String loginUser(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null && passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            return "Login Successful!";
        }
        return "Invalid Credentials";
    }
}
