package com.spring.asset_craft.repository;

import com.spring.asset_craft.dto.MidProductDTO;
import com.spring.asset_craft.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

//    @Query(value="SELECT")
//    List<MidProductDTO> findProductImgsByProductId(@Param("id") String id);
    Optional<Product> findProductById(int id);
}
