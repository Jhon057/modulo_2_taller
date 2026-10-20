package com.hotel.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HuespedResponseDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String documentoIdentidad;
    private String email;
}
