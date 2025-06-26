package com.empresa.loja.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.loja.dtos.request.RegistroClienteDTO;
import com.empresa.loja.entity.Cliente;
import com.empresa.loja.service.ClienteService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Object> registrarCliente(@Valid @RequestBody RegistroClienteDTO registroClienteDTO) {
        Cliente cliente = new Cliente(
            registroClienteDTO.nome(),
            registroClienteDTO.email(),
            registroClienteDTO.senha(),
            registroClienteDTO.cpf(),
            registroClienteDTO.telefone(),
            registroClienteDTO.dataCadastro(),
            registroClienteDTO.endereco()
        );
        clienteService.salvarCliente(cliente);
        return new ResponseEntity<>("Cliente cadastrado com sucesso!", HttpStatus.CREATED);
    }


    
}
