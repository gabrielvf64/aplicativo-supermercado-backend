package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Cliente;
import com.gabrielferreira.aplicativo.repositories.ClienteRepository;
import com.gabrielferreira.aplicativo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente obter(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: " + id +
                ", Tipo: " + Cliente.class.getName()));
    }
}
