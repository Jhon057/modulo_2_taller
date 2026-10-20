package com.hotel.service;

import java.math.BigDecimal;
import java.util.List;

import com.hotel.dto.request.HabitacionRequestDto;
import com.hotel.dto.response.HabitacionResponseDto;

public interface HabitacionService {

    HabitacionResponseDto crear(HabitacionRequestDto request, Long hotelId);

    List<HabitacionResponseDto> listarTodos();

    HabitacionResponseDto buscarPorId(Long id);

    List<HabitacionResponseDto> listarDisponiblesPorHotel(Long hotelId);

    HabitacionResponseDto actualizar(Long id, HabitacionRequestDto request);

    void eliminar(Long id);

    List<HabitacionResponseDto> buscarPorPrecioMaximo(BigDecimal precioMaximo);

    void cambiarEstadoHabitacion(Long habitacionId);

}
