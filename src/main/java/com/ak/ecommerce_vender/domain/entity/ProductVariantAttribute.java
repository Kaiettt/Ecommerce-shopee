package com.ak.ecommerce_vender.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_variant_attribute")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductVariantAttribute {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String attribute;
    private String value;
}
