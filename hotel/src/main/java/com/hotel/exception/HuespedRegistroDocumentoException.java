package com.hotel.exception;

public class HuespedRegistroDocumentoException extends RuntimeException {
    public HuespedRegistroDocumentoException(String numeroIdentidad) {
        super("El huesped con numero de identificación : " + numeroIdentidad + " ya se encuentra registrado");
    }
}
