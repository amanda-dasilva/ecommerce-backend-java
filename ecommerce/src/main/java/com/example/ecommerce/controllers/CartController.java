package com.example.ecommerce.controllers;

import com.example.ecommerce.service.AuthenticationService;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    CategoryService categoryService;
    ProductService productService;
    AuthenticationService authenticationService;

    @Autowired
    public CartController(CategoryService categoryService, ProductService productService,
                          AuthenticationService authenticationService){
        this.categoryService = categoryService;
        this.productService = productService;
        this.authenticationService = authenticationService;
    }
}
