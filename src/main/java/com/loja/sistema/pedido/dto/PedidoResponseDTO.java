package com.loja.sistema.pedido.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.loja.sistema.cliente.dto.ClienteResponseDTO;

public record PedidoResponseDTO(
        Long id,
        String numero,
        ClienteResponseDTO cliente,
        List<ItemPedidoResponseDTO> itens,
        BigDecimal valorTotal,
        String status,
        LocalDateTime dataPedido
) {
}
