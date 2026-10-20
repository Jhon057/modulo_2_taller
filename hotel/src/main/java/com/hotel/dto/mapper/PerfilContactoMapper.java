package com.hotel.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotel.dto.request.PerfilContactoRequestDto;
import com.hotel.dto.response.PerfilContactoResponseDto;
import com.hotel.model.PerfilContacto;

@Mapper(componentModel = "spring")
public interface PerfilContactoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    PerfilContacto aEntidad(PerfilContactoRequestDto request);

    @Mapping(source = "huesped.id", target = "huespedId")
    PerfilContactoResponseDto aRespuesta(PerfilContacto contacto);
}
