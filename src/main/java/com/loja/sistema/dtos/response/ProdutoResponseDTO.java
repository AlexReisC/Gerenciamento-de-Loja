package com.loja.sistema.dtos.response;

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
        CategoriaResponseDTO categoria
) {
}
