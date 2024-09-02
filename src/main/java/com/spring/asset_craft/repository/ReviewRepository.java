package com.spring.asset_craft.repository;

import com.spring.asset_craft.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
