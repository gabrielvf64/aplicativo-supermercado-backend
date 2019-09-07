package com.gabrielferreira.aplicativo.dto;

import com.gabrielferreira.aplicativo.dominio.Categoria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaResultado implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public CategoriaResultado(Categoria categoria) {
        id = categoria.getId();
        nome = categoria.getNome();
    }
}
