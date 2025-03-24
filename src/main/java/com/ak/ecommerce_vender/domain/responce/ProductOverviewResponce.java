package com.ak.ecommerce_vender.domain.responce;

import org.springframework.core.io.Resource;

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
public class ProductOverviewResponce {
    private String name;
    private long id;
    private long price;
    private long sold;
    private String image;
}
