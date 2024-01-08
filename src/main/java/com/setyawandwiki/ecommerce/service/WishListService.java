package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.WishListRequest;
import com.setyawandwiki.ecommerce.dto.WishListResponse;

public interface WishListService {
    WishListResponse addWishList(Long id);
    void deleteWishList(Long id);
}
