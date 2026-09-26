package com.saswat.lovable.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AuthUtil {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long userId, String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*100))
                .claim("userId", userId)
                .signWith(getSecretKey())
                .compact();
    }

    public JwtUserPrincipal verifyAccessToken(String token) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        final Long userId = extractUserId(claims);
        final String username = claims.getSubject();
        final List<GrantedAuthority> authorities = new ArrayList<>();
        return new JwtUserPrincipal(userId, username, authorities);
    }

    public Long extractUserId(Claims claims) {
        final Object rawUserId = claims.get("userId");
        if (rawUserId instanceof Number) {
            final Number numericUserId = (Number) rawUserId;
            return numericUserId.longValue();
        }
        if (rawUserId instanceof String) {
            final String textUserId = (String) rawUserId;
            return Long.parseLong(textUserId);
        }
        return claims.get("userId", Long.class);
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal userPrincipal)) {
            throw new AuthenticationCredentialsNotFoundException("No JWT Found");
        }
        return userPrincipal.userId();
    }
}
