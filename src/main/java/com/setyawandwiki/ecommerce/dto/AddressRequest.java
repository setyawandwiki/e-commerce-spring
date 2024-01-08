package com.setyawandwiki.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
    @NotBlank(message = "phone cannot be null")
    private String phone;
    @NotBlank(message = "street cannot be null")
    private String street;
    @NotBlank(message = "city cannot be null")
    private String city;
    @NotBlank(message = "postalcode cannot be null")
    private String postalCode;
    @NotNull(message = "province cannot be null")
    private String province;
    @NotNull(message = "subdistrict cannot be null")
    private String subdistrict;
    private Boolean isActive;
}
