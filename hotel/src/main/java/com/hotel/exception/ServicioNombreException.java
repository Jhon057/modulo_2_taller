package com.hotel.exception;

public class ServicioNombreException extends RuntimeException {
    public ServicioNombreException(String nombreServicio) {
        super("Ya existe un servicio con nombre" + nombreServicio);
    }
}
