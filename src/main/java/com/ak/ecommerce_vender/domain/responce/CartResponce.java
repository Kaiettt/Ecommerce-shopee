package com.ak.ecommerce_vender.domain.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartResponce {
    private long id;
    private long quantity;
    private long totalPrice;
    private String username;
    private long userID;
}
