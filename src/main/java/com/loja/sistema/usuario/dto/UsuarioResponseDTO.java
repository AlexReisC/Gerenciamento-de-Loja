package com.loja.sistema.usuario.dto;

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
