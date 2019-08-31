package com.gabrielferreira.aplicativo.repositories;

import com.gabrielferreira.aplicativo.dominio.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
