package com.hotel.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicioResponseDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Boolean disponible;
}
