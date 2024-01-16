package com.example.ecommerce.dto.checkout;

public class CheckoutItemDto {
    private String productName;
    private Integer  quantity;
    private double price;
    private Integer productId;

    public CheckoutItemDto() {
    }

    public CheckoutItemDto(String productName, int quantity, double price, Integer productId) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
