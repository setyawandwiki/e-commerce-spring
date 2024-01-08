package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.Product;
import com.setyawandwiki.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WishListResponse {
    private Long id;
    private User user;
    private Product product;
    private Date createdAt;
    private Date updatedAt;
}
