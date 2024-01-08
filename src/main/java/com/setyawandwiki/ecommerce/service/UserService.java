package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.UserProfileRequest;
import com.setyawandwiki.ecommerce.dto.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    UserResponse getDataUserById(Long id);
    UserResponse getDataUserProfile();
    UserResponse updateDataUser(UserProfileRequest request);
}
