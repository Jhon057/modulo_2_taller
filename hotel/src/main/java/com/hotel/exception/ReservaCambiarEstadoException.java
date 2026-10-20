package com.hotel.exception;

import com.hotel.enums.EstadoReserva;

public class ReservaCambiarEstadoException extends RuntimeException {
    public ReservaCambiarEstadoException(EstadoReserva reserva) {
        super("No se puede cambiar el estado de la reserva" + reserva);
    }
}
