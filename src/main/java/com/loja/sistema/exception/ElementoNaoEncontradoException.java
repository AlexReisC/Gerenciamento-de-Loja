package com.loja.sistema.exception;

public class ElementoNaoEncontradoException extends RuntimeException {
    public ElementoNaoEncontradoException(String message) {
        super(message);
    }

    public ElementoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
