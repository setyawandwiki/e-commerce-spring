package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private Double price;
    private User user;
}
