package com.spring.asset_craft.dto;

import com.spring.asset_craft.entity.ProductImage;

import java.util.List;

public class ProductDTO {

    private Long id;
    private String name;
    private float price;
    private float rating;
    private String ownerUsername;
    private String description;
    private String category;
    private List<ReviewDTO> reviews;
    private List<ProductImage> productImages;

    public ProductDTO(Long id, String name, float price, float rating, String category, List<ProductImage> productImages, String ownerUsername) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.category = category;
        this.productImages = productImages;
        this.ownerUsername = ownerUsername;
    }

    public ProductDTO(Long id, String name, float price, String ownerUsername, float rating, String category, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ownerUsername = ownerUsername;
        this.rating = rating;
        this.category = category;
        this.description = description;
    }

    public ProductDTO(Long id, String name, float price, float rating, String ownerUsername, String description, String category, List<ReviewDTO> reviews, List<ProductImage> productImages) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.ownerUsername = ownerUsername;
        this.description = description;
        this.category = category;
        this.reviews = reviews;
        this.productImages = productImages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }
}
