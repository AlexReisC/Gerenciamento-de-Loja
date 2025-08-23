package com.loja.sistema.categoria.dto.request;

import jakarta.validation.constraints.Size;

public record CategoriaAtualizacaoDTO(
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    String nome,

    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    String descricao,

    Boolean ativo
) {
}
