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

import com.hotel.dto.request.HotelRequestDto;
import com.hotel.dto.response.HotelResponseDto;
import com.hotel.dto.response.HotelResponseHabitacionDto;
import com.hotel.service.HotelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/registar-hotel")
    public ResponseEntity<String> crear(@Valid @RequestBody HotelRequestDto request) {
        hotelService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Se ha registrado correctamente el hotel: " + request.getNombre());
    }

    @GetMapping
    public ResponseEntity<List<HotelResponseDto>> listarTodos() {
        return ResponseEntity.status(HttpStatus.FOUND).body(hotelService.listarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<HotelResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(hotelService.buscarPorId(id));
    }

    @GetMapping("/buscar-por-ciudad/{ciudad}")
    public ResponseEntity<List<HotelResponseDto>> buscarPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.status(HttpStatus.FOUND).body(hotelService.buscarPorCiudad(ciudad));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<HotelResponseDto> actualizar(@PathVariable Long id,
            @Valid @RequestBody HotelRequestDto request) {
        HotelResponseDto hotel = hotelService.actualizar(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(hotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        hotelService.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("El registro de hotel con id:" + id + "se ha eliminado ");
    }

    @GetMapping("/habitaciones-disponibles/{cantidad}")
    public ResponseEntity<List<HotelResponseDto>> habitacionDisponible(@PathVariable Long cantidad) {
        return ResponseEntity.status(HttpStatus.FOUND).body(hotelService.habitacionDisponible(cantidad));
    }

    @GetMapping("/listar-hab-hotel/{idHotel}")
    public ResponseEntity<HotelResponseHabitacionDto> listarHabitaciones(
            @PathVariable Long idHotel) {

        return ResponseEntity.ok(hotelService.listarHotelHabitaciones(idHotel));
    }
}