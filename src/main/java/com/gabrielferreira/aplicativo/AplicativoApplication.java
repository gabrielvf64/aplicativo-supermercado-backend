package com.gabrielferreira.aplicativo;

import com.gabrielferreira.aplicativo.dominio.*;
import com.gabrielferreira.aplicativo.dominio.enums.TipoCliente;
import com.gabrielferreira.aplicativo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
    }
}
