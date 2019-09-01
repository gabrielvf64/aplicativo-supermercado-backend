package com.gabrielferreira.aplicativo.dominio;

import com.gabrielferreira.aplicativo.dominio.enums.EstadoPagamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;

    private Integer numeroParcelas;

    public PagamentoComCartao() {
    }

    public PagamentoComCartao(Integer id, EstadoPagamento estadoPagamento, Pedido pedido, Integer numeroParcelas) {
        super(id, estadoPagamento, pedido);
        this.numeroParcelas = numeroParcelas;
    }
}
