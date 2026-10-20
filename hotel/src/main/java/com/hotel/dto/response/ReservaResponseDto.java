package com.hotel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hotel.enums.EstadoReserva;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaResponseDto {

    private Long id;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private EstadoReserva estadoReserva;
    private BigDecimal totalCalculado;

}
