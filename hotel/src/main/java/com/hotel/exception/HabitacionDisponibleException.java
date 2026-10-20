package com.hotel.exception;

public class HabitacionDisponibleException extends RuntimeException {
    public HabitacionDisponibleException(Long habitacionId) {
        super("La habitación con id " + habitacionId + " no se encuentra disponible");
    }
}
