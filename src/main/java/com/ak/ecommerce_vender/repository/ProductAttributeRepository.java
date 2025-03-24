package com.ak.ecommerce_vender.repository;
import com.ak.ecommerce_vender.domain.entity.ProductVariantAttribute;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ProductAttributeRepository extends JpaRepository<ProductVariantAttribute, Long>, JpaSpecificationExecutor<ProductVariantAttribute> {
    Optional<ProductVariantAttribute> findByAttributeAndValue(String attribute, String value);
}
