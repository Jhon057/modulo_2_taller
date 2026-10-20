package com.hotel.enums;

public enum EstadoReserva {
    PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA;

    public boolean puedeCambiarA(EstadoReserva nuevoEstado) {
        return switch (this) {
            case PENDIENTE ->
                nuevoEstado == CONFIRMADA || nuevoEstado == CANCELADA;
            case CONFIRMADA ->
                nuevoEstado == COMPLETADA || nuevoEstado == CANCELADA;
            case CANCELADA, COMPLETADA -> false;
        };
    }
}
