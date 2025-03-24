package com.ak.ecommerce_vender.repository;

import com.ak.ecommerce_vender.domain.entity.ProductImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, JpaSpecificationExecutor<ProductImage> {

    List<ProductImage> findByProduct_Id(long id);
}
