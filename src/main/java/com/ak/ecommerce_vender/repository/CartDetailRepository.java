package com.ak.ecommerce_vender.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ak.ecommerce_vender.domain.entity.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long>, JpaSpecificationExecutor<CartDetail> {

    @Query("SELECT c FROM CartDetail c WHERE c.productVariant.id = :productId")
    Optional<CartDetail> findByProductVariantId(@Param("productId") Long productId);

    @Query("SELECT c FROM CartDetail c WHERE c.cart.id = :cartId AND c.productVariant.id = :productVariantId")
    Optional<CartDetail> findByCartIdAndProductVariantId(@Param("cartId") Long cartId, @Param("productVariantId") Long productVariantId);
}
