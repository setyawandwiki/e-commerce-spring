package com.setyawandwiki.ecommerce.controllers;

import com.setyawandwiki.ecommerce.dto.*;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<WebResponse<String>> signup(@RequestBody SignupRequest request){
        String message = authenticationService.signUp(request);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data(message).build());
    }
    @GetMapping("/verification/{token}")
    public ResponseEntity<WebResponse<String>> verification(@PathVariable("token") String token){
        String message = authenticationService.activateAccount(token);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data(message).build());
    }
    @PostMapping("/signup/seller")
    public ResponseEntity<WebResponse<String>> signupSeller(@RequestBody SignupRequest request){
        String message = authenticationService.signUpSeller(request);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data(message).build());
    }
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthenticationResponse> signin(@RequestBody SigninRequest request){
        return ResponseEntity.ok().body(authenticationService.signin(request));
    }
    @PatchMapping(value = "/forgotPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> forgotPassword(@RequestBody ForgotPasswordRequest request){
        authenticationService.resetPassword(request);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data("password updated").build());
    }
}
