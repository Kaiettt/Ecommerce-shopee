package com.ak.ecommerce_vender.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ak.ecommerce_vender.domain.entity.ProductVariantAttribute;
import com.ak.ecommerce_vender.domain.responce.AttributeResponce;

public interface ProductVariantAttributeRepository extends JpaRepository<ProductVariantAttribute, Long>, JpaSpecificationExecutor<ProductVariantAttribute>{
    @Query(value = """
        SELECT pva.attribute, 
               GROUP_CONCAT(DISTINCT pva.value ORDER BY pva.value SEPARATOR ', ')
        FROM product_variant_attribute pva
        JOIN product_variant_attribute_mapping pvam ON pva.id = pvam.attribute_id
        JOIN product_variant pv ON pvam.product_variant_id = pv.product_variant_id
        WHERE pv.product_variant_id IN :variantIds
        GROUP BY pva.attribute
        """, nativeQuery = true)
    List<AttributeResponce> findAttributesByVariantIds(@Param("variantIds") List<Long> variantIds);
}
