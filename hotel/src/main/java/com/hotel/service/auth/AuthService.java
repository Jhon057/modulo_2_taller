package com.hotel.service.auth;

import com.hotel.dto.user.AuthResponse;
import com.hotel.dto.user.LoginRequest;
import com.hotel.dto.user.RegistroRequest;

public interface AuthService {
    AuthResponse registrar(RegistroRequest request);

    AuthResponse login(LoginRequest request);
}
