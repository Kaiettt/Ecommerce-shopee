package com.ak.ecommerce_vender.repository;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.ak.ecommerce_vender.domain.entity.ProductImage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Repository
public class ProductImageCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductImage> findProductOverviewImageByProductId(long id) {
        return entityManager.createQuery(
                "SELECT p FROM ProductImage p WHERE p.product.id = :id ORDER BY p.id ASC",
                ProductImage.class)
            .setParameter("id", id)
            .setMaxResults(1) // Fetch only the first result
            .getResultList();
    }

    public List<ProductImage> findProductimageByProductId(long id) {
        return entityManager.createQuery(
                "SELECT p FROM ProductImage p WHERE p.product.id = :id",
                ProductImage.class)
            .setParameter("id", id)
            .getResultList();
    }

    
}

