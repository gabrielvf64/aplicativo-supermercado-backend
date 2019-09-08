package com.gabrielferreira.aplicativo.controllers.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer status;
    private String mensagem;
    private Long timeStamp;

    public StandardError() {
    }

    public StandardError(Integer status, String mensagem, Long timeStamp) {
        super();
        this.status = status;
        this.mensagem = mensagem;
        this.timeStamp = timeStamp;
    }

}
