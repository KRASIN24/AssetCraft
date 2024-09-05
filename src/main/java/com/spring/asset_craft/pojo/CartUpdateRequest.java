package com.spring.asset_craft.pojo;

import java.util.List;

public class CartUpdateRequest {

    private List<Long> productIds;

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
