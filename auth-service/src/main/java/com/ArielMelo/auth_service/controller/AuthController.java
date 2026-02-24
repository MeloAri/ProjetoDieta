package com.ArielMelo.auth_service.controller;

import com.ArielMelo.auth_service.dtos.AuthResponseDTO;
import com.ArielMelo.auth_service.dtos.LoginRequestDTO;
import com.ArielMelo.auth_service.dtos.RegisterRequestDTO;
import com.ArielMelo.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody @Valid RegisterRequestDTO request) {

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO request) {

        return ResponseEntity.ok(service.login(request));
    }
}
