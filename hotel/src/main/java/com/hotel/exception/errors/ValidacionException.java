package com.hotel.exception.errors;

import java.util.List;

public class ValidacionException extends RuntimeException {

    private final List<String> errores;

    public ValidacionException(List<String> errores) {
        super("Error de validación");
        this.errores = errores;
    }

    public List<String> getErrores() {
        return errores;
    }
}
