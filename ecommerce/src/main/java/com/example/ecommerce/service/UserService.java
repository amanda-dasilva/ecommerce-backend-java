package com.example.ecommerce.service;

import com.example.ecommerce.dto.user.SignUpResponseDto;
import com.example.ecommerce.dto.user.SignupDto;
import com.example.ecommerce.exceptions.CustomException;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public SignUpResponseDto signUp(SignupDto signupDto) throws CustomException {
        // Check to see if the current email address has already been registered.
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            // If the email address has been registered then throw an exception.
            throw new CustomException("User already exists");
        }
        // first encrypt the password
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("Hashing password failed {}", e.getMessage());
        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), encryptedPassword );
        try {
            // save the User
            userRepository.save(user);
            // success in creating
            return new SignUpResponseDto("success", "User created successfully");
        } catch (Exception e) {
            // handle signup error
            throw new CustomException(e.getMessage());
        }
    }
    // Method to hash a password using the SHA-256 algorithm
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        // Get an instance of the SHA-256 message digest
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // Update the digest with the bytes of the password using UTF-8 encoding
        byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
        // Convert the byte array to a hexadecimal string
        return bytesToHex(digest);
    }
    // Method to convert a byte array to a hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        // Use a StringBuilder to efficiently build the hexadecimal string
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        // Iterate over each byte in the array
        for (byte b : bytes) {
            // Convert each byte to a two-digit hexadecimal representation and append to the StringBuilder
            hexStringBuilder.append(String.format("%02X", b));
        }
        // Return the final hexadecimal string
        return hexStringBuilder.toString();
    }
}
