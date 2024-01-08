package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.Category;
import com.setyawandwiki.ecommerce.entity.OrderItem;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.entity.WishList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductResponse {
    private Long id;
    private String title;
    private String name;
    private String details;
    private Integer quantity;
    private Double price;
    private Double rate;
    private String image1;
    private String image2;
    private String image3;
    private Category category;
    private Date createdAt;
    private Date updatedAt;
}
