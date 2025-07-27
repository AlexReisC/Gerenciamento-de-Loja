package com.empresa.loja.dtos.request;

import com.empresa.loja.pedido.StatusPedido;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO(
        @NotBlank(message = "O número do pedido é obrigatório")
        String numero,

        @NotNull(message = "A data do pedido é obrigatória")
        LocalDateTime dataPedido,

        @NotNull(message = "O valor do pedido é obrigatório")
        @Digits(integer = 10, fraction = 2)
        @DecimalMin(value = "0.00", message = "O valor do pedido deve ser maior que zero")
        BigDecimal valorTotal,

        @NotNull(message = "O status do pedido deve ser informado")
        StatusPedido status,

        @NotNull(message = "O pedido deve ser associado a um cliente")
        ClienteDTO cliente,

        @NotNull(message = "Deve haver itens no pedido")
        List<ItemPedidoDTO> itens
) {
}
