package com.hotel.exception;

public class HotelNoEncontradoException extends RuntimeException {
    public HotelNoEncontradoException(Long hotelId) {
        super("No se encuentra habitación con id: " + hotelId);
    }
}
