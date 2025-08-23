package com.loja.sistema.cliente.dto.request;

import java.time.LocalDateTime;

public record ClienteFiltro(
        String nome,
        LocalDateTime dataCadastro
) {
}
