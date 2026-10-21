package api.conecta_barrio.services;

import java.util.List;

import api.conecta_barrio.dto.request.SolicitudRequestDto;
import api.conecta_barrio.dto.response.SolicitudResponseDto;
import api.conecta_barrio.enums.EstadoSolicitud;

public interface SolicitudService {

    SolicitudResponseDto registrar(SolicitudRequestDto dto);

    List<SolicitudResponseDto> listar();

    SolicitudResponseDto buscarPorId(Long id);

    SolicitudResponseDto actualizar(Long id, SolicitudRequestDto dto);

    void eliminar(Long id);

    void cambiarEstado(Long id, EstadoSolicitud estado);

    void cambiarPrioridad(Long id);

}
