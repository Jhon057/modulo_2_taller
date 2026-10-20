package com.hotel.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hotel.model.Habitacion;
import com.hotel.model.Reserva;
import com.hotel.model.Servicio;
import com.hotel.repository.HabitacionRepository;
import com.hotel.repository.ReservaRepository;
import com.hotel.repository.ServicioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ReporteFinanciero {

        private final ReservaRepository reservaRepository;
        private final HabitacionRepository habitacionRepository;
        private final ServicioRepository servicioRepository;

        @SuppressWarnings("null")
        public String calcularIngresoTotal() {

                List<Reserva> reservas = reservaRepository.findAll();

                BigDecimal total = reservas.stream()
                                .map(reserva -> {
                                        long noches = ChronoUnit.DAYS.between(
                                                        reserva.getFechaIngreso(),
                                                        reserva.getFechaSalida());

                                        BigDecimal totalHabitacion = reserva.getHabitacion()
                                                        .getPrecioNoche()
                                                        .multiply(BigDecimal.valueOf(noches));

                                        BigDecimal totalServicios = reserva.getServicios().stream()
                                                        .map(Servicio::getPrecio)
                                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                        return totalHabitacion.add(totalServicios);
                                })
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                return formatearMoneda(total);
        }

        public String totalServiciosAdicionales() {

                List<Reserva> reservas = reservaRepository.findAll();
                @SuppressWarnings("null")
                BigDecimal total = reservas.stream()
                                .map(reserva -> reserva.getServicios().stream()
                                                .map(Servicio::getPrecio)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                return formatearMoneda(total);
        }

        public BigDecimal precioPromedioHab() {

                List<Habitacion> habitaciones = habitacionRepository.findAll();

                @SuppressWarnings("null")
                BigDecimal total = habitaciones.stream()
                                .map(Habitacion::getPrecioNoche)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                return habitaciones.isEmpty()
                                ? BigDecimal.ZERO
                                : total.divide(
                                                BigDecimal.valueOf(habitaciones.size()),
                                                2,
                                                RoundingMode.HALF_UP);
        }

        @SuppressWarnings("null")
        public Map<String, BigDecimal> servicioMasCostoso() {
                return servicioRepository.findAll().stream()
                                .max(Comparator.comparing(Servicio::getPrecio))
                                .map(s -> Map.of(s.getNombre(), s.getPrecio()))
                                .orElse(Map.of());
        }

        @SuppressWarnings("null")
        public Map<Long, BigDecimal> top5HabMayorIngreso() {

                @SuppressWarnings("null")
                Map<Long, BigDecimal> ingresosPorHabitacion = reservaRepository.findAll().stream()
                                .collect(Collectors.groupingBy(
                                                reserva -> reserva.getHabitacion().getId(),
                                                Collectors.reducing(
                                                                BigDecimal.ZERO,
                                                                this::calcularIngresoReserva,
                                                                BigDecimal::add)));

                return ingresosPorHabitacion.entrySet().stream()
                                .sorted(Map.Entry.<Long, BigDecimal>comparingByValue().reversed())
                                .limit(5)
                                .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (a, b) -> a,
                                                LinkedHashMap::new));
        }

        private BigDecimal calcularIngresoReserva(Reserva reserva) {

                long noches = ChronoUnit.DAYS.between(
                                reserva.getFechaIngreso(),
                                reserva.getFechaSalida());

                return reserva.getHabitacion()
                                .getPrecioNoche()
                                .multiply(BigDecimal.valueOf(noches));
        }

        private String formatearMoneda(BigDecimal valor) {

                Locale locale = Locale.of("es", "CO");
                NumberFormat formato = NumberFormat.getNumberInstance(locale);
                formato.setMinimumFractionDigits(2);
                formato.setMaximumFractionDigits(2);
                return formato.format(valor);
        }

        @SuppressWarnings("null")
        public BigDecimal calcularIngresoTotalValor() {
                return reservaRepository.findAll().stream()
                                .map(reserva -> {
                                        long noches = ChronoUnit.DAYS.between(
                                                        reserva.getFechaIngreso(),
                                                        reserva.getFechaSalida());

                                        BigDecimal totalHabitacion = reserva.getHabitacion()
                                                        .getPrecioNoche()
                                                        .multiply(BigDecimal.valueOf(noches));

                                        @SuppressWarnings("null")
                                        BigDecimal totalServicios = reserva.getServicios().stream()
                                                        .map(Servicio::getPrecio)
                                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                        return totalHabitacion.add(totalServicios);
                                })
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
}
