package com.loja.sistema.produto.dto.response;

import com.loja.sistema.categoria.dto.response.CategoriaResumoDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque,
        Boolean ativo,
        LocalDateTime dataCriacao,
        CategoriaResumoDTO categoria
) {
}
