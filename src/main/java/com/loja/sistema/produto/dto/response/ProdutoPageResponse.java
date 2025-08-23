package com.loja.sistema.produto.dto.response;

import java.util.List;

public record ProdutoPageResponse(
    List<ProdutoResponseDTO> clientes,
    int paginaAtual,
    int totalPaginas,
    long totalElementos,
    int tamanhoPagina
) {
}
