package com.ak.ecommerce_vender.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
@Entity
@Table(name = "cartdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetail {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;
    @OneToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    private long quantity;
    private long price;
}
