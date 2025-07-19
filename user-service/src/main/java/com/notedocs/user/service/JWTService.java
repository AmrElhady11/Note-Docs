package com.notedocs.user.service;

import com.notedocs.user.entity.User;
import com.notedocs.user.repository.TokenRepository;
import com.notedocs.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

public interface JWTService {
    public String generateToken(Map<String , Object>extraClaims, UserDetails user);
    public String generateToken(UserDetails user);
    public String extractUsername(String token);
    public boolean isTokenValid(String token,UserDetails user);


}
