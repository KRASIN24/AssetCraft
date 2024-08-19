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
    public Product findProductById(int id) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id =:data", Product.class);
        query.setParameter("data", id);
        Product product = query.getSingleResult();
        if (product != null) {
            return product;
        }
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
}
