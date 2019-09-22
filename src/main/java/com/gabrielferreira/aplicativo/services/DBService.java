package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.*;
import com.gabrielferreira.aplicativo.dominio.enums.EstadoPagamento;
import com.gabrielferreira.aplicativo.dominio.enums.TipoCliente;
import com.gabrielferreira.aplicativo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

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

    @Autowired
    private BCryptPasswordEncoder senhaEncodada;

    public void instanciarBancoDeTeste() throws ParseException {

        Categoria categoria1 = new Categoria(null, "Bebidas");
        Categoria categoria2 = new Categoria(null, "Natural");
        Categoria categoria3 = new Categoria(null, "Açougue");
        Categoria categoria4 = new Categoria(null, "Laticínios");
        Categoria categoria5 = new Categoria(null, "Higiene");
        Categoria categoria6 = new Categoria(null, "Peixaria");
        Categoria categoria7 = new Categoria(null, "Frutas");


        Produto produto1 = new Produto(null, "Coca-cola", 5.00);
        Produto produto2 = new Produto(null, "Guaraná", 4.50);
        Produto produto3 = new Produto(null, "Suco", 8.00);
        Produto produto4 = new Produto(null, "Alcatra", 20.00);
        Produto produto5 = new Produto(null, "Presunto", 10.00);
        Produto produto6 = new Produto(null, "Sabonete", 2.00);
        Produto produto7 = new Produto(null, "Salmão", 40.00);
        Produto produto8 = new Produto(null, "Banana", 5.00);
        Produto produto9 = new Produto(null, "Maçã", 5.00);
        Produto produto10 = new Produto(null, "Melancia", 5.00);
        Produto produto11 = new Produto(null, "Laranja", 5.00);


        categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        categoria2.getProdutos().addAll(Arrays.asList(produto3));
        categoria3.getProdutos().addAll(Arrays.asList(produto4));
        categoria4.getProdutos().addAll(Arrays.asList(produto5));
        categoria5.getProdutos().addAll(Arrays.asList(produto6));
        categoria6.getProdutos().addAll(Arrays.asList(produto7));
        categoria7.getProdutos().addAll(Arrays.asList(produto8, produto9, produto10, produto11));

        produto1.getCategorias().addAll(Arrays.asList(categoria1));
        produto2.getCategorias().addAll(Arrays.asList(categoria1));
        produto3.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
        produto4.getCategorias().addAll(Arrays.asList(categoria3));
        produto5.getCategorias().addAll(Arrays.asList(categoria4));
        produto6.getCategorias().addAll(Arrays.asList(categoria5));
        produto7.getCategorias().addAll(Arrays.asList(categoria6));
        produto8.getCategorias().addAll(Arrays.asList(categoria7));
        produto9.getCategorias().addAll(Arrays.asList(categoria7));
        produto10.getCategorias().addAll(Arrays.asList(categoria7));
        produto11.getCategorias().addAll(Arrays.asList(categoria7));

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4, categoria5,
                categoria6, categoria7));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7,
                produto8, produto9, produto10, produto11));

        Estado estado1 = new Estado(null, "Rio de Janeiro");
        Estado estado2 = new Estado(null, "São Paulo");

        Cidade cidade1 = new Cidade(null, "Niterói", estado1);
        Cidade cidade2 = new Cidade(null, "Maricá", estado1);
        Cidade cidade3 = new Cidade(null, "Campinas", estado2);

        estado1.getCidades().addAll(Arrays.asList(cidade1, cidade2));
        estado2.getCidades().addAll(Arrays.asList(cidade3));

        estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente(null, "Gabriel", "gabrielvftestes@gmail.com",
                "83272792031", TipoCliente.PESSOAFISICA, senhaEncodada.encode("123"));

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
