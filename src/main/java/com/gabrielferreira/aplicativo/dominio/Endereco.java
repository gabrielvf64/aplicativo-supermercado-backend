package com.gabrielferreira.aplicativo.dominio;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;

    private Cliente cliente;

    private Cidade cidade;

    public Endereco() {
    }

    public Endereco(Integer id, String logradouro, String numero, String complemento, String bairro, String cep,
                    Cliente cliente, Cidade cidade) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cliente = cliente;
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
