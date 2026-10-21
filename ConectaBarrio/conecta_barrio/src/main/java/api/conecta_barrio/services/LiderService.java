package api.conecta_barrio.services;

import java.util.List;

import api.conecta_barrio.dto.request.LiderRequestDto;
import api.conecta_barrio.dto.response.LiderResponseDto;

public interface LiderService {

    LiderResponseDto registrar(LiderRequestDto dto);

    List<LiderResponseDto> listar();

    LiderResponseDto buscarPorId(Long id);

    LiderResponseDto actualizar(Long id, LiderRequestDto dto);

    void eliminar(Long id);

}
