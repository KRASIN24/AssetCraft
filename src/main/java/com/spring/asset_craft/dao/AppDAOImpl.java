package com.spring.asset_craft.dao;

import com.spring.asset_craft.dto.ReviewDTO;
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
    public Product findProductById(int id) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id =:data", Product.class);
        query.setParameter("data", id);
        Product product = query.getSingleResult();
            return product;
    }

    @Override
    public List<Product> findProductByName(String name) {

        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE LOWER(p.name) LIKE LOWER(:data)", Product.class);
        query.setParameter("data", "%" + name.toLowerCase() + "%");
        List<Product> products = query.getResultList();
            return products;
    }

    public String getOwnerUsername(int productId) {
        String queryString = """
    SELECT u.username
    FROM AssociationProductUser pu
    JOIN pu.user u
    WHERE pu.product.id = :productId
      AND pu.status = com.spring.asset_craft.entity.AssociationProductUser.ProductUserStatus.OWNER
    """;
        TypedQuery<String> query = entityManager.createQuery(queryString, String.class);
        query.setParameter("productId", productId);
        try{
            String username = query.getSingleResult();
            return username;
        }catch (Exception e){
            return "";
        }
    }

    @Override
    public List<ReviewDTO> getProductReviews(int id) {
        String querryString = """
                SELECT new com.spring.asset_craft.dto.ReviewDTO(r.comment, r.rating, u.username)
                FROM Review r JOIN r.user u
                WHERE r.product.id = :data
                """;
        TypedQuery<ReviewDTO> query = entityManager.createQuery(querryString, ReviewDTO.class);
        query.setParameter("data", id);
        List<ReviewDTO> reviews = query.getResultList();
            return reviews;

    }

    @Override
    public User findUserByUsername(String username) {

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE LOWER(p.name) LIKE LOWER(:data)", User.class);
        query.setParameter("data", "%" + username.toLowerCase() + "%");
        User user = query.getSingleResult();
        return user;
    }
}
