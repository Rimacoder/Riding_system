package com.ridingsystem.dto;

import com.ridingsystem.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phone;
    @NotNull
    private Role role;
    private String vehicleModel;
    private String licensePlate;
    private Integer vehicleCapacity;
}
