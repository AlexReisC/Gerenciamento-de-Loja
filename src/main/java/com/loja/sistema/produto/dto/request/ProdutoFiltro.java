package com.loja.sistema.produto.dto.request;

import java.math.BigDecimal;

public record ProdutoFiltro(
        String nome,
        Long categoriaId,
        BigDecimal precoMinimo,
        BigDecimal precoMaximo
) {
}
