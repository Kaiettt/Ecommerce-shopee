package com.ak.ecommerce_vender.domain.request;

import java.util.List;

import com.ak.ecommerce_vender.domain.entity.ProductVariantAttribute;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductVariantCreateRequest {
     @NotNull
    private Long productId; 

    @Min(0)
    private long price;

    @Min(0)
    private long stockQuantity;

    private String productImage;

    private List<ProductVariantAttributeRequest> productAttributes;
}
