package com.hotel.exception;

public class HabitacionRegistroException extends RuntimeException {
    public HabitacionRegistroException(String habitacionNumero) {
        super("Ya existe una habitación con el mismo numero :" + habitacionNumero);
    }
}
