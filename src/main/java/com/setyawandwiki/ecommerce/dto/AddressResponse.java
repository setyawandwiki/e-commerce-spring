package com.setyawandwiki.ecommerce.dto;

import com.setyawandwiki.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private Long id;
    private String phone;
    private String street;
    private String city;
    private String postalCode;
    private String province;
    private String subdistrict;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
