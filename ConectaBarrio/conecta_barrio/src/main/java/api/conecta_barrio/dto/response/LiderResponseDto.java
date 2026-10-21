package api.conecta_barrio.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiderResponseDto {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
}