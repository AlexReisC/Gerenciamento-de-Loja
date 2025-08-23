package com.loja.sistema.categoria.dto.response;

import java.util.List;

public record CategoriaPageResponse(
        List<CategoriaResponseDTO> list,
        int paginaAtual,
        int totalPaginas,
        long totalElementos,
        int tamanhoPagina
) {
}
