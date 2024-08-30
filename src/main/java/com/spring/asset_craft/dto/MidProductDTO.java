package com.spring.asset_craft.dto;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MidProductDTO {

    private int productId;
    private String productName;
    private float productPrice;
    private String ownerUsername;
    private float rating;
    private String category;
    private String description;

    private List<String> paths;

    public MidProductDTO(int productId, String productName, float productPrice, String ownerUsername, float rating, String category, String description) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.ownerUsername = ownerUsername;
        this.rating = rating;
        this.category = category;
        this.description = description;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPath() {
        return paths;
    }

    public void setPath(List<String> paths) {
        this.paths = paths;
    }
}
