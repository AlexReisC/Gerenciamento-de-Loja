package com.loja.sistema.cliente;

import java.net.URI;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loja.sistema.cliente.dto.request.ClienteAtualizacaoDTO;
import com.loja.sistema.cliente.dto.request.ClienteFiltro;
import com.loja.sistema.cliente.dto.request.ClienteRequestDTO;
import com.loja.sistema.cliente.dto.response.ClienteResponseDTO;
import com.loja.sistema.dtos.response.PageResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@RestController("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDto) {
        ClienteResponseDTO clienteResponse = clienteService.salvarCliente(clienteRequestDto);
        return ResponseEntity.created(URI.create("/api/clientes/" + clienteResponse.id())).body(clienteResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obterClientePorId(@PathVariable @NotNull @DecimalMin(value = "1", message = "O ID deve ser maior ou igual a 1") Long id) {
        ClienteResponseDTO clienteResponse = clienteService.obterClientePeloID(id);
        return ResponseEntity.ok(clienteResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ClienteResponseDTO>> listarTodosClientes(
            @PositiveOrZero(message = "A página deve ser zero ou maior") @RequestParam(defaultValue = "0") int pagina,
            @DecimalMin(value = "1", message = "O tamanho da página deve ser maior ou igual a 1") @RequestParam(defaultValue = "10") int tamanho,
            @RequestParam(defaultValue = "id") @Pattern(regexp = "id|nome|data_cadastro", message = "Campo de ordenação inválido") String classificarPor,
            @RequestParam(defaultValue = "asc") @Pattern(regexp = "asc|desc", message = "Direção inválida (asc|desc)") String direcao,
            @RequestParam(required = false) ClienteFiltro filtro
    ) {
        Sort sort = direcao.equalsIgnoreCase("asc") ? Sort.by(classificarPor).ascending() : Sort.by(classificarPor).descending();
        PageResponse<ClienteResponseDTO> clientes = clienteService.obterClientes(PageRequest.of(pagina, tamanho, sort), filtro);
        
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarCliente(
        @PathVariable @NotNull @DecimalMin(value = "1", message = "O ID deve ser maior ou igual a 1") Long id, 
        @RequestBody @Valid ClienteAtualizacaoDTO clienteAtualizacaoDto) {
        clienteService.atualizarCliente(id, clienteAtualizacaoDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable @NotNull @DecimalMin(value = "1", message = "O ID deve ser maior ou igual a 1") Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
