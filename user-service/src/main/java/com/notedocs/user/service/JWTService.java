package com.notedocs.user.service;


import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

public interface JWTService {
    public String generateToken(Map<String , Object>extraClaims, UserDetails user);
    public String generateToken(UserDetails user);
    public String extractUsername(String token);
    public boolean isTokenValid(String token,UserDetails user);


}
