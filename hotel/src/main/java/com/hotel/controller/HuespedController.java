package com.hotel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.dto.request.HuespedRequestDto;
import com.hotel.dto.response.HuespedResponseDatosDto;
import com.hotel.dto.response.HuespedResponseDto;
import com.hotel.service.HuespedService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/huesped")
@RequiredArgsConstructor
public class HuespedController {

    private final HuespedService huespedService;

    @PostMapping("/crear-huesped")
    public ResponseEntity<String> crear(@Valid @RequestBody HuespedRequestDto request) {
        huespedService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Se ha registrado correctamente el huesped: " + request.getNombre());
    }

    @GetMapping
    public ResponseEntity<List<HuespedResponseDto>> listarTodos() {
        return ResponseEntity.status(HttpStatus.FOUND).body(huespedService.listarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<HuespedResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(huespedService.buscarPorId(id));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HuespedResponseDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody HuespedRequestDto request) {
        HuespedResponseDto huesped = huespedService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(huesped);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        huespedService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("El registro de huesped con id:" + id + "se ha eliminado ");
    }

    @GetMapping("/buscar-huesped/{nombreApellido}")
    public ResponseEntity<List<HuespedResponseDto>> buscarPorNombreOApellido(@PathVariable String nombreApellido) {
        return ResponseEntity.status(HttpStatus.FOUND).body(huespedService.buscarPorNombreOApellido(nombreApellido));
    }

    @GetMapping("/datos-huesped-completos/{id}")
    public ResponseEntity<HuespedResponseDatosDto> obtenerDatos(
            @PathVariable Long id) {

        return ResponseEntity.ok(huespedService.huespedDatos(id));
    }
}