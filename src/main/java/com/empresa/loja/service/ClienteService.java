package com.empresa.loja.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.loja.entity.Cliente;
import com.empresa.loja.exception.ClientAlreadyExistsException;
import com.empresa.loja.repository.ClienteRepository;
import com.empresa.loja.repository.EnderecoRepository;

@Service
public class ClienteService {
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public void salvarCliente(Cliente cliente){
        logger.info("Iniciando cadastro do cliente");
        Optional<Cliente> clienteByEmail = clienteRepository.findByEmail(cliente.getEmail());

        if (clienteByEmail.isPresent()) {
            logger.error("Erro ao cadastrar cliente, email {} em uso por outro cliente.", cliente.getEmail());
            throw new ClientAlreadyExistsException("Este email está em uso por outro cliente");
        }

        Optional<Cliente> clientebyCpf = clienteRepository.findByCpf(cliente.getCpf());
        if (clientebyCpf.isPresent()) {
            logger.error("Erro ao cadastrar cliente, cpf {} em uso por outro cliente ", cliente.getCpf());
            throw new ClientAlreadyExistsException("Este CPF já está em uso por outro cliente");
        }

        enderecoRepository.save(cliente.getEndereco());
        clienteRepository.save(cliente);
        logger.info("Cliente cadastrado.");
    }

    public Cliente buscarClientePeloID(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow();
    }

    
}
