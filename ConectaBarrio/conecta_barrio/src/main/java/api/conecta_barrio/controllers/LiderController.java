package api.conecta_barrio.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.conecta_barrio.dto.request.LiderRequestDto;
import api.conecta_barrio.dto.response.LiderResponseDto;
import api.conecta_barrio.services.LiderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/lider")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LiderController {

    private final LiderService liderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LiderResponseDto> registrar(
            @Valid @RequestBody LiderRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(liderService.registrar(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIDER', 'USER')")
    @GetMapping
    public ResponseEntity<List<LiderResponseDto>> listar() {

        return ResponseEntity.ok(
                liderService.listar());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIDER', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<LiderResponseDto> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                liderService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<LiderResponseDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody LiderRequestDto dto) {

        return ResponseEntity.ok(
                liderService.actualizar(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        liderService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
