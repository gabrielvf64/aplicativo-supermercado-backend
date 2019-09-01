package com.gabrielferreira.aplicativo.repositories;

import com.gabrielferreira.aplicativo.dominio.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
