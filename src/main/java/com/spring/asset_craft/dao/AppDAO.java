package com.spring.asset_craft.dao;

import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.Review;
import com.spring.asset_craft.entity.User;

import java.util.List;

 public interface AppDAO {

    // TODO: Finish SQL script

     //Function to get data for product page
     Product findProductById(int id);

     List<Product> findProductByName(String name);

     String getOwnerUsername(int productId);


}
