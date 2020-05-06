package com.thales.brewer.service.exception;

public class EmailUsuarioJaCadastradoException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public EmailUsuarioJaCadastradoException(String message) {
        super(message);
    }
}
