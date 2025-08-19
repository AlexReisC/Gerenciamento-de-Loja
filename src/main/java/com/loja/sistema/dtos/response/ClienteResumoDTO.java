package com.loja.sistema.dtos.response;

public record ClienteResumoDTO(
    Long id,
    String nome,
    String email
) {
}
