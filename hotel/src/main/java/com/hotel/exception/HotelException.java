package com.hotel.exception;

public class HotelException extends RuntimeException {
    public HotelException(String mensaje) {
        super("hay error en el ingreso de los datos");
    }
}
