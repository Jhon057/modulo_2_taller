package api.conecta_barrio.dto.response.error;

import java.time.LocalDateTime;

public record ErrorResponse(
        int codigo,
        String mensaje,
        LocalDateTime fecha) {

}
