package com.ak.ecommerce_vender.domain.responce;

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
public class AddToCartResponce {
    private long id;
    private long price;
    private long quantity;
    private long cart_id;
    private long productId;
    private long productVariantID;
}
