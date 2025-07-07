package com.empresa.loja.controller;

import com.empresa.loja.dtos.response.ApiResponse;
import com.empresa.loja.dtos.response.ClienteResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empresa.loja.dtos.request.ClienteDTO;
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
    public ResponseEntity<Object> registrarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = convertDtoToCliente(clienteDTO);
        clienteService.salvarCliente(cliente);
        return new ResponseEntity<>("Cliente cadastrado com sucesso!", HttpStatus.CREATED);
    }

    private Cliente convertDtoToCliente(ClienteDTO clienteDTO){
        return new Cliente(
                clienteDTO.nome(),
                clienteDTO.email(),
                clienteDTO.cpf(),
                clienteDTO.telefone(),
                clienteDTO.endereco()
        );
    }
}
