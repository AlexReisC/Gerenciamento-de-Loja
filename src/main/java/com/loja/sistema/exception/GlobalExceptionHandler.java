package com.loja.sistema.exception;

import com.loja.sistema.dtos.response.ErroApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ResponseEntity<ErroApiResponse> handleClientAlreadyExists(EntidadeDuplicadaException exception){
        ErroApiResponse erroApiResponse = new ErroApiResponse(
                "Cliente inválido",
                List.of(exception.getMessage()),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroApiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroApiResponse> handleValidationExceptions(MethodArgumentNotValidException exception){
        List<String> erros = new ArrayList<>();
        exception.getBindingResult()
                .getFieldErrors().forEach(er -> erros.add(er.getField() + ": " + er.getDefaultMessage()));

        ErroApiResponse erroApiResponse = new ErroApiResponse(
                "Dados de entrada inválidos",
                erros,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(erroApiResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroApiResponse> handleConstraintsExceptions(ConstraintViolationException ex){
        List<String> erros = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> erros.add(violation.getPropertyPath() +
                ": " + violation.getMessage()));

        ErroApiResponse erroApiResponse = new ErroApiResponse(
                "Dados de entrada inválidos",
                erros,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(erroApiResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErroApiResponse> handleNoSuchElement(NoSuchElementException exception){
        ErroApiResponse erroApiResponse = new ErroApiResponse(
                "Não encontrado",
                List.of(exception.getMessage()),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroApiResponse);
    }
}
