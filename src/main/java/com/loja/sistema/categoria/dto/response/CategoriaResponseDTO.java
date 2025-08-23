package com.loja.sistema.categoria.dto.response;

import java.time.LocalDateTime;

public record CategoriaResponseDTO(
        Long id,
        String nome,
        String descricao,
        Boolean ativo,
        LocalDateTime dataCriacao
) {
}
