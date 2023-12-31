package com.example.ecommerce.service;

import com.example.ecommerce.config.MessageStrings;
import com.example.ecommerce.dto.user.SignInDto;
import com.example.ecommerce.dto.user.SignInResponseDto;
import com.example.ecommerce.dto.user.SignUpResponseDto;
import com.example.ecommerce.dto.user.SignupDto;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    public SignUpResponseDto signUp(SignupDto signupDto) throws CustomException {
        // Check if the current email address has already been registered.
        if (userRepository.findByEmail(signupDto.getEmail()) != null) {
            throw new CustomException(MessageStrings.USER_ALREADY_EXISTS);
        }

        // Encrypt the password using BCrypt
        String hashedPassword = BCrypt.hashpw(signupDto.getPassword(), BCrypt.gensalt());

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getEmail(), hashedPassword);

        try {
            // Save the User and generate token
            userRepository.save(user);
            AuthenticationToken authenticationToken = new AuthenticationToken(user);
            authenticationService.saveConfirmationToken(authenticationToken);
            return new SignUpResponseDto("success", "User created successfully");
        } catch (Exception e) {
            // Handle signup error
            throw new CustomException(e.getMessage());
        }
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws AuthenticationFailException, CustomException {
        // Find User by email
        User user = userRepository.findByEmail(signInDto.getEmail());

        if (user == null) {
            throw new AuthenticationFailException(MessageStrings.USER_NOT_PRESENT);
        }

        try {
            // Check if password is correct using BCrypt
            if (!BCrypt.checkpw(signInDto.getPassword(), user.getPassword())) {
                throw new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (Exception e) {
            logger.error("Hashing password failed", e);
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (token == null) {
            throw new CustomException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }

        return new SignInResponseDto("success", token.getToken());
    }
}
