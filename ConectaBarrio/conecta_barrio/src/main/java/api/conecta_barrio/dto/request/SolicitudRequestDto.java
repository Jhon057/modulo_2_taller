package api.conecta_barrio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudRequestDto {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatorio")
    private String descripcion;

    @NotNull(message = "Debe ingresar el id del líder")
    private Long idLider;

}