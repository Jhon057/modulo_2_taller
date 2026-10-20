package com.hotel.dto.response;

import java.util.List;

import com.hotel.model.PerfilContacto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HuespedResponseDatosDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String documentoIdentidad;
    private String email;
    private PerfilContacto perfilContacto;
    private List<ReservaResponseDto> reservas;
}
