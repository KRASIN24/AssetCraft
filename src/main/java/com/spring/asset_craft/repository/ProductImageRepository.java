package com.spring.asset_craft.repository;

import com.spring.asset_craft.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("SELECT i FROM ProductImage i WHERE i.product.id = :productId")
    List<ProductImage> findImagesByProductId(@Param("productId") Long productId);

    @Query("SELECT i FROM ProductImage i WHERE i.id = :imageId")
    ProductImage findImagesById(@Param("imageId") Long imageId);
}
