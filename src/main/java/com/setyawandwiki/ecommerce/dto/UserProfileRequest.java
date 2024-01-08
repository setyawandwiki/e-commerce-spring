package com.setyawandwiki.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.setyawandwiki.ecommerce.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRequest {
    private String firstName;
    private String lastName;
    private Gender gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthDate;
    private Date createdAt;
    private Date updatedAt;
}
