package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.UserProfileRequest;
import com.setyawandwiki.ecommerce.dto.UserResponse;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));
            }
        };
    }

    @Override
    public UserResponse getDataUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        return UserResponse.builder()
                .addresses(user.getAddresses())
                .email(user.getEmail())
                .id(user.getId())
                .gender(user.getGender())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .orders(user.getOrders())
                .products(user.getProducts())
                .wishLists(user.getWishLists())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .wallet(user.getWallet())
                .build();
    }

    @Override
    public UserResponse getDataUserProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        return UserResponse.builder()
                .addresses(user.getAddresses())
                .email(user.getEmail())
                .id(user.getId())
                .role(user.getRole())
                .gender(user.getGender())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .orders(user.getOrders())
                .products(user.getProducts())
                .wishLists(user.getWishLists())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .wallet(user.getWallet())
                .build();
    }

    @Override
    public UserResponse updateDataUser(UserProfileRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        if(Objects.nonNull(request.getFirstName())){
            user.setFirstName(request.getFirstName());
        }
        if(Objects.nonNull(request.getLastName())){
            user.setLastName(request.getLastName());
        }
        if(Objects.nonNull(request.getGender())){
            user.setGender(request.getGender());
        }
        if(Objects.nonNull(request.getBirthDate())){
            user.setBirthDate(request.getBirthDate());
        }
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        userRepository.save(user);
        return UserResponse.builder()
                .addresses(user.getAddresses())
                .email(user.getEmail())
                .id(user.getId())
                .gender(user.getGender())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .orders(user.getOrders())
                .products(user.getProducts())
                .wishLists(user.getWishLists())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .wallet(user.getWallet())
                .build();
    }
}
