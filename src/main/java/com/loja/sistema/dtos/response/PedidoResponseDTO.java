package com.loja.sistema.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
