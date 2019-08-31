package com.gabrielferreira.aplicativo.dominio;

import com.gabrielferreira.aplicativo.dominio.enums.TipoCliente;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String email;
    private String cpfOuCnpj;
    private Integer tipoCliente;

    private List<Endereco> enderecos = new ArrayList<>();

    private Set<String> telefones = new HashSet<>();

    public Cliente() {
    }

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipoCliente) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipoCliente = tipoCliente.getCodigo();
    }

    public TipoCliente getTipo() {
        return TipoCliente.converterParaEnum(tipoCliente);
    }

    public void setTipo(TipoCliente tipo) {
        this.tipoCliente = tipo.getCodigo();
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
