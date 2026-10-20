package com.hotel.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotel.dto.request.HabitacionRequestDto;
import com.hotel.dto.response.HabitacionResponseDto;
import com.hotel.model.Habitacion;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "disponible", ignore = true)
    Habitacion aEntidad(HabitacionRequestDto request);

    @Mapping(source = "hotel.id", target = "idHotel")
    @Mapping(source = "hotel.nombre", target = "nombreHotel")
    HabitacionResponseDto aRespuesta(Habitacion habitacion);

    List<HabitacionResponseDto> aRespuesta(List<Habitacion> habitaciones);

}
