package com.setyawandwiki.ecommerce.config;

import com.setyawandwiki.ecommerce.entity.Role;
import com.setyawandwiki.ecommerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collection;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L);
                return config;
            }
        }).and().csrf(csrf->csrf.disable());
        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/address/**").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/v1/category/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/product/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/product/**").hasAnyAuthority(Role.SELLER.name(), Role.ADMIN.name())
                    .requestMatchers(HttpMethod.PATCH, "/api/v1/product/{id}").hasAnyAuthority(Role.SELLER.name(), Role.ADMIN.name())
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/product/**").hasAnyAuthority(Role.SELLER.name(), Role.ADMIN.name())
                    .requestMatchers(HttpMethod.POST, "/api/v1/category").hasAuthority(Role.ADMIN.name())
                    .requestMatchers(HttpMethod.PATCH, "/api/v1/category").hasAuthority(Role.ADMIN.name())
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/category/{id}").hasAuthority(Role.ADMIN.name())
                    .requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())
                    .requestMatchers("/api/v1/user").hasAnyAuthority(Role.CUSTOMER.name())
                    .requestMatchers("/api/v1/wishlist/**").authenticated()
                    .requestMatchers("/api/v1/orderItem/**").authenticated()
                    .requestMatchers("/api/v1/order").authenticated()
                    .anyRequest().authenticated();
        });
        http.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider()).addFilterBefore(
                jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
        );
        return (SecurityFilterChain)http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
