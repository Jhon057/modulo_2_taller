package com.hotel.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotel.dto.request.ServicioRequestDto;
import com.hotel.dto.response.ServicioResponseDto;
import com.hotel.model.Servicio;

@Mapper(componentModel = "spring")
public interface ServicioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "disponible", ignore = true)
    Servicio aEntidad(ServicioRequestDto request);

    ServicioResponseDto aRespuesta(Servicio servicio);
}
