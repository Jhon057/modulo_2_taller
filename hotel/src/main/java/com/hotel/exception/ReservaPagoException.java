package com.hotel.exception;

public class ReservaPagoException extends RuntimeException {
    public ReservaPagoException(String mensaje) {
        super(mensaje);
    }
}
