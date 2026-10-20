package com.hotel.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hotel.dto.request.ReservaRequestDto;
import com.hotel.dto.response.ReservaResponseDto;
import com.hotel.dto.response.ReservaResponseHuespedDto;
import com.hotel.model.Reserva;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estadoReserva", ignore = true)
    @Mapping(target = "totalCalculado", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    @Mapping(target = "servicios", ignore = true)
    Reserva aEntidad(ReservaRequestDto request);

    ReservaResponseDto aRespuesta(Reserva reserva);

    ReservaResponseHuespedDto aHuespedRespuesta(Reserva reserva);

    List<ReservaResponseDto> alistaRespuesta(List<Reserva> reservas);

}
