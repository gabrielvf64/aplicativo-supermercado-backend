package com.gabrielferreira.aplicativo.repositories;

import com.gabrielferreira.aplicativo.dominio.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT cidade FROM Cidade cidade WHERE cidade.estado.id = :estadoId ORDER BY cidade.nome")
    public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);
}
