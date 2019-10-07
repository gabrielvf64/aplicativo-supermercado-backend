package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Estado;
import com.gabrielferreira.aplicativo.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> obterOrdenadoPorNome() {
        return estadoRepository.findAllByOrderByNome();
    }
}
