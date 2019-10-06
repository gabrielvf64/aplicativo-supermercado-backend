package com.gabrielferreira.aplicativo.services.exceptions;

public class FileException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileException(String mensagem) {
        super(mensagem);
    }

    public FileException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
