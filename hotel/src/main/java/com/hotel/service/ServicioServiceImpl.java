package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.dto.mapper.ServicioMapper;
import com.hotel.dto.request.ServicioRequestDto;
import com.hotel.dto.response.ServicioResponseDto;
import com.hotel.exception.ServicioNoEncontradoException;
import com.hotel.exception.ServicioNombreException;
import com.hotel.model.Servicio;
import com.hotel.repository.ServicioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;
    private final ServicioMapper servicioMapper;

    @Override
    public ServicioResponseDto crear(ServicioRequestDto request) {
        if (servicioRepository.existsByIgnoreCaseNombre(request.getNombre()))
            throw new ServicioNombreException(request.getNombre());

        Servicio servicio = servicioMapper.aEntidad(request);
        servicioRepository.save(servicio);
        return servicioMapper.aRespuesta(servicio);

    }

    @Override
    public List<ServicioResponseDto> listarTodos() {
        return servicioRepository.findAll().stream().map(servicioMapper::aRespuesta).toList();
    }

    @Override
    public List<ServicioResponseDto> listarDisponibles() {
        return servicioRepository.findByDisponibleTrue().stream().map(servicioMapper::aRespuesta).toList();
    }

    @Override
    public ServicioResponseDto buscarPorId(Long id) {
        return servicioRepository.findById(id).map(servicioMapper::aRespuesta)
                .orElseThrow(() -> new ServicioNoEncontradoException(id));

    }

    @Override
    public ServicioResponseDto actualizar(Long id, ServicioRequestDto request) {
        return servicioRepository.findById(id).map(s -> {
            s.setNombre(request.getNombre());
            s.setDescripcion(request.getDescripcion());
            s.setPrecio(request.getPrecio());
            servicioRepository.save(s);
            return servicioMapper.aRespuesta(s);

        }).orElseThrow(() -> new ServicioNoEncontradoException(id));
    }

    @Override
    public void eliminar(Long id) {
        Servicio servicio = servicioRepository.findById(id).orElseThrow(() -> new ServicioNoEncontradoException(id));

        servicioRepository.delete(servicio);
    }

    @Override
    @Transactional
    public ServicioResponseDto cambiarEstado(Long id) {

        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ServicioNoEncontradoException(id));

        servicio.setDisponible(!servicio.getDisponible());

        return servicioMapper.aRespuesta(servicio);
    }

}
