package com.gabrielferreira.aplicativo.controllers;

import com.gabrielferreira.aplicativo.dominio.Categoria;
import com.gabrielferreira.aplicativo.dto.CategoriaResultado;
import com.gabrielferreira.aplicativo.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> obter(@PathVariable Integer id) {
        Categoria categoria = categoriaService.obter(id);
        return ResponseEntity.ok().body(categoria);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> criar(@RequestBody Categoria categoria) {
        categoria = categoriaService.criar(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizar(@RequestBody Categoria categoria, @PathVariable Integer id) {
        categoria.setId(id);
        categoria = categoriaService.atualizar(categoria);
        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaResultado>> obter() {
        List<Categoria> lista = categoriaService.obter();
        List<CategoriaResultado> categorias = lista.stream().map(categoria -> new CategoriaResultado(categoria))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(categorias);
    }
}
