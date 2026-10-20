package com.hotel.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.dto.request.HabitacionRequestDto;
import com.hotel.dto.response.HabitacionResponseDto;
import com.hotel.service.HabitacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/habitacion")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    @PostMapping("/asignar-hab-a/{idHotel}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> crear(@PathVariable Long idHotel, @Valid @RequestBody HabitacionRequestDto request) {
        habitacionService.crear(request, idHotel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Se ha registrado correctamente la habitación: " + request.getNumero() + " al hotel con id "
                        + idHotel);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<HabitacionResponseDto>> listarTodos() {
        return ResponseEntity.status(HttpStatus.FOUND).body(habitacionService.listarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<HabitacionResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(habitacionService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        habitacionService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("El registro de habitación con id:" + id + "se ha eliminado ");
    }

    @GetMapping("/listar-hab-idHotel/{idHotel}")
    public ResponseEntity<List<HabitacionResponseDto>> listarDisponiblesPorHotel(@PathVariable Long idHotel) {
        return ResponseEntity.status(HttpStatus.FOUND).body(habitacionService.listarDisponiblesPorHotel(idHotel));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HabitacionResponseDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody HabitacionRequestDto request) {
        HabitacionResponseDto habitacion = habitacionService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(habitacion);
    }

    @GetMapping("/buscar-precio/{precio}")
    public ResponseEntity<List<HabitacionResponseDto>> buscarPorPrecioMaximo(@PathVariable BigDecimal precio) {
        return ResponseEntity.status(HttpStatus.FOUND).body(habitacionService.buscarPorPrecioMaximo(precio));
    }

    @PatchMapping("/cambiar-estado-habitacion/{id}")
    public ResponseEntity<String> cambiarEstadoHabitacion(@PathVariable Long id) {
        habitacionService.cambiarEstadoHabitacion(id);
        return ResponseEntity.ok("se ha cambiado el estado de la habitación: ");
    }
}