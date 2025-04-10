package com.continuo.ecommerce.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "reviews")

public class Reviews {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String Comment;

    @ManyToOne
    private Products product;

    @ManyToOne
    private User customer;

    private LocalDateTime createdAt;

    public Reviews() {
    }

    public Reviews(Long id, int rating, String comment, Products product, User customer, LocalDateTime createdAt) {
        this.id = id;
        this.rating = rating;
        Comment = comment;
        this.product = product;
        this.customer = customer;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
