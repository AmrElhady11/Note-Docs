package com.notedocs.user.service.impl;

import com.notedocs.user.repository.TokenRepository;
import com.notedocs.user.repository.UserRepository;
import com.notedocs.user.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JWTServiceImpl implements JWTService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final Date JWT_EXPIRATION = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
    private final String SECRET_KEY = "a3f5c1b9e8d7425f9c6a1e4b7d3f8a0c9e2b4d1f6a7c3e8b5d9f0a2c7e4b8d1";


    @Override
    public String generateToken(Map<String , Object>extraClaims, UserDetails user) {
        return Jwts.builder()
                .addClaims(extraClaims)
                .setExpiration(JWT_EXPIRATION)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(user.getUsername())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();



    }
    @Override
    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }
    @Override
    public String extractUsername(String token) {
        return extractOneClaim(token, Claims::getSubject);
    }
    private Date extractExpiration(String token) {
        return extractOneClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }
    @Override
    public boolean isTokenValid(String token,UserDetails user) {
        return !isTokenExpired(token) && user.getUsername().equals(extractUsername(token));
}
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private <T> T extractOneClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);

    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
