package com.ak.ecommerce_vender.domain.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private long price;
    private long stockQuantity;
    private String image;

    @ManyToMany
    @JoinTable(
        name = "product_variant_attribute_mapping",
        joinColumns = @JoinColumn(name = "product_variant_id"),
        inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    private List<ProductVariantAttribute> productAttributes;
}
