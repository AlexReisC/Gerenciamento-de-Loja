package com.loja.sistema.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErroApiResponse(
        String mensagem,
        List<String> erros,
        Integer status,
        LocalDateTime timestamp
) {
}
