package com.hotel.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.enums.EstadoReserva;
import com.hotel.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

        @Query("""
                        SELECT r
                        FROM Reserva r
                        JOIN FETCH r.huesped
                        WHERE r.estadoReserva = :estadoReserva
                        AND r.huesped.id = :huespedId
                        """)
        List<Reserva> findAllByEstadoReservaAndHuespedId(
                        EstadoReserva estadoReserva,
                        Long huespedId);

        List<Reserva> findByEstadoReservaAndFechaSalidaLessThanEqual(
                        EstadoReserva estado,
                        LocalDate fecha);

        boolean existsByHabitacionIdAndFechaIngresoLessThanAndFechaSalidaGreaterThanAndEstadoReserva(
                        Long habitacionId,
                        LocalDate fechaSalida,
                        LocalDate fechaIngreso, EstadoReserva estadoReserva);

        Optional<Reserva> findFirstByHabitacionId(Long habitacionId);

        boolean existsByHuespedIdAndEstadoReservaIn(
                        Long huespedId,
                        List<EstadoReserva> estados);

        @Query("""
                        SELECT r
                        FROM Reserva r
                        JOIN FETCH r.huesped
                        WHERE r.huesped.id = :huespedId
                          AND r.estadoReserva = :estadoReserva
                        """)
        List<Reserva> reservasPorHuesped(
                        @Param("huespedId") Long huespedId,
                        @Param("estadoReserva") EstadoReserva estadoReserva);

        @Query("""
                        SELECT r
                        FROM Reserva r
                        JOIN FETCH r.huesped
                        WHERE r.huesped.id = :huespedId
                        """)
        List<Reserva> todasReservasPorHuesped(
                        @Param("huespedId") Long huespedId);

        boolean existsByHabitacionIdAndEstadoReservaNot(
                        Long habitacionId,
                        EstadoReserva estadoReserva);

}
