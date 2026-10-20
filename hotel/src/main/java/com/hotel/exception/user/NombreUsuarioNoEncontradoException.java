package com.hotel.exception.user;

public class NombreUsuarioNoEncontradoException extends RuntimeException {
    public NombreUsuarioNoEncontradoException(String nombreUsuario) {
        super("El usuario no se encuentra registrado: " + nombreUsuario);
    }
}
