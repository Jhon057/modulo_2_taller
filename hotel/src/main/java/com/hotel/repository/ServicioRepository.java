package com.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.model.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByDisponibleTrue();

    List<Servicio> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByIgnoreCaseNombre(String nombre);
}
