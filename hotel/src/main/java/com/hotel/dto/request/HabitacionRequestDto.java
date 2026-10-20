package com.hotel.dto.request;

import java.math.BigDecimal;

import com.hotel.enums.TipoHabitacion;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitacionRequestDto {

    @NotBlank(message = "El nombre no puede ser vacío")
    private String numero;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El nombre no puede ser vacío")
    private TipoHabitacion tipoHabitacion;

    @NotNull(message = "Se debe ingresar el precio")
    @Positive
    private BigDecimal precioNoche;

}
