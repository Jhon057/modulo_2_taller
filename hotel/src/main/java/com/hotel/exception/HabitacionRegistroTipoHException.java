package com.hotel.exception;

import com.hotel.enums.TipoHabitacion;

public class HabitacionRegistroTipoHException extends RuntimeException {
    public HabitacionRegistroTipoHException(TipoHabitacion tipoHabitacion) {
        super("No existe tipo de habitación  :" + tipoHabitacion);
    }
}
