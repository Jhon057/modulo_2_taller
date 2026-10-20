package com.hotel.exception;

public class HabitacionOcupadaException extends RuntimeException {
    public HabitacionOcupadaException(String mensaje) {
        super(mensaje);
    }
}
