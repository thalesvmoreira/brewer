package com.thales.brewer.service.exception;

public class SenhaObrigatoriaUsuarioException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public SenhaObrigatoriaUsuarioException(String message) {
        super(message);
    }
}
