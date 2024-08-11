package com.spring.asset_craft.entity;

public class Review {

    private int id;
    private String comment;
    private int rating;
    private int userId;
    private int productId;

    public Review() {
    }

    public Review(int id, String comment, int rating, int userId, int productId) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.userId = userId;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", userId='" + userId + '\'' +
                ", productId=" + productId +
                '}';
    }
}
