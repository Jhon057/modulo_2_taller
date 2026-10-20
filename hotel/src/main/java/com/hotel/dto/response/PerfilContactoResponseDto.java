package com.hotel.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilContactoResponseDto {

    private Long id;
    private String telefono;
    private String telefonoEmergencia;
    private String direccion;
    private String pais;
    private String nacionalidad;
    private Long huespedId;
}
