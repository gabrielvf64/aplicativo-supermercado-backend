package com.gabrielferreira.aplicativo.controllers;

import com.gabrielferreira.aplicativo.dominio.Categoria;
import com.gabrielferreira.aplicativo.dto.CategoriaDTO;
import com.gabrielferreira.aplicativo.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
    public ResponseEntity<Void> criar(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaService.converteDTO(categoriaDTO);
        categoria = categoriaService.criar(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO categoriaDTO,
                                          @PathVariable Integer id) {
        Categoria categoria = categoriaService.converteDTO(categoriaDTO);
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
    public ResponseEntity<List<CategoriaDTO>> obter() {
        List<Categoria> lista = categoriaService.obter();
        List<CategoriaDTO> categorias = lista.stream().map(categoria -> new CategoriaDTO(categoria))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(categorias);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDTO>> obterPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
        Page<Categoria> list = categoriaService.obterPagina(page, linesPerPage, direction, orderBy);
        Page<CategoriaDTO> categorias = list.map(categoria -> new CategoriaDTO(categoria));
        return ResponseEntity.ok().body(categorias);
    }
}
