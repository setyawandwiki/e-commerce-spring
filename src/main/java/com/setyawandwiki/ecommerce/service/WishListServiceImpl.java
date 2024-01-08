package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.WebResponse;
import com.setyawandwiki.ecommerce.dto.WishListRequest;
import com.setyawandwiki.ecommerce.dto.WishListResponse;
import com.setyawandwiki.ecommerce.entity.Product;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.entity.WishList;
import com.setyawandwiki.ecommerce.repository.ProductRepository;
import com.setyawandwiki.ecommerce.repository.UserRepository;
import com.setyawandwiki.ecommerce.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService{
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Override
    public WishListResponse addWishList(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found"));
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        WishList wishList = new WishList();
        wishList.setUser(user);
        wishList.setProduct(product);
        wishListRepository.save(wishList);
        System.out.println("\n\n\n\n\n");
        System.out.println(wishList.getId());
        return WishListResponse.builder()
                .id(wishList.getId())
                .createdAt(wishList.getCreatedAt())
                .updatedAt(wishList.getUpdatedAt())
                .product(wishList.getProduct())
                .user(wishList.getUser())
                .build();
    }

    @Override
    public void deleteWishList(Long id) {
        WishList wishList = wishListRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "wishlist not found"));
        wishListRepository.delete(wishList);
    }
}
