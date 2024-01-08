package com.setyawandwiki.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.setyawandwiki.ecommerce.entity.OrderItem;
import com.setyawandwiki.ecommerce.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private Long id;
    private Double price;
    private User user;
}
