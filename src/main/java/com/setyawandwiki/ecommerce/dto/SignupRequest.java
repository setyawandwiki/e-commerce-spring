package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.Gender;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SignupRequest {
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
}
