package com.empresa.loja.dtos.response;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        boolean sucesso,
        String mensagem,
        T dados,
        LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> sucesso(String mensagem, T dados) {
        return new ApiResponse<>(true, mensagem, dados, LocalDateTime.now());
    }
}
