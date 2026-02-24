package com.ArielMelo.auth_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @Email
    private String email;

    @NotBlank
    private String password;

}
