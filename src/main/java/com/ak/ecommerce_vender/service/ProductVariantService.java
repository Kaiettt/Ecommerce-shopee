package com.ak.ecommerce_vender.service;

import com.ak.ecommerce_vender.common.Common;
import com.ak.ecommerce_vender.domain.entity.Product;
import com.ak.ecommerce_vender.domain.entity.ProductVariant;
import com.ak.ecommerce_vender.domain.entity.ProductVariantAttribute;
import com.ak.ecommerce_vender.domain.request.ProductVariantAttributeRequest;
import com.ak.ecommerce_vender.domain.request.ProductVariantCreateRequest;
import com.ak.ecommerce_vender.domain.responce.ProductVariantResponce;
import com.ak.ecommerce_vender.error.EntityNotExistException;
import com.ak.ecommerce_vender.repository.ProductAttributeRepository;
import com.ak.ecommerce_vender.repository.ProductRepository;
import com.ak.ecommerce_vender.repository.ProductVariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductVariantService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductAttributeRepository productAttributeRepository;
    @Transactional
    public ProductVariantResponce createProductVariant(ProductVariantCreateRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotExistException(Common.PRODUCT_NOT_FOUND));
        List<ProductVariantAttribute> productVariantAttributes = new ArrayList<>();
        for (ProductVariantAttributeRequest productVariantAttributeRequest : request.getProductAttributes()) {
            ProductVariantAttribute attribute = productAttributeRepository
            .findByAttributeAndValue(productVariantAttributeRequest.getAttribute(), productVariantAttributeRequest.getValue())
            .orElseGet(() -> {
                ProductVariantAttribute newAttribute = new ProductVariantAttribute();
                newAttribute.setAttribute(productVariantAttributeRequest.getAttribute());
                newAttribute.setValue(productVariantAttributeRequest.getValue());
                return productAttributeRepository.save(newAttribute);
            });
            productVariantAttributes.add(attribute);
        }
        ProductVariant productVariant = ProductVariant.builder()
                .product(product)
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .productAttributes(productVariantAttributes)
                .image(request.getProductImage())
                .build();

        productVariant =  productVariantRepository.save(productVariant);
        ProductVariantResponce productVariantResponce = ProductVariantResponce.builder()
        .productVariantId(productVariant.getProductVariantId())
        .productId(product.getId())
        .productName(product.getName())
        .price(productVariant.getPrice())
        .stockQuantity(productVariant.getStockQuantity())
        .productAttributes(productVariantAttributes)
        .image(productVariant.getImage())
        .build();
        return productVariantResponce;
    }
    public List<ProductVariantResponce> createMutipleProductVariant(List<ProductVariantCreateRequest> requests){
        List<ProductVariantResponce> productVariantResponces = new ArrayList<>();
        for (ProductVariantCreateRequest request : requests) {
            productVariantResponces.add(this.createProductVariant(request));
        }
        return productVariantResponces;
    }
    public ProductVariant getProductVariantById(long id){
        return this.productVariantRepository.findById(id)
        .orElseThrow(() -> new EntityNotExistException(Common.PRODUCT_NOT_FOUND));
    }
}
