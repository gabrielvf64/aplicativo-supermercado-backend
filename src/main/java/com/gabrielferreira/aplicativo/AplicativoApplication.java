package com.gabrielferreira.aplicativo;

import com.gabrielferreira.aplicativo.dominio.*;
import com.gabrielferreira.aplicativo.dominio.enums.EstadoPagamento;
import com.gabrielferreira.aplicativo.dominio.enums.TipoCliente;
import com.gabrielferreira.aplicativo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class AplicativoApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(AplicativoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Categoria categoria1 = new Categoria(null, "Bebidas");
        Categoria categoria2 = new Categoria(null, "Natural");

        Produto produto1 = new Produto(null, "Coca-cola", 5.00);
        Produto produto2 = new Produto(null, "Guaraná", 4.50);
        Produto produto3 = new Produto(null, "Suco", 8.00);

        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Arrays.asList(produto3));

        produto1.getCategorias().addAll(Arrays.asList(categoria1));
        produto2.getCategorias().addAll(Arrays.asList(categoria1));
        produto3.getCategorias().addAll(Arrays.asList(categoria1, categoria2));

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));

        Estado estado1 = new Estado(null, "Rio de Janeiro");
        Estado estado2 = new Estado(null, "São Paulo");

        Cidade cidade1 = new Cidade(null, "Niterói", estado1);
        Cidade cidade2 = new Cidade(null, "Maricá", estado1);
        Cidade cidade3 = new Cidade(null, "Campinas", estado2);

        estado1.getCidades().addAll(Arrays.asList(cidade1, cidade2));
        estado2.getCidades().addAll(Arrays.asList(cidade3));

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente(null, "Gabriel", "gabriel@email.com", "83272792031",
                TipoCliente.PESSOAFISICA);

        cliente1.getTelefones().addAll(Arrays.asList("26115873", "999887766"));

        Endereco endereco1 = new Endereco(null, "Rua Gastão Gonçalves", "79",
                "Bloco A", "Santa Rosa", "24240030", cliente1, cidade1);

        Endereco endereco2 = new Endereco(null, "Rua Doze", "10", "Principal",
                "Itaipuaçu", "24900000", cliente1, cidade2);

        cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));

        clienteRepository.saveAll(Arrays.asList(cliente1));
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

        Pedido pedido1 = new Pedido(null, simpleDateFormat.parse("30/09/2018 13:32"), cliente1, endereco1);
        Pedido pedido2 = new Pedido(null, simpleDateFormat.parse("12/04/2019 20:34"), cliente1, endereco2);

        Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 3);
        pedido1.setPagamento(pagamento1);

        Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2,
                simpleDateFormat.parse("10/02/2019 00:00"), null);
        pedido2.setPagamento(pagamento2);

        cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

        ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, 5.00);
        ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, 2.00);
        ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 2.00, 1, 15.00);

        pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
        pedido2.getItens().addAll(Arrays.asList(itemPedido3));

        produto1.getItens().addAll(Arrays.asList(itemPedido1));
        produto2.getItens().addAll(Arrays.asList(itemPedido3));
        produto3.getItens().addAll(Arrays.asList(itemPedido2));

        itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
    }
}
