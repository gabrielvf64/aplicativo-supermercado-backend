package com.gabrielferreira.aplicativo.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date dataPedido;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;

    private Cliente cliente;

    private Endereco enderecoEntrega;

    public Pedido() {
    }

    public Pedido(Integer id, Date dataPedido, Pagamento pagamento, Cliente cliente, Endereco enderecoEntrega) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}