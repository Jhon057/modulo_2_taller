package com.hotel.repository;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findById(@NonNull Long id);

    List<Hotel> findByCiudad(String ciudad);

    List<Hotel> findByNombreContainingIgnoreCase(String nombre);

    @Query("select h from Hotel h join h.habitaciones ha where ha.disponible = true group by h.id having count (ha) >= :cantidad")
    List<Hotel> habitacionDisponible(@Param("cantidad") Long cantidad);

    boolean existsByCiudadIgnoreCaseAndNombreIgnoreCase(String ciudad, String nombre);

    boolean existsByHabitacionesEmpty(Long hotelId);

    boolean existsByIdAndHabitacionesDisponible(Long id, Boolean disponible);
}
