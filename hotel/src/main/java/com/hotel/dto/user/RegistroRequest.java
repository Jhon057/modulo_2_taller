package com.hotel.dto.user;

import com.hotel.enums.Rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroRequest(
        @NotBlank(message = "El username es obligatorio") String username,
        @NotBlank(message = "El password es obligatorio") String password,
        @NotNull(message = "El rol es obligatorio") Rol rol) {

}
