package com.hotel.exception;

public class ReservaNoEncontradaException extends RuntimeException {
    public ReservaNoEncontradaException(Long reservaId) {
        super("No existe reserva con id " + reservaId);
    }
}
