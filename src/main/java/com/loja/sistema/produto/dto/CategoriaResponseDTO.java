package com.loja.sistema.produto.dto;

import java.time.LocalDateTime;

public record CategoriaResponseDTO(
        Long id,
        String nome,
        String descricao,
        Boolean ativo,
        LocalDateTime dataCriacao
) {
}
