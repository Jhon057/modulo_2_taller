package com.hotel.dto.error.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsResponse(
        int codigo,
        List<String> mensajes,
        LocalDateTime fecha) {

}
