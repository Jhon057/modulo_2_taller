package com.hotel.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicioRequestDto {

    @NotBlank(message = "El nombre del servicio no puede ser vacío")
    private String nombre;

    @NotBlank(message = "La descripción del servicio no puede ser vacío")
    private String descripcion;

    @NotNull(message = "El precio del servicio no puede ser vació")
    @Positive(message = "El valor del servicio debe ser mayor a cero")
    private BigDecimal precio;
}
