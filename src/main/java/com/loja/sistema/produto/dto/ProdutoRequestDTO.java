package com.loja.sistema.produto.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
        String descricao,

        @NotNull(message = "Preço é obrigatório")
        @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "Estoque é obrigatório")
        @PositiveOrZero(message = "Estoque não pode ser negativo")
        Integer estoque,

        @NotNull(message = "Categoria é obrigatória")
        Long categoriaId
) {
}
