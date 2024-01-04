package com.example.ecommerce.exceptions;

public class CartItemNotExistException extends Exception{
    public CartItemNotExistException(String msg) {
        super(msg);
    }
}
