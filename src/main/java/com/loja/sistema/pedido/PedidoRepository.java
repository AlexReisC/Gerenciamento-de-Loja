package com.loja.sistema.pedido;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @EntityGraph(attributePaths = {"itens", "cliente"})
    Optional<Pedido> findByCliente(Long id);

    @Query("SELECT p.status FROM Pedido p JOIN FETCH p.itens i WHERE i.produto.id = :produtoId")
    Optional<String> findStatusByProdutoId(@Param("produtoId") Long produtoId);
}
