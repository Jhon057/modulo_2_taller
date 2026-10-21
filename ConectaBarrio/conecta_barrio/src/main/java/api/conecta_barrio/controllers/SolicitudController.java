package api.conecta_barrio.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.conecta_barrio.dto.request.SolicitudRequestDto;
import api.conecta_barrio.dto.response.SolicitudResponseDto;
import api.conecta_barrio.enums.EstadoSolicitud;
import api.conecta_barrio.services.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/solicitud")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SolicitudController {

        private final SolicitudService solicitudService;

        @PreAuthorize("hasAnyRole('ADMIN', 'LIDER', 'USER')")
        @PostMapping
        public ResponseEntity<SolicitudResponseDto> registrar(
                        @Valid @RequestBody SolicitudRequestDto dto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(solicitudService.registrar(dto));
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'LIDER', 'USER')")
        @GetMapping
        public ResponseEntity<List<SolicitudResponseDto>> listar() {

                return ResponseEntity.ok(
                                solicitudService.listar());
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'LIDER', 'USER')")
        @GetMapping("/{id}")
        public ResponseEntity<SolicitudResponseDto> buscarPorId(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                solicitudService.buscarPorId(id));
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'LIDER', 'USER')")
        @PutMapping("/{id}")
        public ResponseEntity<SolicitudResponseDto> actualizar(
                        @PathVariable Long id,
                        @Valid @RequestBody SolicitudRequestDto dto) {

                return ResponseEntity.ok(
                                solicitudService.actualizar(id, dto));
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'LIDER', 'USER')")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(
                        @PathVariable Long id) {

                solicitudService.eliminar(id);

                return ResponseEntity.noContent().build();
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'LIDER')")
        @PatchMapping("/cambiarEstado/{id}")
        public ResponseEntity<String> cambiarEstado(
                        @PathVariable Long id,
                        @RequestParam EstadoSolicitud estado) {

                solicitudService.cambiarEstado(id, estado);

                return ResponseEntity.ok(
                                "Se ha cambiado de estado a: " + estado);
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'LIDER')")
        @PatchMapping("/cambiarPrioridad/{id}")
        public ResponseEntity<String> cambiarPrioridad(
                        @PathVariable Long id) {

                solicitudService.cambiarPrioridad(id);

                return ResponseEntity.ok(
                                "La prioridad de la solicitud ha sido actualizada.");
        }
}
