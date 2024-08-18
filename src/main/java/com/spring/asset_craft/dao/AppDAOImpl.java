package com.spring.asset_craft.dao;

import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.Review;
import com.spring.asset_craft.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {
    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void addUser(User user) {

    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public Product findProductById(int id) {
        return null;
    }

    @Override
    public List<Product> findProductByName(String name) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE LOWER(p.name) LIKE LOWER(:data)", Product.class);
        query.setParameter("data", "%" + name.toLowerCase() + "%");
        List<Product> products = query.getResultList();
        if (products != null) {
            return products;
        }
        return null;
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(Product product) {

    }

    @Override
    public void addReview(Review review) {

    }

    @Override
    public List<Review> findReviewByProductId(int id) {
        return null;
    }

    @Override
    public void updateReview(Review review) {

    }

    @Override
    public void deleteReview(Review review) {

    }

    @Override
    public void addProductToUser(int userId, int productId, String status) {

    }

    @Override
    public void removeProductFromUser(int userId, int productId) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<Review> getAllReviews() {
        return null;
    }
}
