package com.setyawandwiki.ecommerce.controllers;

import com.setyawandwiki.ecommerce.dto.WebResponse;
import com.setyawandwiki.ecommerce.dto.WishListRequest;
import com.setyawandwiki.ecommerce.dto.WishListResponse;
import com.setyawandwiki.ecommerce.repository.WishListRepository;
import com.setyawandwiki.ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    @PostMapping(path = "/{id}")
    public ResponseEntity<WebResponse<WishListResponse>> addWishlist(@PathVariable("id") Long id){
        WishListResponse wishListResponse = wishListService.addWishList(id);
        return ResponseEntity.ok().body(WebResponse.<WishListResponse>builder().data(wishListResponse).build());
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<WebResponse<String>> removeWishList(@PathVariable("id") Long id){
        wishListService.deleteWishList(id);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data("wishlist sucdess deleted").build());
    }
}
