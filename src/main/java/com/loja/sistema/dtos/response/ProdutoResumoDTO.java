package com.loja.sistema.dtos.response;

import java.math.BigDecimal;

public record ProdutoResumoDTO(
    Long id,
    String nome,
    BigDecimal preco
) {
}
