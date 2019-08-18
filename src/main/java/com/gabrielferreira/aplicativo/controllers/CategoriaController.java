package com.gabrielferreira.aplicativo.controllers;

import com.gabrielferreira.aplicativo.dominio.Categoria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> listar() {

        Categoria categoria1 = new Categoria(1, "Bebidas");
        Categoria categoria2 = new Categoria(2, "Açougue");
        Categoria categoria3 = new Categoria(3, "Padaria");
        Categoria categoria4 = new Categoria(4, "Laticínios");
        Categoria categoria5 = new Categoria(5, "Adega");
        Categoria categoria6 = new Categoria(6, "Hortifrutis");
        Categoria categoria7 = new Categoria(7, "Limpeza");

        List<Categoria> lista = new ArrayList<>();

        lista.add(categoria1);
        lista.add(categoria2);
        lista.add(categoria3);
        lista.add(categoria4);
        lista.add(categoria5);
        lista.add(categoria6);
        lista.add(categoria7);

        return lista;
    }
}
