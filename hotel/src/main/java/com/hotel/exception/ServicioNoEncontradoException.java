package com.hotel.exception;

public class ServicioNoEncontradoException extends RuntimeException {
    public ServicioNoEncontradoException(Long servicioId) {
        super("No se encuentra el servicio para id: " + servicioId);
    }
}
