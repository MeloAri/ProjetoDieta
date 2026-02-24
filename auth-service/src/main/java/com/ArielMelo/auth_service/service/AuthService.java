package com.ArielMelo.auth_service.service;

import com.ArielMelo.auth_service.dtos.AuthResponseDTO;
import com.ArielMelo.auth_service.dtos.LoginRequestDTO;
import com.ArielMelo.auth_service.dtos.RegisterRequestDTO;
import com.ArielMelo.auth_service.entities.User;
import com.ArielMelo.auth_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO register(RegisterRequestDTO request){

        if (userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email já existe");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        return new AuthResponseDTO(token);
    }


public AuthResponseDTO login(LoginRequestDTO request) {

    User user = UserRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid credentials");
    }

    String token = jwtService.generateToken(user.getId(), user.getEmail());

    return new AuthResponseDTO(token);
}
}

