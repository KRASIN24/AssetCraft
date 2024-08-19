package com.spring.asset_craft.dto;

import com.spring.asset_craft.entity.ProductImage;
import com.spring.asset_craft.entity.Review;
import com.spring.asset_craft.entity.User;

import java.util.List;

public class BigProductDTO {

    private int id;
    private String name;
    private float price;
    private float rating;
    private List<ProductImage> imgPath;
    private String owner;
    private String description;
    private String category;
    private List<ReviewDTO> reviews;
    private List<ProductImage> productImages;

    public BigProductDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<ProductImage> getImgPath() {
        return imgPath;
    }

    public void setImgPath(List<ProductImage> imgPath) {
        this.imgPath = imgPath;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
