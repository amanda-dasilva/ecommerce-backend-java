package com.example.ecommerce.service;

import com.example.ecommerce.dto.user.SignUpResponseDto;
import com.example.ecommerce.dto.user.SignupDto;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public SignUpResponseDto signUp(SignupDto signupDto) {
    }
}
