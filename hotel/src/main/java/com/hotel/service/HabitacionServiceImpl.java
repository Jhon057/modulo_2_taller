package com.hotel.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.dto.mapper.HabitacionMapper;
import com.hotel.dto.request.HabitacionRequestDto;
import com.hotel.dto.response.HabitacionResponseDto;
import com.hotel.enums.EstadoReserva;
import com.hotel.exception.HabitacionNoEncontradoException;
import com.hotel.exception.HabitacionOcupadaException;
import com.hotel.exception.HabitacionRegistroException;
import com.hotel.exception.HabitacionRegistroTipoHException;
import com.hotel.exception.HotelNoEncontradoException;
import com.hotel.exception.ReservaActivaException;
import com.hotel.model.Habitacion;
import com.hotel.model.Hotel;
import com.hotel.model.Reserva;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HotelRepository;
import com.hotel.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HotelRepository hotelRepository;
    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;
    private final ReservaRepository reservaRepository;

    @Override
    public HabitacionResponseDto crear(HabitacionRequestDto request, Long hotelId) {

        if (!habitacionRepository.existsByTipoHabitacion(request.getTipoHabitacion()))
            throw new HabitacionRegistroTipoHException(request.getTipoHabitacion());

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNoEncontradoException(hotelId));

        if (habitacionRepository.existsByNumeroIgnoreCaseAndHotelId(request.getNumero(), hotelId)) {
            throw new HabitacionRegistroException(request.getNumero());
        }

        Habitacion habitacion = habitacionMapper.aEntidad(request);
        habitacion.setHotel(hotel);

        Habitacion habitacionGuardada = habitacionRepository.save(habitacion);

        return habitacionMapper.aRespuesta(habitacionGuardada);
    }

    @Override
    public List<HabitacionResponseDto> listarTodos() {
        return habitacionRepository.findAll().stream().map(habitacionMapper::aRespuesta).toList();
    }

    @Override
    public HabitacionResponseDto buscarPorId(Long id) {
        return habitacionRepository.findById(id).map(habitacionMapper::aRespuesta)
                .orElseThrow(() -> new HabitacionNoEncontradoException(id));
    }

    @Override
    public List<HabitacionResponseDto> listarDisponiblesPorHotel(Long hotelId) {

        hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNoEncontradoException(hotelId));

        return habitacionRepository.listarDisponiblesPorHotel(hotelId)
                .stream()
                .map(habitacionMapper::aRespuesta)
                .toList();
    }

    @Override
    public HabitacionResponseDto actualizar(Long id, HabitacionRequestDto request) {

        return habitacionRepository.findById(id).map(h -> {
            h.setNumero(request.getNumero());
            h.setTipoHabitacion(request.getTipoHabitacion());
            h.setPrecioNoche(request.getPrecioNoche());
            habitacionRepository.save(h);
            return habitacionMapper.aRespuesta(h);
        }).orElseThrow(() -> new HabitacionNoEncontradoException(id));

    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new HabitacionNoEncontradoException(id));

        if (reservaRepository.existsByHabitacionIdAndEstadoReservaNot(id, EstadoReserva.CANCELADA)) {
            throw new HabitacionOcupadaException("La habitación tiene reservas activas y no puede ser eliminada");
        }

        habitacionRepository.delete(habitacion);
    }

    @Override
    public List<HabitacionResponseDto> buscarPorPrecioMaximo(BigDecimal precioMaximo) {
        return habitacionRepository.filtroPrecio(precioMaximo).stream().map(habitacionMapper::aRespuesta).toList();
    }

    @Transactional
    @Override
    public void cambiarEstadoHabitacion(Long habitacionId) {

        Habitacion habitacion = habitacionRepository.findById(habitacionId)
                .orElseThrow(() -> new HabitacionNoEncontradoException(habitacionId));

        Optional<Reserva> reserva = reservaRepository.findFirstByHabitacionId(habitacionId);

        if (reserva.isPresent()) {
            EstadoReserva estado = reserva.get().getEstadoReserva();

            if (estado == EstadoReserva.PENDIENTE
                    || estado == EstadoReserva.CONFIRMADA) {
                throw new ReservaActivaException(
                        "No se puede cambiar el estado de la habitación porque tiene una reserva activa.");
            }
        }

        boolean disponible = !Boolean.TRUE.equals(habitacion.getDisponible());

        habitacion.setDisponible(disponible);

    }

}