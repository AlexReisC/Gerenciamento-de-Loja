package com.empresa.loja.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
        @NotBlank(message = "Logradouro é obrigatório")
        @Size(max = 255, message = "Logradouro não pode exceder 255 caracteres")
        String logradouro,

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
        String cep,

        @NotBlank(message = "Bairro é obrigatório")
        @Size(max = 100, message = "Bairro não pode exceder 100 caracteres")
        String bairro,

        @NotBlank(message = "Cidade é obrigatória")
        @Size(max = 100, message = "Cidade não pode exceder 100 caracteres")
        String cidade,

        @NotBlank(message = "UF é obrigatório")
        @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
        String uf
) {
}
