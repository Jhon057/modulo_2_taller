package com.hotel.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelResponseHabitacionDto {

    private Long id;
    private String nombre;
    List<HabitacionResponseDto> habitaciones;
}
