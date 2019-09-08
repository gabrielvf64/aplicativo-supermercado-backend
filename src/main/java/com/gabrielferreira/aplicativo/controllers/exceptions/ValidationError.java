package com.gabrielferreira.aplicativo.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    private static final long serialVersionUID = 1L;

    private List<FieldMessage> erros = new ArrayList<>();

    public ValidationError(Integer status, String mensagem, Long timeStamp) {
        super(status, mensagem, timeStamp);
    }

    public List<FieldMessage> getErros() {
        return erros;
    }

    public void adicionarErro(String fieldName, String mensagem) {
        erros.add(new FieldMessage(fieldName, mensagem));
    }
}
