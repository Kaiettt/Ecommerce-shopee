package com.ak.ecommerce_vender.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ak.ecommerce_vender.domain.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart>{
    Cart findByUserId(long userID);
}
