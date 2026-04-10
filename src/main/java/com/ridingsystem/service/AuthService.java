package com.ridingsystem.service;

import com.ridingsystem.dto.AuthResponse;
import com.ridingsystem.dto.LoginRequest;
import com.ridingsystem.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
