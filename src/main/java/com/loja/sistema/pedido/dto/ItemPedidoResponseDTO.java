package com.loja.sistema.pedido.dto;

import java.math.BigDecimal;

import com.loja.sistema.produto.dto.response.ProdutoResponseDTO;

public record ItemPedidoResponseDTO(
        Long id,
        ProdutoResponseDTO produto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}
