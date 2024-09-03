package com.spring.asset_craft.repository;

import com.spring.asset_craft.dto.ReviewDTO;
import com.spring.asset_craft.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findProductById(Long id);

    @Query("SELECT new com.spring.asset_craft.dto.ReviewDTO(r.comment, r.rating, u.username) " +
            "FROM Review r JOIN r.user u " +
            "WHERE r.product.id = :productId")
    List<ReviewDTO> findProductReviews(@Param("productId") Long productId);


}
