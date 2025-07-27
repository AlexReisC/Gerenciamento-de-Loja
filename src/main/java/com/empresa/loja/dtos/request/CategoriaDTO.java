package com.empresa.loja.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CategoriaDTO(
        @NotBlank(message = "O nome da categoria é obrigatorio")
        @Size(min = 3, max = 50, message = "O nome da categoria deve ter entre 3 e 50 caracteres")
        String nome,

        @Size(max = 200, message = "A descrição da categoria deve ter entre 0 e 200 caracteres")
        String descricao,

        @NotNull(message = "Deve ser informado se a categoria é ativa ou não")
        boolean ativo,

        @NotNull(message = "A data de criação é obrigatória")
        LocalDateTime dataCriacao
) {
}
