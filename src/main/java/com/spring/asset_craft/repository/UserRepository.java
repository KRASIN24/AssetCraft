package com.spring.asset_craft.repository;

import com.spring.asset_craft.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long userId);

    @Modifying
    @Query(value = "INSERT INTO users_roles (user_id, role_id) VALUES (:userId, 1)", nativeQuery = true)
    void insertUserRole(@Param("userId") Long userId);


}
