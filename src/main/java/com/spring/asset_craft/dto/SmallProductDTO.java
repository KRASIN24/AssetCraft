package com.spring.asset_craft.dto;

import com.spring.asset_craft.entity.ProductImage;

import java.util.List;

public class SmallProductDTO
{
    private int id;
    private String name;
    private float price;
    private float rating;
    private String category;

    //TODO: Change list to string
    private List<ProductImage> imgPath;
    private String owner;


    public SmallProductDTO() {
    }


    public SmallProductDTO(int id, String name, float price, float rating, String category, List<ProductImage> imgPath, String owner) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.category = category;
        this.imgPath = imgPath;
        this.owner = owner;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Override
    public String toString() {
        return "SmallProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", category='" + category + '\'' +
                ", imgPath=" + imgPath +
                ", owner='" + owner + '\'' +
                '}';
    }
}
