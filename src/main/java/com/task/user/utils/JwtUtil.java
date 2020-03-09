package com.task.user.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    private static final String secret = "qazwsxedcrfv";

    private static final long timeOfLeaf = 24*60*60*1000;

    public String getUsernameForToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean tokenIsExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public String generateTokenForUser(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails.getUsername());
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String userName = getUsernameForToken(token);
        return userName.equals(userDetails.getUsername()) && !tokenIsExpired(token);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(Map<String, Object> claims, String login) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ timeOfLeaf))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
