package com.empresa.loja.dtos.request;

import java.time.LocalDateTime;

import com.empresa.loja.entity.Endereco;

public record RegistroClienteDTO(String nome, String email, String senha, 
    String cpf, String telefone, LocalDateTime dataCadastro, 
    Endereco endereco) {
}
