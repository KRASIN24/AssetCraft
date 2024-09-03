package com.spring.asset_craft.repository;

import com.spring.asset_craft.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
    Double findProductRatingById(@Param("productId") Long productId);
}
