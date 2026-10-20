package com.hotel.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.dto.mapper.ReservaMapper;
import com.hotel.dto.request.ReservaRequestDto;
import com.hotel.dto.response.ReservaResponseDto;
import com.hotel.dto.response.ReservaResponseHuespedDto;
import com.hotel.enums.EstadoReserva;
import com.hotel.exception.HabitacionDisponibleException;
import com.hotel.exception.HabitacionOcupadaException;
import com.hotel.exception.HuespedNoEncontradoException;
import com.hotel.exception.ReservaAsignarServicioException;
import com.hotel.exception.ReservaCambiarEstadoException;
import com.hotel.exception.ReservaCanceladaException;
import com.hotel.exception.ReservaInvalidaException;
import com.hotel.exception.ReservaNoEncontradaException;
import com.hotel.exception.ReservaPagoException;
import com.hotel.model.Habitacion;
import com.hotel.model.Huesped;
import com.hotel.model.Reserva;
import com.hotel.model.Servicio;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.HuespedRepository;
import com.hotel.repository.ReservaRepository;
import com.hotel.repository.ServicioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ServicioRepository servicioRepository;
    private final ReservaMapper reservaMapper;
    private final HuespedRepository huespedRepository;
    private final HabitacionRepository habitacionRepository;

    @Transactional
    @Override
    public ReservaResponseDto crear(ReservaRequestDto request) {

        Huesped huesped = huespedRepository.findById(request.getHuespedId())
                .orElseThrow(() -> new HuespedNoEncontradoException(request.getHuespedId()));

        Habitacion habitacion = habitacionRepository.findById(request.getHabitacionId())
                .orElseThrow(() -> new HabitacionDisponibleException(request.getHabitacionId()));

        if (reservaRepository.existsByHabitacionIdAndFechaIngresoLessThanAndFechaSalidaGreaterThanAndEstadoReserva(
                request.getHabitacionId(),
                request.getFechaSalida(),
                request.getFechaIngreso(), EstadoReserva.CONFIRMADA)) {

            throw new HabitacionOcupadaException(
                    "La habitación ya se encuentra reservada para las fechas seleccionadas. Elija otras fechas.");
        }

        if (Boolean.FALSE.equals(habitacion.getDisponible()))
            throw new HabitacionDisponibleException(request.getHabitacionId());

        validarFecha(request.getFechaIngreso(), request.getFechaSalida());

        Reserva reserva = reservaMapper.aEntidad(request);
        reserva.setHuesped(huesped);
        reserva.setHabitacion(habitacion);

        if (request.getServiciosId() != null && !request.getServiciosId().isEmpty()) {
            List<Servicio> servicios = servicioRepository.findAllById(request.getServiciosId());
            reserva.setServicios(servicios);
        }
        reserva.setTotalCalculado(calcularTotal(reserva));

        Reserva reservaGuardada = reservaRepository.save(reserva);

        return reservaMapper.aRespuesta(reservaGuardada);
    }

    @Override
    public List<ReservaResponseDto> listarTodosActivos() {
        return reservaRepository.findAll().stream().filter(r -> r.getEstadoReserva() != EstadoReserva.CANCELADA)
                .map(reservaMapper::aRespuesta).toList();
    }

    @Override
    public List<ReservaResponseDto> listarTodosCancelados() {
        return reservaRepository.findAll().stream().filter(r -> r.getEstadoReserva() == EstadoReserva.CANCELADA)
                .map(reservaMapper::aRespuesta).toList();
    }

    @Override
    @Transactional
    public ReservaResponseDto actualizar(Long id, ReservaRequestDto request) {

        var huesped = huespedRepository.findById(request.getHuespedId())
                .orElseThrow(() -> new HuespedNoEncontradoException(request.getHuespedId()));

        Habitacion habitacion = habitacionRepository.findById(request.getHabitacionId())
                .orElseThrow(() -> new HabitacionDisponibleException(request.getHabitacionId()));

        if (reservaRepository.existsByHabitacionIdAndFechaIngresoLessThanAndFechaSalidaGreaterThanAndEstadoReserva(
                request.getHabitacionId(),
                request.getFechaSalida(),
                request.getFechaIngreso(), EstadoReserva.CONFIRMADA)) {

            throw new HabitacionOcupadaException(
                    "La habitación ya se encuentra reservada para las fechas seleccionadas. Elija otras fechas.");
        }
        validarFecha(request.getFechaIngreso(), request.getFechaSalida());

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA)
            throw new ReservaCanceladaException();

        reserva.setFechaIngreso(request.getFechaIngreso());
        reserva.setFechaSalida(request.getFechaSalida());
        reserva.setHuesped(huesped);
        reserva.setHabitacion(habitacion);
        reserva.setTotalCalculado(calcularTotal(reserva));

        return reservaMapper.aRespuesta(reserva);
    }

    @Override
    @Transactional
    public void cancelar(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No hay reserva con id: " + id));

        if (!reserva.getEstadoReserva().puedeCambiarA(EstadoReserva.CANCELADA)) {
            throw new ReservaCambiarEstadoException(reserva.getEstadoReserva());
        }

        long dias = ChronoUnit.DAYS.between(
                LocalDate.now(ZoneId.systemDefault()),
                reserva.getFechaIngreso());

        if (dias <= 1) {
            throw new ReservaInvalidaException(
                    "La cancelación de la reserva se debe hacer un día antes");
        }

        reserva.setEstadoReserva(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    @SuppressWarnings("null")
    @Override
    @Transactional
    public void agregarServicio(Long reservaId, List<Long> serviciosId) {

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNoEncontradaException(reservaId));

        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new ReservaCanceladaException();
        }

        List<Servicio> servicios = servicioRepository.findAllById(serviciosId);

        // Validar que todos los servicios existan
        if (servicios.size() != serviciosId.size()) {
            throw new ReservaAsignarServicioException("Uno o más servicios no existen.");
        }

        // Validar que todos los servicios estén disponibles
        for (Servicio servicio : servicios) {
            if (!Boolean.TRUE.equals(servicio.getDisponible())) {
                throw new ReservaAsignarServicioException(
                        "El servicio '" + servicio.getNombre() + "' se encuentra inactivo.");
            }
        }

        // Obtener los ids ya asociados a la reserva
        Set<Long> serviciosActuales = reserva.getServicios().stream()
                .map(Servicio::getId)
                .collect(Collectors.toSet());

        // Agregar únicamente los que no existan
        for (Servicio servicio : servicios) {
            if (serviciosActuales.add(servicio.getId())) {
                reserva.getServicios().add(servicio);
            } else
                throw new ReservaAsignarServicioException(
                        "El servicio " + servicio.getNombre() + " ya se encuentra asignado");

        }

        reserva.setTotalCalculado(calcularTotal(reserva));

        reservaMapper.aRespuesta(reservaRepository.save(reserva));
    }

    private BigDecimal calcularTotal(Reserva reserva) {

        if (reserva == null) {
            return BigDecimal.ZERO;
        }

        long noches = ChronoUnit.DAYS.between(
                reserva.getFechaIngreso(),
                reserva.getFechaSalida());

        if (noches <= 0) {
            throw new ReservaInvalidaException(
                    "La fecha de salida debe ser posterior a la fecha de ingreso.");
        }

        BigDecimal totalHabitacion = reserva.getHabitacion()
                .getPrecioNoche()
                .multiply(BigDecimal.valueOf(noches));

        BigDecimal totalServicios = BigDecimal.ZERO;

        if (reserva.getServicios() != null) {
            for (Servicio servicio : reserva.getServicios()) {

                if (servicio != null && servicio.getPrecio() != null) {
                    totalServicios = totalServicios.add(servicio.getPrecio());
                }
            }
        }

        return totalHabitacion.add(totalServicios);
    }

    @Override
    @Transactional
    public void eliminarServicio(Long reservaId, Long servicioId) {

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNoEncontradaException(reservaId));

        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA)
            throw new ReservaCanceladaException();

        boolean eliminado = reserva.getServicios()
                .removeIf(s -> s.getId().equals(servicioId));

        if (!eliminado) {
            throw new ReservaAsignarServicioException(
                    "El servicio no está asociado a la reserva.");
        }

        reserva.setTotalCalculado(calcularTotal(reserva));
        reservaRepository.save(reserva);

        reservaMapper.aRespuesta(reserva);
    }

    @Override
    @Transactional
    public void pagarReserva(Long reservaId) {

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNoEncontradaException(reservaId));

        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new ReservaCanceladaException();
        }

        if (reserva.getEstadoReserva() == EstadoReserva.CONFIRMADA || reserva
                .getEstadoReserva() == EstadoReserva.COMPLETADA) {
            throw new ReservaPagoException("La reserva ya fue pagada.");
        }

        if (reservaRepository.existsByHabitacionIdAndFechaIngresoLessThanAndFechaSalidaGreaterThanAndEstadoReserva(
                reserva.getHabitacion().getId(),
                reserva.getFechaSalida(),
                reserva.getFechaIngreso(),
                EstadoReserva.CONFIRMADA)) {

            throw new HabitacionOcupadaException(
                    "No se puede realizar pago ya hay reserva en estado confirmado");
        }
        reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
        reservaMapper.aRespuesta(reservaRepository.save(reserva));
    }

    @Override
    public List<ReservaResponseHuespedDto> reservasPorHuesped(Long huespedId, EstadoReserva estadoReserva) {

        return reservaRepository
                .findAllByEstadoReservaAndHuespedId(estadoReserva,
                        huespedId)
                .stream()
                .map(reservaMapper::aHuespedRespuesta)
                .toList();
    }

    @Override
    public List<ReservaResponseHuespedDto> todasReservasPorHuesped(Long huespedId) {
        return reservaRepository.todasReservasPorHuesped(huespedId)
                .stream()
                .map(reservaMapper::aHuespedRespuesta)
                .toList();
    }

    private void validarFecha(LocalDate fechaIngreso, LocalDate fechaSalida) {
        long noches = ChronoUnit.DAYS.between(
                fechaIngreso,
                fechaSalida);

        if (noches <= 0)
            throw new ReservaInvalidaException(
                    "La fecha de salida debe ser posterior a la fecha de ingreso.");
    }

    @Override
    public ReservaResponseDto findById(Long id) {
        return reservaRepository.findById(id).map(reservaMapper::aRespuesta)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));
    }

}
