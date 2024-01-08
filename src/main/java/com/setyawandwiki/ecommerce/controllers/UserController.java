package com.setyawandwiki.ecommerce.controllers;

import com.setyawandwiki.ecommerce.dto.UserProfileRequest;
import com.setyawandwiki.ecommerce.dto.UserResponse;
import com.setyawandwiki.ecommerce.dto.WebResponse;
import com.setyawandwiki.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public String userController(){
        return "this is user controller";
    }
    @GetMapping(path = "/profile")
    public ResponseEntity<WebResponse<UserResponse>> profile(){
        UserResponse dataUserProfile = userService.getDataUserProfile();
        return ResponseEntity.ok().body(WebResponse.<UserResponse>builder().data(dataUserProfile).build());
    }
    @PatchMapping(path = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserResponse>> profileUpdate(@RequestBody UserProfileRequest request){
        UserResponse dataUserProfile = userService.updateDataUser(request);
        return ResponseEntity.ok().body(WebResponse.<UserResponse>builder().data(dataUserProfile).build());
    }
}
