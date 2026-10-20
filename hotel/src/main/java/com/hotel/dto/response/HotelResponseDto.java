package com.hotel.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelResponseDto {

    private Long id;
    private String nombre;
    private String ciudad;
    private String direccion;
    private Integer categoria;
    private String telefono;
}
