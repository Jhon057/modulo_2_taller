package com.hotel.exception;

public class HuespedNoEncontradoException extends RuntimeException {
    public HuespedNoEncontradoException(Long huespedId) {
        super("No se encuentra huesped con id: " + huespedId);
    }
}
