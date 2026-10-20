package com.hotel.dto.response;

import java.math.BigDecimal;

import com.hotel.enums.TipoHabitacion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitacionResponseDto {

    private Long id;
    private String numero;
    private TipoHabitacion tipoHabitacion;
    private BigDecimal precioNoche;
    private Boolean disponible;
    private Long idHotel;
    private String nombreHotel;
}
