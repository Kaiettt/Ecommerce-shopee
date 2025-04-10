package com.ak.ecommerce_vender.controller;

import com.ak.ecommerce_vender.domain.entity.ProductVariant;
import com.ak.ecommerce_vender.domain.request.ProductVariantCreateRequest;
import com.ak.ecommerce_vender.domain.responce.ProductVariantResponce;
import com.ak.ecommerce_vender.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;
    
    @PostMapping
    public ResponseEntity<ProductVariantResponce> createProductVariant(@Valid @RequestBody ProductVariantCreateRequest request) {
         return ResponseEntity.status(HttpStatus.CREATED).body(this.productVariantService.createProductVariant(request));
    }
    @PostMapping("/mutiples")
    public ResponseEntity<List<ProductVariantResponce>> createMutipleProductVariant(@Valid @RequestBody List<ProductVariantCreateRequest> requests) {
         return ResponseEntity.status(HttpStatus.CREATED).body(this.productVariantService.createMutipleProductVariant(requests));
    }
}
