package com.ak.ecommerce_vender.domain.responce;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;

import com.ak.ecommerce_vender.common.Category;
import com.ak.ecommerce_vender.domain.entity.ProductVariant;

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
public class ProductDetailResponce {
    private Long id;
    private String name;
    private long sold;
    private String productDescription;
    private Category category;
    private Long price;
    private String brand;
    private Long discount;
    private List<ProductVariantResponce> productVariants;
    private List<String> images;
    private long shopId;
    private String shopName;
}
