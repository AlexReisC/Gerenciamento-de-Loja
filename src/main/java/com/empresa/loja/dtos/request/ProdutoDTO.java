package com.empresa.loja.dtos.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoDTO(
        @NotBlank(message = "O nome não pode ficar em branco")
        @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres")
        String nome,

        @Size(max = 200, message = "O nome do produto deve ter até 200 caracteres")
        String descricao,

        @NotNull(message = "O preco não pode ficar vazio")
        @Digits(integer = 8, fraction = 2)
        @DecimalMin(value = "0.00", message = "O valor do preço deve ser maior que 0")
        BigDecimal preco,

        @NotNull(message = "O estoque não pode ser nulo")
        @Min(value = 1, message = "O valor para estoque deve ser maior que 0")
        int estoque,

        @NotNull(message = "É obrigatório informar se o produto está ativo ou não")
        boolean ativo,

        @NotNull(message = "O produto deve ter uma categoria associada.")
        CategoriaDTO categoria
) {
}
