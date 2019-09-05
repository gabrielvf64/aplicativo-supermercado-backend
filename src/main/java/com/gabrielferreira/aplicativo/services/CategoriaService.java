package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Categoria;
import com.gabrielferreira.aplicativo.repositories.CategoriaRepository;
import com.gabrielferreira.aplicativo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria obter(Integer id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException(
                "O objeto não foi encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()
        ));
    }

    public Categoria criar(Categoria categoria) {
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Categoria categoria) {
        obter(categoria.getId());
        return categoriaRepository.save(categoria);
    }
}
