package com.empresa.loja.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Map<String, Object> obterClientes(int pagina, int tamanho, String ordenacao){
        logger.info("Buscando todos os clientes.");
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by(ordenacao));
        Page<Cliente> clientePage = clienteRepository.findAll(pageable);

        Map<String, Object> clientesMap = new HashMap<>();
        if (clientePage.hasContent()){
            logger.info("Clientes encontrados com sucesso.");
            clientesMap.put("clientes", clientePage.getContent());
            clientesMap.put("paginaAtual", clientePage.getNumber());
            clientesMap.put("totalPaginas", clientePage.getTotalPages());
            clientesMap.put("totalElementos", clientePage.getTotalElements());
        }

        return clientesMap;
    }

    public Cliente obterClientePeloID(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new NoSuchElementException("Cliente com ID " + id + " não encontrado."));
    }

    public List<Cliente> obterClientePeloNome(String nome){
        logger.info("Inciando busca por cliente com nome {}", nome);
        List<Cliente> clienteList = clienteRepository.findByNomeContaining(nome);

        if (clienteList.isEmpty()){
            logger.error("Nenhum cliente encontrado.");
            throw new NoSuchElementException("Nenhum cliente encontrado");
        }

        logger.info("Clientes com nome {}, encontrados com sucesso.", nome);
        return clienteList;
    }

    public void atualizarCliente(Long id, Cliente cliente){
        logger.info("Iniciando atualização de dados do cliente com ID {}", id);
        if(clienteRepository.findById(id).isEmpty()){
            logger.error("Cliente não encontrado");
            throw new NoSuchElementException("Cliente não encontrado.");
        }

        Optional<Cliente> clienteByEmail = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteByEmail.isPresent() && !clienteByEmail.get().getId().equals(id)) {
            logger.error("Email {} já está em uso por outro cliente", cliente.getEmail());
            throw new ClientAlreadyExistsException("Email já está em uso por outro cliente");
        }

        if (cliente.getEndereco() != null){
            enderecoRepository.save(cliente.getEndereco());
        }

        clienteRepository.updateClienteById(id, cliente.getNome(), cliente.getEmail(), cliente.getTelefone());
        logger.info("Cliente atualizado");
    }

    public void deletarCliente(Long id){
        logger.info("Inicando deleção do cliente com ID {}", id);
        if (!clienteRepository.existsById(id)){
            logger.error("Cliente não encontrado.");
            throw new NoSuchElementException("Cliente não deletado.");
        }
        clienteRepository.deleteById(id);
        logger.info("Cliente deletado.");
    }
}
