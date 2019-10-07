package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Cidade;
import com.gabrielferreira.aplicativo.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> obterPorEstado(Integer estadoId) {
        return cidadeRepository.findCidades(estadoId);
    }
}
