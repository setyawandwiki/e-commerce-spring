package com.setyawandwiki.ecommerce.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SigninRequest {
    @Column(unique = true)
    private String email;
    private String password;
}
