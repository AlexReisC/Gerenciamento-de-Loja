package com.empresa.loja.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.loja.entity.Cliente;
import com.empresa.loja.exception.ClientAlreadyExistsException;
import com.empresa.loja.repository.ClienteRepository;
import com.empresa.loja.repository.EnderecoRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public void salvarCliente(Cliente cliente){
        Optional<Cliente> clienteByEmail = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteByEmail.isPresent()) {
            throw new ClientAlreadyExistsException("Já existe um cliente com o email: " + cliente.getEmail());
        }
        
        Optional<Cliente> clientebyCpf = clienteRepository.findByCpf(cliente.getCpf());
        if (clientebyCpf.isPresent()) {
            throw new ClientAlreadyExistsException("Já existe um cliente com o cpf: " + cliente.getCpf());
        }

        LocalDateTime dataCadastro = cliente.getDataCadastro();
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
            cliente.setDataCadastro(dataCadastro);
        }

        enderecoRepository.save(cliente.getEndereco());
        clienteRepository.save(cliente);
    }

    public Cliente buscarClientePeloID(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow();
    }

    
}
