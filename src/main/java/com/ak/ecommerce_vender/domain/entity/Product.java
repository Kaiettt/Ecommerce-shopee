package com.ak.ecommerce_vender.domain.entity;

import java.util.List;

import com.ak.ecommerce_vender.common.Category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private long sold;
    @Column(columnDefinition = "TEXT")
    private String productDescription;
    @Enumerated(EnumType.STRING)
    private Category category;
    @NotNull
    private Long price;
    private String brand;
    private Long discount;
    @JsonIgnore
    @OneToMany(mappedBy="product",fetch = FetchType.LAZY)
    private List<ProductVariant> productVariants;
    @JsonIgnore
    @OneToMany(mappedBy="product",fetch = FetchType.LAZY)
    private List<ProductImage> productImages;
  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id",nullable = false)
    private Shop shop;
}
