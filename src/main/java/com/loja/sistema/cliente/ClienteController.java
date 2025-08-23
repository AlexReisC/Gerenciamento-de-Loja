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
import com.loja.sistema.cliente.dto.response.ClientePageResponseDTO;
import com.loja.sistema.cliente.dto.response.ClienteResponseDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<ClienteResponseDTO> obterClientePorId(@PathVariable @NotNull @DecimalMin("1") Long id) {
        ClienteResponseDTO clienteResponse = clienteService.obterClientePeloID(id);
        return ResponseEntity.ok(clienteResponse);
    }

    @GetMapping
    public ResponseEntity<ClientePageResponseDTO> listarTodosClientes(
            @PositiveOrZero @RequestParam(defaultValue = "0") int page,
            @DecimalMin("1") @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) ClienteFiltro filtro
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        ClientePageResponseDTO clientes = clienteService.obterClientes(PageRequest.of(page, size, sort), filtro);
        
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarCliente(@PathVariable @NotNull @DecimalMin("1") Long id, 
        @RequestBody @Valid ClienteAtualizacaoDTO clienteAtualizacaoDto) {
        clienteService.atualizarCliente(id, clienteAtualizacaoDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable @NotNull @DecimalMin("1") Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
