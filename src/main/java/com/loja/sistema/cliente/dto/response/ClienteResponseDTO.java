package com.loja.sistema.cliente.dto.response;

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
