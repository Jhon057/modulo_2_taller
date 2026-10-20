package com.hotel.service;

import java.util.List;

import com.hotel.dto.request.ReservaRequestDto;
import com.hotel.dto.response.ReservaResponseDto;
import com.hotel.dto.response.ReservaResponseHuespedDto;
import com.hotel.enums.EstadoReserva;

public interface ReservaService {

    ReservaResponseDto crear(ReservaRequestDto request);

    List<ReservaResponseDto> listarTodosActivos();

    List<ReservaResponseDto> listarTodosCancelados();

    ReservaResponseDto actualizar(Long id, ReservaRequestDto request);

    void cancelar(Long id);

    void agregarServicio(Long reservaId, List<Long> servicioId);

    void eliminarServicio(Long reservaId, Long servicioId);

    void pagarReserva(Long reservaId);

    List<ReservaResponseHuespedDto> reservasPorHuesped(Long huespedId, EstadoReserva estadoReserva);

    List<ReservaResponseHuespedDto> todasReservasPorHuesped(Long huespedId);

    ReservaResponseDto findById(Long id);
}
