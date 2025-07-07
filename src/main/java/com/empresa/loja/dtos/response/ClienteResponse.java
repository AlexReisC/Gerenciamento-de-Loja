package com.empresa.loja.dtos.response;

import com.empresa.loja.entity.Endereco;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        Endereco endereco,
        LocalDateTime dataCadastro
) {
}
