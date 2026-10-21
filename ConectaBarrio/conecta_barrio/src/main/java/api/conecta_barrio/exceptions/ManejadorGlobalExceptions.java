package api.conecta_barrio.exceptions;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import api.conecta_barrio.dto.response.error.ErrorResponse;

@RestControllerAdvice
public class ManejadorGlobalExceptions {

    @ExceptionHandler(LiderRegistroException.class)
    public ResponseEntity<ErrorResponse> validarExcepcion(LiderRegistroException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now(ZoneId.systemDefault()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RegistroNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> validarExcepcion(RegistroNoEncontradoException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now(ZoneId.systemDefault()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(SolicitudCambiarEstadoException.class)
    public ResponseEntity<ErrorResponse> validarExcepcion(SolicitudCambiarEstadoException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now(ZoneId.systemDefault()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(SolicitudRegistroException.class)
    public ResponseEntity<ErrorResponse> validarExcepcion(SolicitudRegistroException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now(ZoneId.systemDefault()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UsuarioRegistroException.class)
    public ResponseEntity<ErrorResponse> validarExcepcion(UsuarioRegistroException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now(ZoneId.systemDefault()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 403: sin permiso (USER intentando borrar)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accesoDenegado(AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "No tienes permiso para realizar esta acción",
                LocalDateTime.now(ZoneId.systemDefault()));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // 400: fallo una validacion de @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validacion(MethodArgumentNotValidException ex) {
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

}
