package com.example.ecommerce.service;

import com.example.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    CartRepository cartRepository;
    @Autowired
    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }
}
