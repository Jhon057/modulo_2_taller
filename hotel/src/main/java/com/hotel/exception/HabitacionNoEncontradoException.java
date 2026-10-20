package com.hotel.exception;

public class HabitacionNoEncontradoException extends RuntimeException {
    public HabitacionNoEncontradoException(Long habitacionId) {
        super("No se encuentra habitación con id: " + habitacionId);
    }
}
