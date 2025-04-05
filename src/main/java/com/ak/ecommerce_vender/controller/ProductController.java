package com.ak.ecommerce_vender.controller;

import com.ak.ecommerce_vender.common.Common;
import com.ak.ecommerce_vender.domain.entity.Product;
import com.ak.ecommerce_vender.domain.request.ProductCreateRequest;
import com.ak.ecommerce_vender.domain.responce.ProductDetailResponce;
import com.ak.ecommerce_vender.domain.responce.ResultPaginationDTO;
import com.ak.ecommerce_vender.error.CheckRequest;
import com.ak.ecommerce_vender.error.InvalidIdException;
import com.ak.ecommerce_vender.service.ProductService;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.http.MediaType;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<ProductDetailResponce> createNewProduct(
    @ModelAttribute ProductCreateRequest request) throws IOException, GeneralSecurityException {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.createNewProduct(request));
}

    @GetMapping("/products")
    public ResponseEntity<ResultPaginationDTO> getAllProducts(@Filter Specification<Product> spec, Pageable page) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productService.getAllProducts(spec,page));
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDetailResponce> getProductById(@PathVariable String id) {
        if (id == null || !CheckRequest.isNumeric(id)) {
            throw new InvalidIdException(Common.INVALID_ID);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.productService.getProductDetailByID(Long.parseLong(id)));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id) {
        if (id == null || !CheckRequest.isNumeric(id)) {
            throw new InvalidIdException(Common.INVALID_ID);
        }
        this.productService.deleteProductById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
