package com.loja.sistema.dtos.response;

import java.util.List;

public record PageResponse<T>(
    List<T> conteudo, 
    int paginaAtual, 
    int totalPaginas, 
    long totalElementos, 
    int tamanhoPagina
) {
}
