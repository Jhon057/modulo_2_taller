package com.hotel.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.enums.TipoHabitacion;
import com.hotel.model.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    Optional<Habitacion> findById(Long id);

    @Query("""
            SELECT h
            FROM Habitacion h
            WHERE h.hotel.id = :hotelId
            AND h.disponible = true
            """)
    List<Habitacion> listarDisponiblesPorHotel(@Param("hotelId") Long hotelId);

    List<Habitacion> findByTipoHabitacion(TipoHabitacion tipoHabitacion);

    @Query("select ha from Habitacion ha join fetch ha.hotel where ha.precioNoche <= :precio")
    List<Habitacion> filtroPrecio(@Param("precio") BigDecimal precio);

    boolean existsByNumeroIgnoreCaseAndHotelId(String numero, Long hotelId);

    boolean existsByHotelId(Long hotelId);

    boolean existsByTipoHabitacion(TipoHabitacion tipoHabitacion);

}
