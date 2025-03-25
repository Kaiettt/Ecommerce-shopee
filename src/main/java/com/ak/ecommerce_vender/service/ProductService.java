package com.ak.ecommerce_vender.service;

import com.ak.ecommerce_vender.common.Common;
import com.ak.ecommerce_vender.domain.entity.Product;
import com.ak.ecommerce_vender.domain.entity.ProductVariant;
import com.ak.ecommerce_vender.domain.entity.Shop;
import com.ak.ecommerce_vender.domain.request.ProductCreateRequest;
import com.ak.ecommerce_vender.domain.responce.ProductOverviewResponce;
import com.ak.ecommerce_vender.domain.responce.AttributeResponce;
import com.ak.ecommerce_vender.domain.responce.ProductDetailResponce;
import com.ak.ecommerce_vender.domain.responce.ProductVariantResponce;
import com.ak.ecommerce_vender.domain.responce.ResultPaginationDTO;
import com.ak.ecommerce_vender.domain.responce.ResultPaginationDTO.Meta;
import com.ak.ecommerce_vender.error.EntityNotExistException;
import com.ak.ecommerce_vender.repository.ProductRepository;
import com.ak.ecommerce_vender.repository.ProductVariantAttributeRepository;
import com.ak.ecommerce_vender.repository.ProductVariantRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ShopService shopService;
    private final ImageService imageService;
    private final ProductVariantAttributeRepository productVariantAttributeRepository;
    public ProductDetailResponce createNewProduct(ProductCreateRequest request) throws IOException, GeneralSecurityException {
        Shop shop = this.shopService.getShopById(request.getShopID());
        
        Product product = Product.builder()
                .name(request.getName())
                .sold(request.getSold())
                .productDescription(request.getProductDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .shop(shop)
                .brand(request.getBrand())
                .discount(request.getDiscount())
                .build();
        product = this.productRepository.save(product);
        List<String> imagesPath = this.imageService.storeProductImage(request.getImages(),product);
       
        ProductDetailResponce productResponce = ProductDetailResponce.builder()
                .id(product.getId())
                .name(product.getName())
                .sold(product.getSold())
                .productDescription(product.getProductDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .brand(product.getBrand())
                .discount(product.getDiscount())
                .images(imagesPath)
                .shopId(product.getShop().getId())
                .shopName(product.getShop().getName())
                .build();
        return productResponce;
    }
    public List<ProductVariantResponce> getProductVariantById(long id){
        List<ProductVariant> productVariants = this.productVariantRepository.findByProductId(id);
        List<ProductVariantResponce> productVariantResponces = new ArrayList<>();
        for (ProductVariant productVariant : productVariants) {
            ProductVariantResponce productVariantResponce = ProductVariantResponce.builder()
                .productVariantId(productVariant.getProductVariantId())
                .productId(productVariant.getProduct().getId())
                .productName(productVariant
                .getProduct().getName())
                .price(productVariant.getPrice())
                .stockQuantity(productVariant.getStockQuantity())
                .productAttributes(productVariant.getProductAttributes())
                .image(productVariant.getImage())
                .build();
            productVariantResponces.add(productVariantResponce);
        }
        return productVariantResponces;
    }
    public ResultPaginationDTO getAllProducts(Specification<Product> spec,Pageable pageable) {
        Page<Product> pageProduct = this.productRepository.findAll(spec,pageable);
        ResultPaginationDTO.Meta meta = Meta.builder()
        .page(pageable.getPageNumber()+1)
        .pageSize(pageable.getPageSize())
        .pages(pageProduct.getTotalPages())
        .total(pageProduct.getTotalElements())
        .build();

        List<ProductOverviewResponce> productResponces = new ArrayList<>();
        
        for (Product product : pageProduct.getContent()) {
            ProductOverviewResponce productResponce = ProductOverviewResponce.builder()
                .price(product.getPrice())
                .sold(product.getSold())
                .id(product.getId())
                .name(product.getName())
                .build();
            String image = this.imageService.getProductOverviewImage(product.getId());
            productResponce.setImage(image);
            productResponces.add(productResponce);
        }
        ResultPaginationDTO res = ResultPaginationDTO.builder()
            .meta(meta)
            .result(productResponces)
            .build();
        return res;
    }

    public Product getProductById(long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistException(Common.PRODUCT_NOT_FOUND));
    }
    public int getTotalProductStock(List<ProductVariantResponce> productVariantResponces){
        int total = 0;
        for (ProductVariantResponce productVariantResponce : productVariantResponces) {
            total += productVariantResponce.getStockQuantity();
        }
        return total;
    }
    public List<AttributeResponce> getAttributeAndImage(List<ProductVariantResponce> productVariantResponces){
        List<Long> productVariantIdList = productVariantResponces.stream()
                .map(ProductVariantResponce::getProductVariantId)
                .collect(Collectors.toList());

        List<AttributeResponce> attributeAndImageResponce = this.productVariantAttributeRepository.findAttributesByVariantIds(productVariantIdList);
        return attributeAndImageResponce;
    }
    public ProductDetailResponce getProductDetailByID(long id){
        Product product = this.getProductById(id);
        List<String> images = this.imageService.getProductImages(id);
        List<ProductVariantResponce> productVariantResponces = this.getProductVariantById(id);
        List<AttributeResponce> attributeResponce = this.getAttributeAndImage(productVariantResponces);
        return ProductDetailResponce.builder()
        .id(product.getId())
        .name(product.getName())
        .sold(product.getSold())
        .productDescription(product.getProductDescription())
        .category(product.getCategory())
        .price(product.getPrice())
        .brand(product.getBrand())
        .StockQuantity(getTotalProductStock(productVariantResponces))
        .discount(product.getDiscount())
        .attributeResponces(attributeResponce)
        .productVariants(productVariantResponces)
        .images(images)
        .shopId(product.getShop().getId()) 
        .shopName(product.getShop().getName()) 
        .build();
    }

    public void deleteProductById(long id) {
        Product product = getProductById(id); // Ensure the product exists
        this.productRepository.delete(product);
    }
}
