package com.gabrielferreira.aplicativo.services.exceptions;

public class DataIntegrityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataIntegrityException(String mensagem) {
        super(mensagem);
    }

    public DataIntegrityException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
