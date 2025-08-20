package com.loja.sistema.produto.dto;

import java.math.BigDecimal;

public record ProdutoResumoDTO(
    Long id,
    String nome,
    BigDecimal preco
) {
}
