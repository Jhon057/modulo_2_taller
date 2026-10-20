package com.hotel.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotel.dto.request.HuespedRequestDto;
import com.hotel.dto.response.HuespedResponseDatosDto;
import com.hotel.dto.response.HuespedResponseDto;
import com.hotel.model.Huesped;

@Mapper(componentModel = "spring")
public interface HuespedMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    @Mapping(target = "perfilContacto", ignore = true)
    Huesped aEntidad(HuespedRequestDto request);

    HuespedResponseDto aRespuesta(Huesped huesped);

    HuespedResponseDatosDto aDatos(Huesped huesped);

}
