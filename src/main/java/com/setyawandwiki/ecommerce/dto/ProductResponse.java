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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
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
    private List<OrderItem> orderItems;
    private Category category;
    private List<WishList> wishLists;
    private User user;
    private Date createdAt;
    private Date updatedAt;
}
