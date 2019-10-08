package com.gabrielferreira.aplicativo.controllers.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long timeStamp;
    private Integer status;
    private String erro;
    private String mensagem;
    private String path;

    public StandardError() {
    }

    public StandardError(Long timeStamp, Integer status, String erro, String mensagem, String path) {
        super();
        this.timeStamp = timeStamp;
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.path = path;
    }
}
