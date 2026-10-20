package com.hotel.exception;

public class ReservaCanceladaException extends RuntimeException {
    public ReservaCanceladaException() {
        super("la reserva se encuentra en estado cancelada ");
    }
}
