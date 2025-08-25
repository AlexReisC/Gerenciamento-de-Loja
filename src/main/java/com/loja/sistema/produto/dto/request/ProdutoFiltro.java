package com.loja.sistema.produto.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;

public record ProdutoFiltro(
        String nome,
        @DecimalMin(value = "1", message = "O ID da categoria deve ser maior ou igual a 1")
        Long categoriaId,
        @PositiveOrZero(message = "O preço mínimo não pode ser negativo")
        BigDecimal precoMinimo,
        @PositiveOrZero(message = "O preço máximo não pode ser negativo")
        BigDecimal precoMaximo
) {
}
