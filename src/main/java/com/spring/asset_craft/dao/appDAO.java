package com.spring.asset_craft.dao;

import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.Review;
import com.spring.asset_craft.entity.User;

import java.util.List;

public interface appDAO {

    // TODO: Finish SQL script
    // -----------USER--------------

    // USER METHODS

    public void addUser(User user);

    public User findUserById(int id);

    public void updateUser(User user);

    public void deleteUser(User user);

    // PRODUCT METHODS

    public void addProduct(Product product);

    public Product findProductById(int id);

    public Product findProductByName(String name);

    public void updateProduct(Product product);

    public void deleteProduct(Product product);

    // REVIEW METHODS

    public void addReview(Review review);

    public Review findReviewByProductId(int id);

    public void updateReview(Review review);

    public void deleteReview(Review review);

    // OTHER METHODS
    public void addProductToUser(int userId, int productId, String status);

    public void removeProductFromUser(int userId, int productId);

    // -------------ADMIN----------------

    public List<User> getAllUsers();

    public List<Review> getAllReviews();
}
