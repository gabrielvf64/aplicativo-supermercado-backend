package com.gabrielferreira.aplicativo.dto;

import com.gabrielferreira.aplicativo.dominio.Estado;

import java.io.Serializable;

public class EstadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public EstadoDTO() {
    }

    public EstadoDTO(Estado estado) {
        id = estado.getId();
        nome = estado.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
