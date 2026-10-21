package api.conecta_barrio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import api.conecta_barrio.dto.request.LiderRequestDto;
import api.conecta_barrio.dto.response.LiderResponseDto;
import api.conecta_barrio.exceptions.LiderRegistroException;
import api.conecta_barrio.exceptions.RegistroNoEncontradoException;
import api.conecta_barrio.mapper.LiderMapper;
import api.conecta_barrio.models.Lider;
import api.conecta_barrio.repositories.LiderRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LiderServiceImpl implements LiderService {

    private LiderRepository liderRepository;
    private LiderMapper liderMapper;

    @Override
    public LiderResponseDto registrar(LiderRequestDto dto) {
        if (dto == null)
            throw new LiderRegistroException("Debe ingresar datos del líder");
        validarCorreo(dto.getEmail());
        Lider lider = liderMapper.aEntidad(dto);
        Lider liderRegistro = liderRepository.save(lider);
        return liderMapper.aRespuesta(liderRegistro);
    }

    @Override
    public List<LiderResponseDto> listar() {
        return liderRepository.findAll().stream().map(liderMapper::aRespuesta).toList();
    }

    @Override
    public LiderResponseDto buscarPorId(Long id) {
        return liderRepository.findById(id).map(liderMapper::aRespuesta)
                .orElseThrow(() -> new RegistroNoEncontradoException(id));
    }

    @Override
    public LiderResponseDto actualizar(Long id, LiderRequestDto dto) {
        validarCorreoActualizacion(dto.getEmail(), id);
        return liderRepository.findById(id).map(l -> {
            l.setEmail(dto.getEmail());
            l.setNombre(dto.getNombre());
            l.setTelefono(dto.getTelefono());
            Lider liderActualizado = liderRepository.save(l);
            return liderMapper.aRespuesta(liderActualizado);

        }).orElseThrow(() -> new RegistroNoEncontradoException(id));
    }

    @Override
    public void eliminar(Long id) {
        Lider liderExistente = liderRepository.findById(id).orElseThrow(
                () -> new RegistroNoEncontradoException(id));
        liderRepository.delete(liderExistente);
    }

    private void validarCorreo(String correo) {
        if (liderRepository.existsByIgnoreCaseEmail(correo)) {
            throw new LiderRegistroException("ya se encuentra el correo registrado");
        }
    }

    private void validarCorreoActualizacion(String correo, Long id) {
        if (liderRepository.existsByEmailIgnoreCaseAndIdNot(correo, id)) {
            throw new LiderRegistroException("Ya se encuentra el correo registrado");
        }
    }
}
