package com.ak.ecommerce_vender.domain.responce;

import java.util.List;

import com.ak.ecommerce_vender.common.Category;

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
    private int StockQuantity;
    private List<ProductVariantResponce> productVariants;
    private List<String> images;
    private List<Attribute> attributeResponces;
    private long shopId;
    private String shopName;
}
