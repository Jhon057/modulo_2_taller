package api.conecta_barrio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import api.conecta_barrio.dto.request.SolicitudRequestDto;
import api.conecta_barrio.dto.response.SolicitudResponseDto;
import api.conecta_barrio.enums.EstadoSolicitud;
import api.conecta_barrio.exceptions.RegistroNoEncontradoException;
import api.conecta_barrio.exceptions.SolicitudCambiarEstadoException;
import api.conecta_barrio.exceptions.SolicitudRegistroException;
import api.conecta_barrio.mapper.SolicitudMapper;
import api.conecta_barrio.models.Lider;
import api.conecta_barrio.models.Solicitud;
import api.conecta_barrio.repositories.LiderRepository;
import api.conecta_barrio.repositories.SolicitudRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SolicitudServiceImpl implements SolicitudService {

    private SolicitudRepository solicitudRepository;
    private SolicitudMapper solicitudMapper;
    private LiderRepository liderRepository;

    @Override
    public SolicitudResponseDto registrar(SolicitudRequestDto dto) {
        if (dto == null)
            throw new SolicitudRegistroException("Se debe ingresar el registro de la solicitud");
        Lider liderEncontrado = liderRepository.findById(dto.getIdLider())
                .orElseThrow(() -> new RegistroNoEncontradoException(dto.getIdLider()));
        if (solicitudRepository.existsByIgnoringCaseTitulo(dto.getTitulo()))
            throw new SolicitudRegistroException("El título ya se encuentra registrado");

        Solicitud solicitud = solicitudMapper.aEntidad(dto);
        solicitud.setLider(liderEncontrado);
        Solicitud solicitudRegistro = solicitudRepository.save(solicitud);
        return solicitudMapper.aRespuesta(solicitudRegistro);
    }

    @Override
    public List<SolicitudResponseDto> listar() {
        return solicitudRepository.findAll().stream().map(solicitudMapper::aRespuesta).toList();
    }

    @Override
    public SolicitudResponseDto buscarPorId(Long id) {
        return solicitudRepository.findById(id).map(solicitudMapper::aRespuesta)
                .orElseThrow(() -> new RegistroNoEncontradoException(id));
    }

    @Override
    public SolicitudResponseDto actualizar(Long id, SolicitudRequestDto dto) {
        if (dto == null) {
            throw new SolicitudRegistroException(
                    "Se debe ingresar el registro de la solicitud");
        }

        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RegistroNoEncontradoException(id));

        Lider liderEncontrado = liderRepository.findById(dto.getIdLider())
                .orElseThrow(() -> new RegistroNoEncontradoException(dto.getIdLider()));

        validarTituloRegistro(dto.getTitulo(), id);

        solicitud.setTitulo(dto.getTitulo());
        solicitud.setDescripcion(dto.getDescripcion());
        solicitud.setLider(liderEncontrado);

        Solicitud solicitudActualizada = solicitudRepository.save(solicitud);

        return solicitudMapper.aRespuesta(solicitudActualizada);
    }

    @Override
    public void eliminar(Long id) {
        Solicitud solicitudExistente = solicitudRepository.findById(id).orElseThrow(
                () -> new RegistroNoEncontradoException(id));
        solicitudRepository.delete(solicitudExistente);
    }

    private void validarTituloRegistro(String titulo, Long id) {
        if (solicitudRepository.existsByTituloAndIdNot(titulo, id)) {
            throw new SolicitudRegistroException("Ya se encuentra el titulo registrado");
        }
    }

    @Override
    public void cambiarEstado(Long id, EstadoSolicitud nuevoEstado) {

        Solicitud solicitudExistente = solicitudRepository.findById(id)
                .orElseThrow(() -> new RegistroNoEncontradoException(id));

        EstadoSolicitud estadoActual = solicitudExistente.getEstado();

        if (!esTransicionValida(estadoActual, nuevoEstado)) {
            throw new SolicitudCambiarEstadoException(nuevoEstado);
        }

        solicitudExistente.setEstado(nuevoEstado);

        solicitudRepository.save(solicitudExistente);
    }

    @Override
    public void cambiarPrioridad(Long id) {

        Solicitud solicitudExistente = solicitudRepository.findById(id)
                .orElseThrow(() -> new RegistroNoEncontradoException(id));

        solicitudExistente.setPrioridad(!solicitudExistente.getPrioridad());

        solicitudRepository.save(solicitudExistente);
    }

    private boolean esTransicionValida(
            EstadoSolicitud estadoActual,
            EstadoSolicitud nuevoEstado) {

        return switch (estadoActual) {

            case PENDIENTE ->
                nuevoEstado == EstadoSolicitud.EN_PROCESO
                        || nuevoEstado == EstadoSolicitud.CANCELADA;

            case EN_PROCESO ->
                nuevoEstado == EstadoSolicitud.RESUELTA
                        || nuevoEstado == EstadoSolicitud.CANCELADA;

            case RESUELTA, CANCELADA ->
                false;
        };
    }

}
