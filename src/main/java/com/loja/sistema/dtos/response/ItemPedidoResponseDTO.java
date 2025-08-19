package com.loja.sistema.dtos.response;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long id,
        ProdutoResponseDTO produto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}
