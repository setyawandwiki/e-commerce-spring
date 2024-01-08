package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.Order;
import com.setyawandwiki.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;
    private Double price;
    private Integer quantity;
    private Product product;
    private String status;
    private Order order;
    private Date createdAt;
    private Date updatedAt;
}
