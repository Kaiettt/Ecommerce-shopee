package com.ak.ecommerce_vender.domain.responce;

import java.util.List;

import com.ak.ecommerce_vender.domain.entity.ProductVariantAttribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartDetailResponce {
    private Long id;
    private String productName;
    private long productPrice;
    private List<ProductVariantAttribute> currentAttributes;
    private String image;
    private long quantity;
    private long totalPrice;
}
