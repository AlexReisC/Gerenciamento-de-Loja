package com.loja.sistema.cliente.dto.response;

public record EnderecoResponseDTO(
        String cep,
        String logradouro,
        String bairro,
        String cidade,
        String uf
) {
}
