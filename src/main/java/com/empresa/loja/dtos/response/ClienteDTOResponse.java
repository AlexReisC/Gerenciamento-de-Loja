package com.empresa.loja.dtos.response;

public record ClienteDTOResponse(
        Long id,
        String nome,
        String email
) {
}
