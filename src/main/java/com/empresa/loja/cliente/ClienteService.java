package com.empresa.loja.cliente;

import com.empresa.loja.dtos.request.ClienteDTO;
import com.empresa.loja.exception.ClientAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClienteService {
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Transactional
    public Cliente salvarCliente(ClienteDTO clienteDTO){
        Cliente cliente = clienteMapper.toEntity(clienteDTO);

        logger.info("Iniciando cadastro do cliente");
        boolean clienteByEmail = clienteRepository.findByEmail(cliente.getEmail()).isPresent();

        if (clienteByEmail) {
            logger.error("Erro ao cadastrar cliente, email {} em uso por outro cliente.", cliente.getEmail());
            throw new ClientAlreadyExistsException("Este email está em uso por outro cliente");
        }

        boolean clientebyCpf = clienteRepository.findByCpf(cliente.getCpf()).isPresent();
        if (clientebyCpf) {
            logger.error("Erro ao cadastrar cliente, cpf {} em uso por outro cliente ", cliente.getCpf());
            throw new ClientAlreadyExistsException("Este CPF já está em uso por outro cliente");
        }

        return clienteRepository.save(cliente);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Cliente obterClientePeloID(Long id){
        return clienteRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Cliente com ID " + id + " não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterClientePeloNome(String nome){
        logger.info("Inciando busca por cliente com nome {}", nome);
        List<Cliente> clienteList = clienteRepository.findByNomeContaining(nome);

        if (clienteList.isEmpty()){
            logger.error("Nenhum cliente com nome {} foi encontrado.", nome);
            throw new NoSuchElementException("Nenhum cliente encontrado");
        }

        logger.info("Clientes com nome {}, encontrados com sucesso.", nome);
        return clienteList;
    }

    @Transactional
    public void atualizarCliente(Long id, ClienteDTO clienteDTO){
        logger.info("Iniciando atualização de dados do cliente com ID {}", id);

        Cliente cliente = obterClientePeloID(id);

        Optional<Cliente> clienteByEmail = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteByEmail.isPresent() && !clienteByEmail.get().getEmail().equals(cliente.getEmail())) {
            logger.error("Email {} já está em uso pelo cliente ID {}", cliente.getEmail(), clienteByEmail.get().getId());
            throw new ClientAlreadyExistsException("O email já está em uso por outro cliente");
        }

        cliente.setNome(clienteDTO.nome());
        cliente.setEmail(clienteDTO.email());
        cliente.setCpf(clienteDTO.cpf());
        cliente.setTelefone(clienteDTO.telefone());
        if (clienteDTO.endereco() != null) {
            cliente.setEndereco(clienteDTO.endereco());
        }

        clienteRepository.save(cliente);
        logger.info("Cliente atualizado");
    }

    @Transactional
    public void deletarCliente(Long id){
        logger.info("Inicando deleção do cliente com ID {}", id);
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            logger.info("Cliente deletado.");
        } 
        throw new NoSuchElementException("Cliente não existe.");
    }
}
