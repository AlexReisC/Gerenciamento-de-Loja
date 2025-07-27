package com.empresa.loja.dtos.response;

import java.math.BigDecimal;

public record ItemDTOResponse(
        Long id,
        ProdutoDTOResponse produto,
        String quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}
