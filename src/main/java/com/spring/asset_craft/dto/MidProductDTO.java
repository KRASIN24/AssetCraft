package com.spring.asset_craft.dto;

public class MidProductDTO {
    private String productName;
    private float productPrice;
    private String ownerUsername;

    public MidProductDTO(String productName, float productPrice, String ownerUsername) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.ownerUsername = ownerUsername;
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
}
