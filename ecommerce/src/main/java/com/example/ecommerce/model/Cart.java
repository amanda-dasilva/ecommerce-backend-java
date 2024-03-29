package com.example.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_date")
    private Date createdDate;
    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    @JsonIgnore
    private Product product;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @JsonIgnore
    private User user;
    private Integer quantity;
    public Cart() {

    }
    public Cart(Product product, User user, Integer quantity) {
        this.product = product;
        this.user = user;
        this.createdDate = new Date();
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
