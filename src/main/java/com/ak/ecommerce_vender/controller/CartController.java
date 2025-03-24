package com.ak.ecommerce_vender.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ak.ecommerce_vender.common.Common;
import com.ak.ecommerce_vender.domain.request.CartDetailRequest;
import com.ak.ecommerce_vender.domain.responce.CartDetailResponce;
import com.ak.ecommerce_vender.domain.responce.CartResponce;
import com.ak.ecommerce_vender.error.CheckRequest;
import com.ak.ecommerce_vender.error.InvalidIdException;
import com.ak.ecommerce_vender.service.CartService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CartController {
    private final CartService cartService;
    @GetMapping("cart/{id}")
    public ResponseEntity<CartResponce> getUserCart(@PathVariable String id) {
        if (id == null || !CheckRequest.isNumeric(id)) {
            throw new InvalidIdException(Common.INVALID_ID);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.cartService.getUserCart(Long.parseLong(id)));
    }
    @PostMapping("/add-to-cart")
    public ResponseEntity<CartDetailResponce> addProductToCart(@RequestBody CartDetailRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.cartService.addProductToCart(request));
    }
    
}
