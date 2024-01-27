package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.WishListRequest;
import com.setyawandwiki.ecommerce.dto.WishListResponse;

import java.util.List;

public interface WishListService {
    WishListResponse addWishList(Long id);
    List<WishListResponse> getAllWishList();
    WishListResponse getWishListById(Long id);
    void deleteWishList(Long id);
}
