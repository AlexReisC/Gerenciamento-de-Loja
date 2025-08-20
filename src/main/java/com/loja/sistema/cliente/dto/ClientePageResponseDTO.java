package com.loja.sistema.cliente.dto;

import java.util.List;

public record ClientePageResponseDTO(
    List<ClienteResponseDTO> clientes,
    int paginaAtual,
    int totalPaginas,
    long totalClientes
) {
}
