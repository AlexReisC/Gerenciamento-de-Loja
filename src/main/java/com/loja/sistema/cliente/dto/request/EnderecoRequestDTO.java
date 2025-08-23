package com.loja.sistema.cliente.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(
        @NotBlank(message = "CEP é obrigatório")
        @Size(min = 8, max = 9, message = "CEP inválido")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
        String cep,

        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,

        @NotBlank(message = "Bairro é obrigatório")
        String bairro,

        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @NotBlank(message = "UF é obrigatória")
        @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
        @Pattern(regexp = "\\s[A-Z]", message = "A UF deve ser formada por duas letras maiúsculas")
        String uf
) {
}
