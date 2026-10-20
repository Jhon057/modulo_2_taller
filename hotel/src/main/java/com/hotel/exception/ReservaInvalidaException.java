package com.hotel.exception;

public class ReservaInvalidaException extends RuntimeException {
    public ReservaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
