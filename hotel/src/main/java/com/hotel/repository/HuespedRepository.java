package com.hotel.repository;

import com.hotel.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    boolean existsByIgnoreCaseDocumentoIdentidad(String documentoIdentidad);

    boolean existsByIgnoreCaseEmail(String email);

    List<Huesped> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre,
            String apellido);

    @Query("""
                SELECT h
                FROM Huesped h
                LEFT JOIN FETCH h.reservas
                LEFT JOIN FETCH h.perfilContacto
                WHERE h.id = :id
            """)
    Optional<Huesped> findByIdConDatos(@Param("id") Long id);

}

