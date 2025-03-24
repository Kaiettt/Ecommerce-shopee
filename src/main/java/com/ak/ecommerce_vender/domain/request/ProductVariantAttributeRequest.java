package com.ak.ecommerce_vender.domain.request;

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
public class ProductVariantAttributeRequest {
    private String attribute;
    private String value;
}
