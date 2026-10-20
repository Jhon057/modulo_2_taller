package com.hotel.service;

import java.util.List;

import com.hotel.dto.request.ServicioRequestDto;
import com.hotel.dto.response.ServicioResponseDto;

public interface ServicioService {

    ServicioResponseDto crear(ServicioRequestDto request);

    List<ServicioResponseDto> listarTodos();

    List<ServicioResponseDto> listarDisponibles();

    ServicioResponseDto buscarPorId(Long id);

    ServicioResponseDto actualizar(Long id, ServicioRequestDto request);

    void eliminar(Long id);

    ServicioResponseDto cambiarEstado(Long id);
}
