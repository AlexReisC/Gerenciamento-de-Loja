package com.empresa.loja.dtos.request;

import com.empresa.loja.entity.Endereco;

public record ClienteDTO(
        String nome,
        String email,
        String cpf,
        String telefone,
        Endereco endereco
) {
}
