package com.notedocs.user.controller;

import com.notedocs.user.request.LoginRequest;
import com.notedocs.user.request.RegisterRequest;
import com.notedocs.user.response.LoginResponse;
import com.notedocs.user.response.RegisterResponse;
import com.notedocs.user.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<RegisterResponse> signUp(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);

    }
    @PostMapping("/LogIn")
    public ResponseEntity<LoginResponse> Login(@RequestBody LoginRequest request){


    }







}
