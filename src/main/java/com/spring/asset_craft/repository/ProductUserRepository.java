package com.spring.asset_craft.repository;

import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.entity.AssociationProductUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUserRepository extends JpaRepository<AssociationProductUser, Long> {

    @Query(value = "SELECT new com.spring.asset_craft.dto.ProductDTO(p.id, p.name, p.price, u.username, p.rating, p.category, p.description) " +
            "FROM AssociationProductUser a " +
            "JOIN a.product p " +
            "JOIN a.user u " +
            "WHERE a.status = 'CART' AND u.username = :username")
    List<ProductDTO> findCartProductsByUser(@Param("username") String username);

    @Query(value="SELECT a FROM AssociationProductUser a WHERE a.product.id = :productId AND a.user.username = :username")
    AssociationProductUser findCartProductsByProductId(@Param("productId") int productId, @Param("username") String username);

    @Query("SELECT COUNT(a)>0 FROM AssociationProductUser a WHERE a.product.id = :productId AND a.user.id = :userId AND a.status = :status")
    boolean alreadyExists(@Param("productId") int productId, @Param("userId") int userId, @Param("status") AssociationProductUser.ProductUserStatus status);
}

