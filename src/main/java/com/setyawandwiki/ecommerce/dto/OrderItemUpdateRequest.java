package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.Product;
import lombok.Data;

import java.util.Date;

@Data
public class OrderItemUpdateRequest {
    private Long id;
    private Double price;
    private Integer quantity;
    private Date updatedAt;
}
