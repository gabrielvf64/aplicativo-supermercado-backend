package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.ItemPedido;
import com.gabrielferreira.aplicativo.dominio.PagamentoComBoleto;
import com.gabrielferreira.aplicativo.dominio.Pedido;
import com.gabrielferreira.aplicativo.dominio.enums.EstadoPagamento;
import com.gabrielferreira.aplicativo.repositories.ItemPedidoRepository;
import com.gabrielferreira.aplicativo.repositories.PagamentoRepository;
import com.gabrielferreira.aplicativo.repositories.PedidoRepository;
import com.gabrielferreira.aplicativo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    public Pedido obter(Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! Id: " + id + ", Tipo: "
                + Pedido.class.getName()));
    }

    public Pedido criar(Pedido pedido) {
        pedido.setId(null);
        pedido.setDataPedido(new Date());
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getDataPedido());
        }

        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for (ItemPedido ip : pedido.getItens()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.obter(ip.getProduto().getId()).getPreco());
            ip.setPedido(pedido);
        }

        itemPedidoRepository.saveAll(pedido.getItens());
        return pedido;
    }
}
