package com.empresa.loja.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponse(
        String mensagem,
        List<String> erros,
        int status,
        LocalDateTime timeStamp
) { }
