package com.empresa.loja.dtos.response;

import java.math.BigDecimal;

public record ProdutoDTOResponse(
        Long id,
        String nome,
        BigDecimal preco
) {
}
