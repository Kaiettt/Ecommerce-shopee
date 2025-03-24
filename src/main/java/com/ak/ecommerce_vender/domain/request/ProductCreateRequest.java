package com.ak.ecommerce_vender.domain.request;

import java.util.List;


import com.ak.ecommerce_vender.common.Category;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateRequest {

    @NotNull
    private String name;
    private long sold;
    private String productDescription;
    @NotNull
    private Category category;
    @NotNull
    private Long price;
    private String brand;
    private Long discount;
    private List<MultipartFile > images;
    private long shopID;
}

