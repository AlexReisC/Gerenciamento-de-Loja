package com.empresa.loja.exception;

import com.empresa.loja.dtos.response.ErroResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ErroResponse> handleClientAlreadyExists(ClientAlreadyExistsException exception){
        ErroResponse erroResponse = new ErroResponse(
                exception.getMensagem(),
                null,
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(erroResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidationExceptions(MethodArgumentNotValidException exception){
        List<String> erros = new ArrayList<>();
        exception.getBindingResult()
                .getFieldErrors().forEach(er -> erros.add(er.getField() + ": " + er.getDefaultMessage()));

        ErroResponse erroResponse = new ErroResponse(
                "Dados inválidos",
                erros,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroResponse> handleConstraintsExceptions(ConstraintViolationException ex){
        List<String> erros = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> erros.add(violation.getPropertyPath() +
                ": " + violation.getMessage()));

        ErroResponse erroResponse = new ErroResponse(
                "Dados inválidos",
                erros,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
    }
}
