package com.example.ecommerce.service;

import com.example.ecommerce.config.MessageStrings;
import com.example.ecommerce.exceptions.AuthenticationFailException;
import com.example.ecommerce.model.AuthenticationToken;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AuthenticationService {
    private final TokenRepository repository;

    @Autowired
    public AuthenticationService(TokenRepository repository) {
        this.repository = repository;
    }

    // Save the confirmation token
    @Transactional
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    // Retrieve token for the User
    public AuthenticationToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    // Retrieve User from the token
    public User getUser(String token) {
        AuthenticationToken authenticationToken = repository.findTokenByToken(token);
        return (authenticationToken != null) ? authenticationToken.getUser() : null;
    }

    // Check if the token is valid
    public void authenticate(String token) throws AuthenticationFailException {
        if (token == null) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }

        if (getUser(token) == null) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID + ": " + token);
        }
    }
}
