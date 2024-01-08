package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.*;
import com.setyawandwiki.ecommerce.entity.Role;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromMail;
    @Override
    public String signUp(SignupRequest signupRequest) {
        User existingUser = userRepository.findByEmail(signupRequest.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        } else {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            User user = new User();
            user.setEmail(signupRequest.getEmail());
            user.setFirstName(signupRequest.getFirstName());
            user.setLastName(signupRequest.getLastName());
            user.setGender(signupRequest.getGender());
            user.setRole(Role.CUSTOMER);
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setIsActive(false);
            user.setToken(UUID.randomUUID().toString());
            userRepository.save(user);

            simpleMailMessage.setFrom(fromMail);
            simpleMailMessage.setSubject("verification user");
            simpleMailMessage.setText("please click this link to active your account : http://localhost:8080/api/v1/auth/verification/" + user.getToken());
            simpleMailMessage.setTo(user.getEmail());
            javaMailSender.send(simpleMailMessage);
            return "please check your email";
        }
    }

    @Override
    public String signUpSeller(SignupRequest signupRequest) {
        User existingUser = userRepository.findByEmail(signupRequest.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        } else {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            User user = new User();
            user.setEmail(signupRequest.getEmail());
            user.setFirstName(signupRequest.getFirstName());
            user.setLastName(signupRequest.getLastName());
            user.setGender(signupRequest.getGender());
            user.setRole(Role.CUSTOMER);
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setIsActive(false);
            user.setToken(UUID.randomUUID().toString());
            userRepository.save(user);

            simpleMailMessage.setFrom(fromMail);
            simpleMailMessage.setSubject("verification user");
            simpleMailMessage.setText("please click this link to active your account : http://localhost:8080/api/v1/auth/verification/" + user.getToken());
            simpleMailMessage.setTo(user.getEmail());
            javaMailSender.send(simpleMailMessage);
            return "please check your email";
        }
    }

    @Override
    public String activateAccount(String token) {
        User user = userRepository.findByToken(token).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid token"));
        user.setToken(null);
        user.setIsActive(true);
        userRepository.save(user);
        return "user activate";
    }

    @Override
    public JWTAuthenticationResponse signin(SigninRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("email or password invalid"));
        if(user.getIsActive().equals(false)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not active");
        }
        String jwt = jwtService.generateToken(user);
        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        return jwtAuthenticationResponse;
    }

    public void resetPassword(ForgotPasswordRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        if(!request.getNewPassword().equals(request.getNewPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password is not equal or not same");
        }
        user.setPassword(passwordEncoder.encode(request.getConfirmPassword()));
        userRepository.save(user);
    }

    @Override
    public JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }
}
