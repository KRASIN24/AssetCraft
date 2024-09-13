package com.spring.asset_craft.dto;

public class ReviewDTO {

    private String comment;
    private float rating;
    private String username;

    private String productName;

    private Long productId;

    public ReviewDTO(String comment, float rating, String username) {
        this.comment = comment;
        this.rating = rating;
        this.username = username;
    }

    public ReviewDTO(String comment, float rating, String username, String productName, Long productId) {
        this.comment = comment;
        this.rating = rating;
        this.username = username;
        this.productName = productName;
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "comment='" + comment + '\'' +
                ", rating=" + rating +
                ", username='" + username + '\'' +
                ", productName='" + productName + '\'' +
                ", productId=" + productId +
                '}';
    }
}
