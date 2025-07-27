package com.empresa.loja.dtos.request;

import com.empresa.loja.cliente.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

public record ClienteDTO(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @Email(message = "Email deve ser válido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "O CPF deve conter formato válido")
        String cpf,

        @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}",
                message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
        String telefone,

        @NotNull(message = "Data de cadastro é pbrigatoria")
        LocalDateTime dataCadastro,

        @Valid
        @NotNull(message = "Endereço é obrigatório")
        Endereco endereco
) {
}
