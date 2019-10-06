package com.gabrielferreira.aplicativo.controllers;

import com.gabrielferreira.aplicativo.dominio.Pedido;
import com.gabrielferreira.aplicativo.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> obter(@PathVariable Integer id) {
        Pedido pedido = pedidoService.obter(id);
        return ResponseEntity.ok().body(pedido);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> criar(@Valid @RequestBody Pedido pedido) {
        pedido = pedidoService.criar(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Pedido>> obterPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "dataPedido") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        Page<Pedido> lista = pedidoService.obterPagina(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(lista);
    }
}
