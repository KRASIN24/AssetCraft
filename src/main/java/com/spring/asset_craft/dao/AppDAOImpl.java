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

//    @Override
//    public Product findProductById(int id) {
//        TypedQuery<Product> query = entityManager.createQuery(
//                "SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id =:data", Product.class);
//        query.setParameter("data", id);
//        Product product = query.getSingleResult();
//            return product;
//    }



}
