package com.hotel.exception.user;

public class NombreUsuarioRegistroException extends RuntimeException {
    public NombreUsuarioRegistroException(String nombreUsuario) {
        super("El usuario ya se encuentra registrado: " + nombreUsuario);
    }
}
