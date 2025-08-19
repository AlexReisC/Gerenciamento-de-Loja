package com.loja.sistema.dtos.response;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        EnderecoResponseDTO endereco,
        LocalDateTime dataCadastro
) {
}
