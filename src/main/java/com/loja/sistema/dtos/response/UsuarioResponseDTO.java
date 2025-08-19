package com.loja.sistema.dtos.response;

import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Long id,
        String username,
        String email,
        String role,
        Boolean ativo,
        LocalDateTime dataCriacao
) {
}
