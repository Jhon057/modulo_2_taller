package com.hotel.service;

import com.hotel.dto.mapper.HuespedMapper;
import com.hotel.dto.mapper.PerfilContactoMapper;
import com.hotel.dto.request.HuespedRequestDto;
import com.hotel.dto.response.HuespedResponseDatosDto;
import com.hotel.dto.response.HuespedResponseDto;
import com.hotel.enums.EstadoReserva;
import com.hotel.exception.HuespedNoEncontradoException;
import com.hotel.exception.HuespedRegistroCorreoException;
import com.hotel.exception.HuespedRegistroDocumentoException;
import com.hotel.exception.ReservaActivaException;
import com.hotel.exception.errors.ValidacionException;
import com.hotel.model.Huesped;
import com.hotel.model.PerfilContacto;
import com.hotel.repository.HuespedRepository;
import com.hotel.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HuespedServiceImpl implements HuespedService {

    private final HuespedRepository huespedRepository;
    private final ReservaRepository reservaRepository;
    private final PerfilContactoMapper perfilContactoMapper;
    private final HuespedMapper huespedMapper;
    List<String> errores = new ArrayList<>();

    @Override
    public HuespedResponseDto crear(HuespedRequestDto request) {

        if (huespedRepository.existsByIgnoreCaseDocumentoIdentidad(
                request.getDocumentoIdentidad())) {

            errores.add("El huésped con número de identificación "
                    + request.getDocumentoIdentidad()
                    + " ya se encuentra registrado.");
        }

        if (huespedRepository.existsByIgnoreCaseEmail(request.getEmail())) {

            errores.add("El huésped con correo "
                    + request.getEmail()
                    + " ya se encuentra registrado.");
        }

        if (!errores.isEmpty()) {
            throw new ValidacionException(errores);
        }
        Huesped huesped = huespedMapper.aEntidad(request);
        PerfilContacto perfil = perfilContactoMapper.aEntidad(request.getPerfilContacto());

        // Mantener sincronizada la relación bidireccional
        huesped.setPerfilContacto(perfil);
        perfil.setHuesped(huesped);

        Huesped huespedGuardado = huespedRepository.save(huesped);

        return huespedMapper.aRespuesta(huespedGuardado);
    }

    @Override
    public List<HuespedResponseDto> listarTodos() {
        return huespedRepository.findAll().stream().map(huespedMapper::aRespuesta).toList();
    }

    @Override
    public HuespedResponseDto buscarPorId(Long id) {
        return huespedRepository.findById(id).map(huespedMapper::aRespuesta)
                .orElseThrow(() -> new HuespedNoEncontradoException(id));
    }

    @Override
    public HuespedResponseDto actualizar(Long id, HuespedRequestDto request) {
        if (huespedRepository.existsByIgnoreCaseDocumentoIdentidad(request.getDocumentoIdentidad()))
            throw new HuespedRegistroDocumentoException(request.getDocumentoIdentidad());

        if (huespedRepository.existsByIgnoreCaseEmail(request.getEmail()))
            throw new HuespedRegistroCorreoException(request.getEmail());

        return huespedRepository.findById(id).map(h -> {
            h.setNombre(request.getNombre());
            h.setApellido(request.getApellido());
            h.setDocumentoIdentidad(request.getDocumentoIdentidad());
            h.setEmail(request.getEmail());
            huespedRepository.save(h);
            return huespedMapper.aRespuesta(h);
        }).orElseThrow(() -> new HuespedNoEncontradoException(id));

    }

    @Override
    public void eliminar(Long id) {
        Huesped huesped = huespedRepository.findById(id).orElseThrow(
                () -> new HuespedNoEncontradoException(id));

        if (reservaRepository
                .existsByHuespedIdAndEstadoReservaIn(
                        id,
                        List.of(
                                EstadoReserva.CONFIRMADA,
                                EstadoReserva.PENDIENTE
                        ))
        )
            throw new ReservaActivaException("El usuario debe cancelar reserva activa en estado CONFIRMADA o PENDIENTE");

        huespedRepository.delete(huesped);

    }

    @Override
    public List<HuespedResponseDto> buscarPorNombreOApellido(String texto) {
        return huespedRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(texto, texto).stream()
                .map(huespedMapper::aRespuesta).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HuespedResponseDatosDto huespedDatos(Long id) {

        Huesped encontrado = huespedRepository.findByIdConDatos(id)
                .orElseThrow(() -> new HuespedNoEncontradoException(id));

        return huespedMapper.aDatos(encontrado);
    }

}
