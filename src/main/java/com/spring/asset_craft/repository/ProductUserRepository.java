package com.spring.asset_craft.repository;

import com.spring.asset_craft.dto.MidProductDTO;
import com.spring.asset_craft.entity.AssociationProductUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUserRepository extends JpaRepository<AssociationProductUser, Long> {

    @Query(value = "SELECT new com.spring.asset_craft.dto.MidProductDTO(p.name, p.price, u.username) " +
            "FROM AssociationProductUser a " +
            "JOIN a.product p " +
            "JOIN a.user u " +
            "WHERE a.status = 'CART' AND u.username = :username")
    List<MidProductDTO> findCartProductsByUser(@Param("username") String username);
}

