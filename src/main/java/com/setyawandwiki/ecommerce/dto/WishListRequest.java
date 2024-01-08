package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.Product;
import com.setyawandwiki.ecommerce.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListRequest {
    private Long id;
    private User user;
    private Product product;
    private Date createdAt;
    private Date updatedAt;
}
