package com.loja.sistema.dtos.response;

public record EnderecoResponseDTO(
        String cep,
        String logradouro,
        String bairro,
        String cidade,
        String uf
) {
}
