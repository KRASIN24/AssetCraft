package com.spring.asset_craft.dto;

import com.spring.asset_craft.entity.ProductImage;
import com.spring.asset_craft.entity.Review;
import com.spring.asset_craft.entity.User;

import java.util.List;

public class BigProductDTO extends SmallProductDTO {

    private String description;
    private String category;
    private List<Review> reviews;
    private List<ProductImage> productImages;

    public BigProductDTO(int id, String name, float price, float rating, List<ProductImage> imgPath, String owner) {
        super(id, name, price, rating, imgPath, owner);
    }
}
