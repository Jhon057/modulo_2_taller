package api.conecta_barrio.dto.response;

import api.conecta_barrio.enums.EstadoSolicitud;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SolicitudResponseDto {

    private Long id;
    private String titulo;
    private String descripcion;
    private Boolean prioridad = false;
    private EstadoSolicitud estado;
    private LocalDateTime fechaCreacion;
    private LiderResponseDto lider;
}