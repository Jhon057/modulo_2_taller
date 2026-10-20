package com.hotel.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotel.dto.request.HotelRequestDto;
import com.hotel.dto.response.HotelResponseDto;
import com.hotel.dto.response.HotelResponseHabitacionDto;
import com.hotel.model.Hotel;

@Mapper(componentModel = "spring", uses = HabitacionMapper.class)
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitaciones", ignore = true)
    Hotel aEntidad(HotelRequestDto request);

    HotelResponseDto aRespuesta(Hotel hotel);

    HotelResponseHabitacionDto aRespuestaHabitacion(Hotel hotel);

}
