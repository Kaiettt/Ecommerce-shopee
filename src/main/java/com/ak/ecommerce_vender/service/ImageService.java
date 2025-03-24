package com.ak.ecommerce_vender.service;

import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ak.ecommerce_vender.domain.entity.Product;
import com.ak.ecommerce_vender.domain.entity.ProductImage;
import com.ak.ecommerce_vender.repository.ProductImageCustomRepository;
import com.ak.ecommerce_vender.repository.ProductImageRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class ImageService {
    

    private final ProductImageCustomRepository productImageCustomRepository;
    private final ProductImageRepository productImageRepository;
   public String getProductOverviewImage(long id) {
        List<ProductImage> productImages = productImageCustomRepository.findProductOverviewImageByProductId(id);

        return productImages.get(0).getImagePath();
    }
    public List<String> getProductImages(long id) {
        List<ProductImage> productImages = this.productImageRepository.findByProduct_Id(id);
        List<String> images = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            images.add(productImage.getImagePath());
        }
        return images;
    }
    public Resource getImageResource(String path){
        try {
            Path filePath = Paths.get(path);
            return new UrlResource(filePath.toUri());
        } catch (Exception e) {
            throw new RuntimeException("Error loading image file: " + path, e);
        }
    }

    public ProductImage saveProductImage(String image,Product product){
        ProductImage productImage = ProductImage.builder()
            .imagePath(image)
            .product(product)
            .build();
        return this.productImageRepository.save(productImage);
    }
    private final DriveService driveService;
    public List<String> storeProductImage(List<MultipartFile> images,Product product)throws IOException, GeneralSecurityException {
    List<String> imagePaths = new ArrayList<>();
    for (MultipartFile image : images) {
        if (image.isEmpty()) {
            continue;
        }
        File tempFile = File.createTempFile("temp", null);
        image.transferTo(tempFile);
        String imagesPath = this.driveService.uploadImageToDrive(tempFile);  
        ProductImage productImage = this.saveProductImage(imagesPath,product);
            // Add the file path to the list
        imagePaths.add(productImage.getImagePath());
    }
    
    return imagePaths;
}
}
