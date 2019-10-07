package com.gabrielferreira.aplicativo.controllers;

import com.gabrielferreira.aplicativo.dominio.Cidade;
import com.gabrielferreira.aplicativo.dominio.Estado;
import com.gabrielferreira.aplicativo.dto.CidadeDTO;
import com.gabrielferreira.aplicativo.dto.EstadoDTO;
import com.gabrielferreira.aplicativo.services.CidadeService;
import com.gabrielferreira.aplicativo.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> obter() {
        List<Estado> lista = estadoService.obterOrdenadoPorNome();
        List<EstadoDTO> listaDTO = lista.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDTO);
    }

    @RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> obterCidades(@PathVariable Integer estadoId) {
        List<Cidade> lista = cidadeService.obterPorEstado(estadoId);
        List<CidadeDTO> listaDTO = lista.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDTO);
    }
}
