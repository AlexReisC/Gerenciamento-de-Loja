package com.loja.sistema.cliente;

import java.util.Optional;

import com.loja.sistema.pedido.Pedido;
import com.loja.sistema.pedido.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loja.sistema.cliente.dto.request.ClienteAtualizacaoDTO;
import com.loja.sistema.cliente.dto.request.ClienteRequestDTO;
import com.loja.sistema.cliente.dto.response.ClientePageResponseDTO;
import com.loja.sistema.cliente.dto.response.ClienteResponseDTO;
import com.loja.sistema.exception.ElementoNaoEncontradoException;
import com.loja.sistema.exception.EntidadeDuplicadaException;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PedidoRepository pedidoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, PedidoRepository pedidoRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public ClienteResponseDTO salvarCliente(ClienteRequestDTO clienteRequestDto){
        logger.info("Iniciando cadastro do cliente");

        boolean clienteByEmail = clienteRepository.existsByEmail(clienteRequestDto.email());
        if (clienteByEmail) {
            logger.error("Erro ao cadastrar cliente, email {} em uso por outro cliente.", clienteRequestDto.email());
            throw new EntidadeDuplicadaException("Este email está em uso por outro cliente");
        }

        boolean existsByCpf = clienteRepository.existsByCpf(clienteRequestDto.cpf());
        if (existsByCpf) {
            logger.error("Erro ao cadastrar cliente, cpf {} em uso por outro cliente ", clienteRequestDto.cpf());
            throw new EntidadeDuplicadaException("Este CPF já está em uso por outro cliente");
        }
        
        Cliente cliente = clienteMapper.toEntity(clienteRequestDto);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        logger.info("Cliente cadastrado com sucesso. ID: {}", clienteSalvo.getId());
        return clienteMapper.toResponse(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public ClientePageResponseDTO obterClientes(int pagina, int tamanho, String ordenacao){
        logger.info("Buscando todos os clientes com paginação.");
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by(ordenacao));
        Page<Cliente> clientePage = clienteRepository.findAll(pageable);

        return new ClientePageResponseDTO(
                clientePage.getContent()
                    .stream()
                    .map(clienteMapper::toResponse)
                    .toList(),
                clientePage.getNumber(),
                clientePage.getTotalPages(),
                clientePage.getTotalElements(),
                clientePage.getSize()
        );
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO obterClientePeloID(Long id){
        Cliente cliente = clienteRepository.findById(id).orElseThrow(
                () -> new ElementoNaoEncontradoException("Cliente com ID " + id + " não encontrado."));
        
        return clienteMapper.toResponse(cliente);
    }

    @Transactional
    public void atualizarCliente(Long id, ClienteAtualizacaoDTO clienteAtualizacaoDto){
        logger.info("Iniciando atualização de dados do cliente com ID {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ElementoNaoEncontradoException("Cliente com ID " + id + " não encontrado."));

        Optional<Cliente> cliente2 = clienteRepository.findByEmailAndIdNot(clienteAtualizacaoDto.email(), id);
        if (cliente2.isPresent()) {
            logger.error("Email {} já em uso pelo cliente com ID {}", clienteAtualizacaoDto.email(), cliente2.get().getId());
            throw new EntidadeDuplicadaException("O email já está em uso por outro cliente");
        }

        clienteMapper.updateEntityFromRequest(clienteAtualizacaoDto, cliente);
        clienteRepository.save(cliente);
        logger.info("Cliente com ID {} atualizado com sucesso", cliente.getId());
    }

    @Transactional
    public void deletarCliente(Long id){
        logger.info("Iniciando exclusão do cliente com ID: {}", id);

        if (!clienteRepository.existsById(id)) {
            logger.error("Cliente não exite com ID: {}", id);
            throw new ElementoNaoEncontradoException("Cliente não existe.");
        }

        Optional<Pedido> pedido = pedidoRepository.findByCliente(id);
        if (pedido.isEmpty()){
            clienteRepository.deleteById(id);
        } else {
            boolean statusCancelado = pedido.get().getStatus().toString().equals("CANCELADO");
            boolean statusEntregue = pedido.get().getStatus().toString().equals("ENTREGUE");
            if (statusCancelado || statusEntregue) {
                clienteRepository.deleteById(id);
            }
        }
        logger.info("Cliente com ID {} excluído com sucesso.", id);
    }
}
