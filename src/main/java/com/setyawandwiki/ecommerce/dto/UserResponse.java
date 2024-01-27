package com.setyawandwiki.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.setyawandwiki.ecommerce.entity.*;
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
public class UserResponse {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String birthDate;
    private Role role;
    private Wallet wallet;
    private List<Address> addresses;
    private List<Order> orders;
    private List<WishList> wishLists;
    private List<Product> products;
    private Date createdAt;
    private Date updatedAt;
}
