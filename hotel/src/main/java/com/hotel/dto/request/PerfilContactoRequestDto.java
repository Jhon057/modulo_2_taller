package com.hotel.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilContactoRequestDto {

    @NotBlank(message = "El valor de teléfono no debe quedar vacío")
    private String telefono;

    @NotBlank(message = "El valor de teléfono de emergencia no debe quedar vacío")
    private String telefonoEmergencia;

    @NotBlank(message = "El valor de dirección no debe quedar vacío")
    private String direccion;

    @NotBlank(message = "El valor de país no debe quedar vacío")
    private String pais;

    @NotBlank(message = "El valor de nacionalidad no debe quedar vacío")
    private String nacionalidad;

}
