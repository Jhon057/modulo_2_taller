package com.hotel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.dto.request.ServicioRequestDto;
import com.hotel.dto.response.ServicioResponseDto;
import com.hotel.service.ServicioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @PostMapping("/crear-servicio")
    public ResponseEntity<String> crear(@Valid @RequestBody ServicioRequestDto request) {
        servicioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Se ha registrado correctamente el servicio: " + request.getNombre());
    }

    @GetMapping
    public ResponseEntity<List<ServicioResponseDto>> listarTodos() {
        return ResponseEntity.status(HttpStatus.FOUND).body(servicioService.listarTodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ServicioResponseDto>> listarDisponibles() {
        return ResponseEntity.status(HttpStatus.FOUND).body(servicioService.listarDisponibles());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ServicioResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(servicioService.buscarPorId(id));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ServicioResponseDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody ServicioRequestDto request) {
        ServicioResponseDto servicio = servicioService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(servicio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("El registro del servicio:" + id + "se ha eliminado ");
    }

    @PatchMapping("/cambiar-estado/{id}")
    public ResponseEntity<ServicioResponseDto> cambiarEstado(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.cambiarEstado(id));
    }

}