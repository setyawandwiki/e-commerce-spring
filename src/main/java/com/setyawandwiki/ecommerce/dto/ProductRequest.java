package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.Category;
import com.setyawandwiki.ecommerce.entity.OrderItem;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.entity.WishList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String title;
    private String name;
    private String details;
    private Integer quantity;
    private Double price;
    private Double rate;
    private MultipartFile image1;
    private MultipartFile image2;
    private MultipartFile image3;
    private String category;
}
