package com.loja.sistema.cliente.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteAtualizacaoDTO(
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Pattern(regexp = "^[a-zA-zÀ-ÿ\\s]+$", message = "Nome deve conter apenas letras")
    String nome,
    
    @Email(message = "Email inválido")
    String email,
    
    @Pattern(regexp = "(\\d{2}) \\d{4,5}-\\d{4}", message = "O telefone deve estar no formato (XX) XXXXX-XXXX")
    String telefone,

    EnderecoRequestDTO endereco
) { 
}
