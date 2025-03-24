package com.ak.ecommerce_vender.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ak.ecommerce_vender.domain.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long>, JpaSpecificationExecutor<Shop>{
}
