package com.loja.sistema.produto.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProdutoAtualizacaoDTO(
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    String nome,

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    String descricao,

    @DecimalMin(value = "0.01", message = "O preço deve ser maior que 0")
    BigDecimal preco,

    @PositiveOrZero(message = "O estoque não pode ser negativo")
    Integer estoque,
    
    Boolean ativo,

    @DecimalMin(value = "1", message = "O ID da Categoria deve ser maior que zero")
    Long categoriaId
) {
}
