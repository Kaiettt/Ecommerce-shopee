package com.ak.ecommerce_vender.service;

import com.ak.ecommerce_vender.common.Common;
import com.ak.ecommerce_vender.domain.entity.Product;
import com.ak.ecommerce_vender.domain.entity.ProductVariant;
import com.ak.ecommerce_vender.domain.request.ProductVariantCreateRequest;
import com.ak.ecommerce_vender.error.EntityNotExistException;
import com.ak.ecommerce_vender.repository.ProductRepository;
import com.ak.ecommerce_vender.repository.ProductVariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductVariantService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    @Transactional
    public ProductVariant createProductVariant(ProductVariantCreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotExistException(Common.PRODUCT_NOT_FOUND));

        ProductVariant productVariant = ProductVariant.builder()
                .product(product)
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .productAttributes(request.getProductAttributes())
                .build();

        return productVariantRepository.save(productVariant);
    }

    public ProductVariant getProductVariantById(long id){
        return this.productVariantRepository.findById(id)
        .orElseThrow(() -> new EntityNotExistException(Common.PRODUCT_NOT_FOUND));
    }
}
