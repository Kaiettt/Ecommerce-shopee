package com.ak.ecommerce_vender.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDetailRequest {
    private long quantity;
    private long productID;
    private long productVariantId;
}




