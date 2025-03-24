package com.ak.ecommerce_vender.service;

import org.springframework.stereotype.Service;

import com.ak.ecommerce_vender.common.Common;
import com.ak.ecommerce_vender.domain.entity.Shop;
import com.ak.ecommerce_vender.error.EntityNotExistException;
import com.ak.ecommerce_vender.repository.ShopRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class ShopService {
    private final ShopRepository repository;
    public Shop getShopById(long id){
         return this.repository.findById(id)
            .orElseThrow(() -> new EntityNotExistException(Common.SHOP_NOT_FOUND));
    }
}
