package com.loja.sistema.produto;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.annotation.Nullable;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {
    boolean existsByNomeIgnoreCase(String nome);
    
    @EntityGraph(attributePaths = "categoria")
    Page<Produto> findByAtivoFalse(Pageable pageable);

    @EntityGraph(attributePaths = "categoria")
    Page<Produto> findByAtivoTrue(@Nullable Specification<Produto> spec, Pageable pageable);
    
    @EntityGraph(attributePaths = "categoria")
    Page<Produto> findByCategoriaId(@Param("categoriaId") Long categoriaId, Pageable pageable);

    @Query("SELECT p FROM Produto p JOIN FETCH p.categoria c WHERE LOWER(p.nome) = LOWER(:nome) AND p.id != :id")
    Optional<Produto> findByNomeEqualsAndIdNot(@Param("nome") String nome, @Param("id") Long id);

}
