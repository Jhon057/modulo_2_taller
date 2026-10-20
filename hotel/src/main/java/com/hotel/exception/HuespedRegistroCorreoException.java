package com.hotel.exception;

public class HuespedRegistroCorreoException extends RuntimeException {
    public HuespedRegistroCorreoException(String correo) {
        super("El huesped con correo : " + correo + " ya se encuentra registrado");
    }
}
