package com.empresa.loja.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemPedidoDTO(
        @NotNull(message = "É obrigatório ter um pedido associado ao item")
        PedidoDTO pedido,

        @NotNull(message = "O item deve ter um produto")
        ProdutoDTO produto,

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade deve ser maior que zero")
        int quantidade,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.00", message = "O preco deve ser maior que zero")
        @Digits(integer = 8, fraction = 2)
        BigDecimal precoUnitario
) {
}
