package com.spring.asset_craft.repository;

import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.entity.ProductUser.ProductUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductUserRepository extends JpaRepository<ProductUser, Long> {

    @Query(value="SELECT pu FROM ProductUser pu WHERE pu.product.id = :productId AND pu.user.username = :username")
    Optional<ProductUser> findProductInCartByProductId(@Param("productId") Long productId, @Param("username") String username);

    @Query("SELECT COUNT(pu)>0 FROM ProductUser pu WHERE pu.product.id = :productId AND pu.user.id = :userId AND pu.status = :status")
    boolean hasStatus(@Param("productId") Long productId, @Param("userId") Long userId, @Param("status") ProductUserStatus status);

    @Query("SELECT u.username " +
            "FROM ProductUser pu " +
            "JOIN pu.user u " +
            "WHERE pu.product.id = :productId " +
            "AND pu.status = com.spring.asset_craft.entity.ProductUser.ProductUserStatus.OWNER")
    Optional<String> getOwnerUsername(@Param("productId") Long productId);

    @Query("SELECT u.id " +
            "FROM ProductUser pu " +
            "JOIN pu.user u " +
            "WHERE pu.product.id = :productId " +
            "AND pu.status = com.spring.asset_craft.entity.ProductUser.ProductUserStatus.OWNER")
    Long getOwnerId(@Param("productId") Long productId);

    @Modifying
    @Transactional
    @Query("UPDATE ProductUser pu SET pu.status = com.spring.asset_craft.entity.ProductUser.ProductUserStatus.BUYER "+
            "WHERE pu.product.id IN :productsIds AND pu.status = com.spring.asset_craft.entity.ProductUser.ProductUserStatus.CART")
    void updateProductStatus(List<Long> productsIds);

    @Query(value = "SELECT new com.spring.asset_craft.entity.Product(p.id, p.name, p.price, p.category, p.description) " +
            "FROM ProductUser pu " +
            "JOIN pu.product p " +
            "JOIN pu.user u " +
            "WHERE pu.status = :status AND u.username = :username")
    List<Product> findProductsWithStatusByUser(@Param("username") String username, @Param("status") ProductUserStatus status);

}


