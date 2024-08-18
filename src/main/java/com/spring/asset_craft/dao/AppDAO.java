package com.spring.asset_craft.dao;

import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.Review;
import com.spring.asset_craft.entity.User;

import java.util.List;

 public interface AppDAO {

    // TODO: Finish SQL script
    // -----------USER--------------

    // USER METHODS

     void addUser(User user);

     User findUserById(int id);

     void updateUser(User user);

     void deleteUser(User user);

    // PRODUCT METHODS

     void addProduct(Product product);

     Product findProductById(int id);

     List<Product> findProductByName(String name);

     String getOwnerUsername(int productId);

     void updateProduct(Product product);

     void deleteProduct(Product product);

    // REVIEW METHODS

     void addReview(Review review);

     List<Review> findReviewByProductId(int id);

     void updateReview(Review review);

     void deleteReview(Review review);

    // OTHER METHODS
     void addProductToUser(int userId, int productId, String status);

     void removeProductFromUser(int userId, int productId);

    // -------------ADMIN----------------

     List<User> getAllUsers();

     List<Review> getAllReviews();
}
