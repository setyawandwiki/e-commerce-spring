package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.Order;
import com.setyawandwiki.ecommerce.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequest {
    private Long id;
    private Double price;
    private Integer quantity;
    private Product product;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
