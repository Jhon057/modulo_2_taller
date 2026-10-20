package com.hotel.controller;

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

import com.hotel.dto.request.ReservaRequestDto;
import com.hotel.dto.response.ReservaResponseDto;
import com.hotel.dto.response.ReservaResponseHuespedDto;
import com.hotel.enums.EstadoReserva;
import com.hotel.service.ReservaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping("/registrar-reserva")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> crear(@Valid @RequestBody ReservaRequestDto request) {
        reservaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Se ha registrado la reserva correctamente al huesped id: " + request.getHuespedId());
    }

    @GetMapping("/reservas-estado-activos")
    public ResponseEntity<List<ReservaResponseDto>> listarTodosActivos() {
        return ResponseEntity.status(HttpStatus.FOUND).body(reservaService.listarTodosActivos());
    }

    @GetMapping("/reservas-estado-cancelados")
    public ResponseEntity<List<ReservaResponseDto>> listarTodosCancelados() {
        return ResponseEntity.status(HttpStatus.FOUND).body(reservaService.listarTodosCancelados());
    }

    @GetMapping("/reservas-huesped/{id}/{estadoReserva}")
    public ResponseEntity<List<ReservaResponseHuespedDto>> reservasPorHuesped(
            @PathVariable Long id,
            @PathVariable EstadoReserva estadoReserva) {

        return ResponseEntity.ok(
                reservaService.reservasPorHuesped(id, estadoReserva));
    }

    @GetMapping("/reservas-huesped/{id}")
    public ResponseEntity<List<ReservaResponseHuespedDto>> todasReservasPorHuesped(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservaService.todasReservasPorHuesped(id));
    }

    @GetMapping("/buscar-reserva/{id}")
    public ResponseEntity<ReservaResponseDto> encontrarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @PutMapping("/actualizar-reserva/{id}")
    public ResponseEntity<ReservaResponseDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody ReservaRequestDto request) {
        ReservaResponseDto hotel = reservaService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(hotel);
    }

    @PatchMapping("/cancelar-reserva/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Se ha cambiado el estado de la reserva a cancelado");
    }

    @PatchMapping("/agregar-servicio-a/reserva-id/{id}")
    public ResponseEntity<String> agregarServicio(@PathVariable Long id, @RequestBody List<Long> servicioId) {
        reservaService.agregarServicio(id, servicioId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Se han agregado los servicios a la reserva");
    }

    @DeleteMapping("/eliminar-servicio/{reservaId}/{servicioId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminarServicio(
            @PathVariable Long reservaId,
            @PathVariable Long servicioId) {
        reservaService.eliminarServicio(reservaId, servicioId);
        return ResponseEntity.ok("Se ha eliminado el servicio:" + servicioId + " de la reserva:  " + reservaId);
    }

    @PatchMapping("/pago_reserva-id/{id}")
    public ResponseEntity<String> pagarReserva(@PathVariable Long id) {
        reservaService.pagarReserva(id);
        return ResponseEntity.ok("pago completado");
    }
}