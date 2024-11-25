package com.spring.asset_craft.dto;

import com.spring.asset_craft.entity.ProductImage;

import java.util.List;

public class ProductDTO {

    private Long id;
    private String name;
    private float price;
    private Double rating;
    private String ownerUsername;
    private String description;
    private String category;
    private List<ReviewDTO> reviews;
    private List<ProductImage> productImages;
    private boolean isOwner;
    private boolean inCart;
    private boolean isBought;

    private int whishlistAmout = 0;

    // Small DTO
    public ProductDTO(Long id, String name, float price, Double rating, String category, List<ProductImage> productImages,
                      String ownerUsername, boolean isOwner, boolean inCart, boolean isBought) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.category = category;
        this.productImages = productImages;
        this.ownerUsername = ownerUsername;
        this.isOwner = isOwner;
        this.inCart = inCart;
        this.isBought = isBought;
    }

    public ProductDTO(Long id, String name, float price, String ownerUsername, Double rating, String category, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ownerUsername = ownerUsername;
        this.rating = rating;
        this.category = category;
        this.description = description;
    }

    // Big DTO
    public ProductDTO(Long id, String name, float price, Double rating, String ownerUsername, String description, String category, List<ReviewDTO> reviews, List<ProductImage> productImages) {
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
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

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }


    public int getWhishlistAmout() {
        return whishlistAmout;
    }

    public void setWhishlistAmout(int whishlistAmout) {
        this.whishlistAmout = whishlistAmout;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", ownerUsername='" + ownerUsername + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", reviews=" + reviews +
                ", productImages=" + productImages +
                ", isOwner=" + isOwner +
                ", inCart=" + inCart +
                ", isBought=" + isBought +
                '}';
    }
}
