package com.hotel.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaRequestDto {

    @FutureOrPresent(message = "la reserva no se puede hacer con una fecha pasada")
    @NotNull(message = "Debe ingresar la fecha de ingreso")
    private LocalDate fechaIngreso;

    @FutureOrPresent(message = "la reserva no se puede hacer con una fecha pasada")
    @NotNull(message = "Debe ingresar la fecha de salida")
    private LocalDate fechaSalida;

    @NotNull(message = "Debe ingresar el id del huésped")
    private Long huespedId;

    @NotNull(message = "Debe ingresar el id de la habitación")
    private Long habitacionId;

    private List<Long> serviciosId;
}
