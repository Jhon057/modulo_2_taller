package com.hotel.exception.global;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hotel.dto.error.response.ErrorResponse;
import com.hotel.dto.error.response.ErrorsResponse;
import com.hotel.exception.HabitacionDisponibleException;
import com.hotel.exception.HabitacionNoEncontradoException;
import com.hotel.exception.HabitacionOcupadaException;
import com.hotel.exception.HabitacionRegistroException;
import com.hotel.exception.HabitacionRegistroTipoHException;
import com.hotel.exception.HotelException;
import com.hotel.exception.HotelNoEncontradoException;
import com.hotel.exception.HotelRegistroException;
import com.hotel.exception.HuespedNoEncontradoException;
import com.hotel.exception.HuespedRegistroCorreoException;
import com.hotel.exception.HuespedRegistroDocumentoException;
import com.hotel.exception.ReservaActivaException;
import com.hotel.exception.ReservaAsignarServicioException;
import com.hotel.exception.ReservaCambiarEstadoException;
import com.hotel.exception.ReservaCanceladaException;
import com.hotel.exception.ReservaInvalidaException;
import com.hotel.exception.ReservaNoEncontradaException;
import com.hotel.exception.ReservaPagoException;
import com.hotel.exception.ServicioNoEncontradoException;
import com.hotel.exception.ServicioNombreException;
import com.hotel.exception.errors.ValidacionException;
import com.hotel.exception.user.NombreUsuarioNoEncontradoException;
import com.hotel.exception.user.NombreUsuarioRegistroException;

@RestControllerAdvice
public class HandlerGlobalExceptions {

        @ExceptionHandler(ReservaNoEncontradaException.class)
        public ResponseEntity<ErrorResponse> noEncontrado(ReservaNoEncontradaException ex) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(ReservaAsignarServicioException.class)
        public ResponseEntity<ErrorResponse> noExisteServicio(ReservaAsignarServicioException ex) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(NombreUsuarioNoEncontradoException.class)
        public ResponseEntity<ErrorResponse> usuarioNoExiste(NombreUsuarioNoEncontradoException ex) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(ReservaCambiarEstadoException.class)
        public ResponseEntity<ErrorResponse> cambiarEstado(ReservaCambiarEstadoException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(ReservaPagoException.class)
        public ResponseEntity<ErrorResponse> reservaPago(ReservaPagoException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(ReservaCanceladaException.class)
        public ResponseEntity<ErrorResponse> reservaCancelada(ReservaCanceladaException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(NombreUsuarioRegistroException.class)
        public ResponseEntity<ErrorResponse> usuarioRegistrado(NombreUsuarioRegistroException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(ReservaInvalidaException.class)
        public ResponseEntity<ErrorResponse> reservaInvalida(ReservaInvalidaException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(ServicioNoEncontradoException.class)
        public ResponseEntity<ErrorResponse> noEncontrado(ServicioNoEncontradoException ex) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(ServicioNombreException.class)
        public ResponseEntity<ErrorResponse> noEncontrado(ServicioNombreException ex) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(HabitacionDisponibleException.class)
        public ResponseEntity<ErrorResponse> habitacionNoDisponible(HabitacionDisponibleException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(HotelException.class)
        public ResponseEntity<ErrorResponse> errorIngresoDatos(HotelException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(HotelRegistroException.class)
        public ResponseEntity<ErrorResponse> errorIngresoDatos(HotelRegistroException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(HuespedRegistroDocumentoException.class)
        public ResponseEntity<ErrorResponse> errorIngresoDatos(HuespedRegistroDocumentoException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(HuespedRegistroCorreoException.class)
        public ResponseEntity<ErrorResponse> errorIngresoDatos(HuespedRegistroCorreoException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(ReservaActivaException.class)
        public ResponseEntity<ErrorResponse> reservaActiva(ReservaActivaException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(HabitacionRegistroException.class)
        public ResponseEntity<ErrorResponse> registroNoDisponible(HabitacionRegistroException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(HabitacionRegistroTipoHException.class)
        public ResponseEntity<ErrorResponse> registroTipoHabitacion(HabitacionRegistroTipoHException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(HabitacionOcupadaException.class)
        public ResponseEntity<ErrorResponse> habitacionOcupada(HabitacionOcupadaException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(HabitacionNoEncontradoException.class)
        public ResponseEntity<ErrorResponse> habitacionNoEncontrado(HabitacionNoEncontradoException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(HotelNoEncontradoException.class)
        public ResponseEntity<ErrorResponse> hotelNoEncontrado(HotelNoEncontradoException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(HuespedNoEncontradoException.class)
        public ResponseEntity<ErrorResponse> huespedNoEncontrado(HuespedNoEncontradoException ex) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponse> accesoDenegado(AccessDeniedException ex) {
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.FORBIDDEN.value(),
                                "No tienes permiso para realizar esta acción",
                                LocalDateTime.now(ZoneId.systemDefault()));
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }

        // 400: fallo una validación de @Valid
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex) {
                String mensaje = ex.getBindingResult().getFieldErrors().stream()
                                .findFirst()
                                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                                .orElse("Error de validación");
                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                mensaje,
                                LocalDateTime.now(ZoneId.systemDefault()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(Exception.class)
        public ProblemDetail handleGeneral(Exception ex) {
                ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                                "Error interno del servidor");
                problemDetail.setTitle("Error interno");
                return problemDetail;
        }

        @ExceptionHandler(ValidacionException.class)
        public ResponseEntity<ErrorsResponse> manejarValidacion(
                        ValidacionException ex) {

                ErrorsResponse error = new ErrorsResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getErrores(),
                                LocalDateTime.now(ZoneId.systemDefault()));

                return ResponseEntity.badRequest().body(error);
        }
}
