package com.ak.ecommerce_vender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ak.ecommerce_vender.domain.entity.Cart;
import com.ak.ecommerce_vender.domain.entity.CartDetail;
import com.ak.ecommerce_vender.domain.entity.Product;
import com.ak.ecommerce_vender.domain.entity.ProductVariant;
import com.ak.ecommerce_vender.domain.entity.User;
import com.ak.ecommerce_vender.domain.request.CartDetailRequest;
import com.ak.ecommerce_vender.domain.responce.AddToCartResponce;
import com.ak.ecommerce_vender.domain.responce.CartDetailResponce;
import com.ak.ecommerce_vender.domain.responce.CartResponce;
import com.ak.ecommerce_vender.error.EntityNotExistException;
import com.ak.ecommerce_vender.repository.CartDetailRepository;
import com.ak.ecommerce_vender.repository.CartRepository;
import com.ak.ecommerce_vender.util.SecurityUtil;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class CartService {
    private final UserService userService;
    private final ProductVariantService productVariantService;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductService productService;
    public CartResponce getUserCart(long userID) {
        Cart cart = this.cartRepository.findByUserId(userID);
        if(cart == null){
            User user = this.userService.getUserByID(userID);
            cart  = Cart.builder()
                .user(user)
                .total_price(0)
                .quantity(0)
                .build();
            cart = this.cartRepository.save(cart);
        }
        List<CartDetail> cartDetails = cart.getCartDetails();
        List<CartDetailResponce> cartDetailResponces = new ArrayList<>();
        for (CartDetail cartDetail : cartDetails) {
            Product product = cartDetail.getProduct();
            ProductVariant productVariant = cartDetail.getProductVariant();
            CartDetailResponce cartResponce = CartDetailResponce.builder()
                .id(cartDetail.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .currentAttributes(productVariant.getProductAttributes())
                .image(productVariant.getImage())
                .quantity(cartDetail.getQuantity())
                .totalPrice(cartDetail.getPrice())
                .build();
            cartDetailResponces.add(cartResponce);
        }
        CartResponce cartResponce = CartResponce.builder()
            .id(cart.getId())
            .quantity(cart.getQuantity())
            .totalPrice(cart.getTotal_price())
            .userID(cart.getUser().getId())
            .cartDetailResponces(cartDetailResponces)
            .username(cart.getUser().getUsername())
            .build();
        return cartResponce;
    }
    public AddToCartResponce addProductToCart(CartDetailRequest request) throws EntityNotExistException{
    Optional<String> userLoginInfo = SecurityUtil.getCurrentUserLogin();
    if(!userLoginInfo.isPresent()){
        throw new EntityNotExistException("Please login");
    }
    String email = userLoginInfo.get();
    User user = this.userService.getUserByUserName(email);

    Cart cart = this.cartRepository.findByUserId(user.getId());
    if (cart == null) {
        cart = Cart.builder()
            .user(user)
            .total_price(0)
            .quantity(0)
            .cartDetails(new ArrayList<>())  
            .build();
        cart = this.cartRepository.save(cart);
    }

    ProductVariant productVariant = this.productVariantService.getProductVariantById(request.getProductVariantId());
    Product product = this.productService.getProductById(request.getProductID());
    
    CartDetail cartDetail = this.cartDetailRepository.findByCartIdAndProductVariantId(cart.getId(), productVariant.getProductVariantId()).orElse(null);

    long newPrice = product.getPrice() * request.getQuantity();
    cart.setQuantity(cart.getQuantity() + request.getQuantity());
    cart.setTotal_price(cart.getTotal_price() + newPrice);

    if (cartDetail == null) {
        cartDetail = CartDetail.builder()
            .price(newPrice)
            .quantity(request.getQuantity())
            .cart(cart)
            .productVariant(productVariant)
            .product(product)
            .build();
        cart.getCartDetails().add(cartDetail);
    } else {
        cartDetail.setQuantity(cartDetail.getQuantity() + request.getQuantity());
        cartDetail.setPrice(cartDetail.getPrice() + newPrice);
    }
    cartDetail = this.cartDetailRepository.save(cartDetail);
    AddToCartResponce cartDetailResponce = AddToCartResponce.builder()
        .cart_id(cart.getId())
        .id(cartDetail.getId())
        .price(cartDetail.getPrice())
        .quantity(cartDetail.getQuantity())
        .productId(product.getId())
        .productVariantID(productVariant.getProductVariantId())
        .build();

    return cartDetailResponce;
}

    
    
}
