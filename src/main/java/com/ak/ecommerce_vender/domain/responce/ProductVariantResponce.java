package com.ak.ecommerce_vender.domain.responce;

import java.util.List;

import org.springframework.core.io.Resource;

import com.ak.ecommerce_vender.domain.entity.ProductVariantAttribute;
import com.ak.ecommerce_vender.domain.request.ProductVariantAttributeRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductVariantResponce {
    private long productVariantId;
    private long productId;
    private String productName;
    private long price;
    private long stockQuantity;
    private List<ProductVariantAttribute> productAttributes;
    private String image;
}
