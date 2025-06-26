package com.empresa.loja.exception;

public class ClientAlreadyExistsException extends RuntimeException{
    private String mensagem;
    
    public ClientAlreadyExistsException() {
    }

    public ClientAlreadyExistsException(String message) {
        super(message);
        this.mensagem = message;
    }

    public String getMensagem() {
        return mensagem;
    }

}
