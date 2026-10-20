package com.hotel.service;

import java.util.List;

import com.hotel.dto.request.HotelRequestDto;
import com.hotel.dto.response.HotelResponseDto;
import com.hotel.dto.response.HotelResponseHabitacionDto;

public interface HotelService {

    HotelResponseDto crear(HotelRequestDto request);

    List<HotelResponseDto> listarTodos();

    HotelResponseDto buscarPorId(Long id);

    List<HotelResponseDto> buscarPorCiudad(String ciudad);

    HotelResponseDto actualizar(Long id, HotelRequestDto request);

    void eliminar(Long id);

    List<HotelResponseDto> habitacionDisponible(Long cantidadHabitaciones);

    HotelResponseHabitacionDto listarHotelHabitaciones(Long idHotel);
}
