package com.gabrielferreira.aplicativo;

import com.gabrielferreira.aplicativo.dominio.Categoria;
import com.gabrielferreira.aplicativo.dominio.Produto;
import com.gabrielferreira.aplicativo.repositories.CategoriaRepository;
import com.gabrielferreira.aplicativo.repositories.ProdutoRepository;
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

    public static void main(String[] args) {
        SpringApplication.run(AplicativoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Criando objetos
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

        // Salvando em uma lista
        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
    }
}
