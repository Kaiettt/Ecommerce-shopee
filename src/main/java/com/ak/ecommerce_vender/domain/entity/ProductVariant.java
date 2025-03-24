package com.ak.ecommerce_vender.domain.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
@Entity
@Table(name = "product_variant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productVariantId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    private long price;
    private long stockQuantity;
    @ElementCollection
    @CollectionTable(name = "product_attribute", joinColumns = @JoinColumn(name = "product_variant_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "attribute", column = @Column(name = "attribute")),
            @AttributeOverride(name = "value", column = @Column(name = "value"))
    })
    private List<ProductVariantAttribute> productAttributes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_image_id",nullable = false)
    private ProductImage productImage;
}
