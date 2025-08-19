package com.loja.sistema.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotEmpty(message = "Pedido deve conter pelo menos 1 item")
        @Valid
        List<ItemPedidoRequestDTO> itens
) {
}
