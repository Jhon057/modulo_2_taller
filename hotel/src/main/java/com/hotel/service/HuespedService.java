package com.hotel.service;

import java.util.List;

import com.hotel.dto.request.HuespedRequestDto;
import com.hotel.dto.response.HuespedResponseDatosDto;
import com.hotel.dto.response.HuespedResponseDto;

public interface HuespedService {

    HuespedResponseDto crear(HuespedRequestDto request);

    List<HuespedResponseDto> listarTodos();

    HuespedResponseDto buscarPorId(Long id);

    HuespedResponseDto actualizar(Long id, HuespedRequestDto request);

    void eliminar(Long id);

    List<HuespedResponseDto> buscarPorNombreOApellido(String texto);

    HuespedResponseDatosDto huespedDatos(Long id);

}
