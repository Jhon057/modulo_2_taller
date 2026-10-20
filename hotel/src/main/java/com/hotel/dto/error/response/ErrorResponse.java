package com.hotel.dto.error.response;

import java.time.LocalDateTime;

public record ErrorResponse(
                int codigo,
                String mensaje,
                LocalDateTime fecha) {

}
