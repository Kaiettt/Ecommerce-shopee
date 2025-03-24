package com.ak.ecommerce_vender.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ak.ecommerce_vender.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{
    Optional<User> findByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
}
