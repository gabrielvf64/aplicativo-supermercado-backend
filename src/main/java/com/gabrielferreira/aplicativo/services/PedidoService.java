package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Pedido;
import com.gabrielferreira.aplicativo.repositories.PedidoRepository;
import com.gabrielferreira.aplicativo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido obter(Integer id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! Id: " + id + ", Tipo: "
                + Pedido.class.getName()));
    }
}
