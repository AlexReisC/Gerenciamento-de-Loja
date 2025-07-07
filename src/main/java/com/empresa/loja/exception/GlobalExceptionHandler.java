package com.empresa.loja.exception;

import com.empresa.loja.dtos.response.ErroApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ErroApiResponse> handleClientAlreadyExists(ClientAlreadyExistsException exception){
        ErroApiResponse erroApiResponse = new ErroApiResponse(
                exception.getMensagem(),
                null,
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
                "Dados inválidos",
                erros,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroApiResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroApiResponse> handleConstraintsExceptions(ConstraintViolationException ex){
        List<String> erros = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> erros.add(violation.getPropertyPath() +
                ": " + violation.getMessage()));

        ErroApiResponse erroApiResponse = new ErroApiResponse(
                "Dados inválidos",
                erros,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroApiResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErroApiResponse> handleNoSuchElement(NoSuchElementException exception){
        ErroApiResponse erroApiResponse = new ErroApiResponse(
                exception.getMessage(),
                null,
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroApiResponse);
    }
}
