package com.gabrielferreira.aplicativo.repositories;

import com.gabrielferreira.aplicativo.dominio.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
