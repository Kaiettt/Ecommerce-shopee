package com.ak.ecommerce_vender.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.ak.ecommerce_vender.domain.entity.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long>, JpaSpecificationExecutor<ProductVariant>{

    List<ProductVariant> findByProductId(long id);
}
