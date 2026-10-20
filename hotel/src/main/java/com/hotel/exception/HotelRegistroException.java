package com.hotel.exception;

public class HotelRegistroException extends RuntimeException {
    public HotelRegistroException(String ciudad, String nombre) {
        super("Ya existe registro de un hotel con nombre" + nombre + "en la misma ciudad :" + ciudad);
    }
}
