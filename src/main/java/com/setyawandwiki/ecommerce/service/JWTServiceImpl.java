package com.setyawandwiki.ecommerce.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService{
    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 24))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignatureKey(){
        byte[] key = Decoders.BASE64.decode("0d2c56d24b9425b934c5ed1b8d444525f594d6aaa1068e993ac233977d31ed9c");
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolve){
        final Claims claims = extractAllClaims(token);
        return claimsResolve.apply(claims);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String generateRefreshToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        return null;
    }
}
