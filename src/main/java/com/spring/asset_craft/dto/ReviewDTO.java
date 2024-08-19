package com.spring.asset_craft.dto;

public class ReviewDTO {

    private String comment;
    private float rating;
    private String username;

    public ReviewDTO(String comment, float rating, String username) {
        this.comment = comment;
        this.rating = rating;
        this.username = username;
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
}
