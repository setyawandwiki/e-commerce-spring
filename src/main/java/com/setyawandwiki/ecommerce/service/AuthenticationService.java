package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.*;
import com.setyawandwiki.ecommerce.entity.User;

public interface AuthenticationService {
    String signUp(SignupRequest signupRequest);
    String signUpSeller(SignupRequest signupRequest);
    String activateAccount(String token);
    JWTAuthenticationResponse signin(SigninRequest signinRequest);
    JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    void resetPassword(ForgotPasswordRequest request);
}
