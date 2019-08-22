package com.gabrielferreira.aplicativo.repositories;

import com.gabrielferreira.aplicativo.dominio.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
