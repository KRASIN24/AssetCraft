package com.spring.asset_craft.repository;

import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.ProductUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductUserRepository extends JpaRepository<ProductUser, Long> {

    @Query(value = "SELECT new com.spring.asset_craft.entity.Product(p.id, p.name, p.price, p.category, p.description, p.rating) " +
            "FROM ProductUser pu " +
            "JOIN pu.product p " +
            "JOIN pu.user u " +
            "WHERE pu.status = 'CART' AND u.username = :username")
    List<Product> findCartProductsByUser(@Param("username") String username);

    @Query(value="SELECT pu FROM ProductUser pu WHERE pu.product.id = :productId AND pu.user.username = :username")
    Optional<ProductUser> findCartProductByProductId(@Param("productId") int productId, @Param("username") String username);

    @Query("SELECT COUNT(pu)>0 FROM ProductUser pu WHERE pu.product.id = :productId AND pu.user.id = :userId AND pu.status = com.spring.asset_craft.entity.ProductUser.ProductUserStatus.CART")
    boolean inCart(@Param("productId") int productId, @Param("userId") int userId);

    @Query("SELECT u.username " +
            "FROM ProductUser pu " +
            "JOIN pu.user u " +
            "WHERE pu.product.id = :productId " +
            "AND pu.status = com.spring.asset_craft.entity.ProductUser.ProductUserStatus.OWNER")
    Optional<String> getOwnerUsername(@Param("productId") int productId);


    //boolean existsByProductAndUserAndStatus(int productId, int userId, ProductUser.ProductUserStatus status);

    @Query("SELECT COUNT(pu)>0 FROM ProductUser pu WHERE pu.product.id = :productId AND pu.user.id = :userId AND pu.status = :status")
    boolean alreadyExists(@Param("productId") int productId, @Param("userId") int userId, @Param("status") ProductUser.ProductUserStatus status);
}


