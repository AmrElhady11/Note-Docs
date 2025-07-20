package com.notedocs.user.service;

import com.notedocs.user.entity.Authority;
import com.notedocs.user.entity.Role;
import com.notedocs.user.entity.Token;
import com.notedocs.user.entity.User;
import com.notedocs.user.enums.UserRole;
import com.notedocs.user.repository.*;
import com.notedocs.user.request.LoginRequest;
import com.notedocs.user.request.RegisterRequest;
import com.notedocs.user.response.LoginResponse;
import com.notedocs.user.response.RegisterResponse;
import com.notedocs.user.util.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.notedocs.user.enums.TokenType;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OTPService otpService;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    private final MapperUtil mapperUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        isEmailNotExist(request.getEmail());
        isUsernameNotExist(request.getUsername());

        User newUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .build();
        Role role = Role.builder()
                .role(UserRole.USER)
                .build();
        Set<Authority> authorities = new HashSet<>();
        role.setAuthorities(authorities);
        newUser.setRoles(Set.of(role));
        userRepository.save(newUser);

        otpService.sendOTP(request.getEmail());

        return mapperUtil.mapEntity(newUser, RegisterResponse.class);
    }

    public LoginResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeToken(user);
        saveUserToken(user, jwtToken);
        return LoginResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void isEmailNotExist(String email) {
        var user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            System.out.println("User email already exists");  // will be updated after adding global exception handling
        }
    }

    private void isUsernameNotExist(String username) {
        var user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            System.out.println("Username already exists");  // will be updated after adding global exception handling
        }
    }


    private void revokeToken(User user) {
        var validToken = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validToken.isEmpty()) {
            return;
        }
        validToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}