package com.hotel.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelRequestDto {

    @NotBlank(message = "El nombre no puede ser vacío")
    @Size(min = 3, max = 50, message = "El nombre  debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "la ciudad no puede ser vacío")
    @Size(min = 3, max = 50, message = "La ciudad  debe tener entre 3 y 50 caracteres")
    private String ciudad;

    @NotBlank(message = "La dirección no puede ser vacío")
    @Size(min = 3, max = 50, message = "La dirección debe tener entre 3 y 50 caracteres")
    private String direccion;

    @NotNull(message = "La categoría no puede ser vacía")
    @Min(value = 1, message = "La categoría debe ser mínimo 1")
    @Max(value = 5, message = "La categoría debe ser máximo 5")
    private Integer categoria;

    @Size(min = 3, max = 15, message = "El teléfono debe tener entre 3 y 20 caracteres")
    @NotBlank(message = "El valor del teléfono no puede ser vacío")
    private String telefono;
}
