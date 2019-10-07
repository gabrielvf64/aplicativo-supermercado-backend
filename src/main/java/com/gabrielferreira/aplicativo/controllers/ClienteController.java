package com.gabrielferreira.aplicativo.controllers;

import com.gabrielferreira.aplicativo.dominio.Cliente;
import com.gabrielferreira.aplicativo.dto.ClienteDTO;
import com.gabrielferreira.aplicativo.dto.ClienteNewDTO;
import com.gabrielferreira.aplicativo.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> obter(@PathVariable Integer id) {
        Cliente cliente = clienteService.obter(id);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public ResponseEntity<Cliente> obter(@RequestParam(value = "value") String email) {
        Cliente cliente = clienteService.obterPorEmail(email);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> criar(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
        Cliente cliente = clienteService.converteDTO(clienteNewDTO);
        cliente = clienteService.criar(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO clienteDTO,
                                          @PathVariable Integer id) {
        Cliente cliente = clienteService.converteDTO(clienteDTO);
        cliente.setId(id);
        cliente = clienteService.atualizar(cliente);
        return ResponseEntity.noContent().build();

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> obter() {
        List<Cliente> lista = clienteService.obter();
        List<ClienteDTO> clientes = lista.stream().map(cliente -> new ClienteDTO(cliente))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(clientes);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> obterPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
        Page<Cliente> list = clienteService.obterPagina(page, linesPerPage, direction, orderBy);
        Page<ClienteDTO> clientes = list.map(cliente -> new ClienteDTO(cliente));
        return ResponseEntity.ok().body(clientes);
    }

    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public ResponseEntity<Void> uploadFotoPerfil(@RequestParam(name = "arquivo") MultipartFile multipartFile) {
        URI uri = clienteService.uploadFotoPerfil(multipartFile);
        return ResponseEntity.created(uri).build();
    }
}
