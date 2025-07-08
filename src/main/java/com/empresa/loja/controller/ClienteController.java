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

import java.util.List;
import java.util.Map;


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
        ApiResponse<Object> response = ApiResponse.sucesso("Cliente cadastrado com sucesso", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarClientes(@RequestParam(required = false, defaultValue = "0") int pagina,
                                                        @RequestParam(required = false, defaultValue = "10") int tamanho,
                                                        @RequestParam(required = false, defaultValue = "id") String ordenacao){
        Map<String, Object> clientes = clienteService.obterClientes(pagina, tamanho, ordenacao);
        if (clientes.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> listarClientePeloID(@PathVariable @Min(value = 1, message = "ID deve ser maior que zero") Long id){
        Cliente cliente = clienteService.obterClientePeloID(id);
        ApiResponse<Cliente> response = ApiResponse.sucesso("Cliente encontrado", cliente);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nome")
    public ResponseEntity<ApiResponse<?>> listarClientesPeloNome(@RequestParam(required = true, defaultValue = "nome") @Size(min = 3, message = "O nome deve conter pelo menos 3 letras") String nome){
        List<Cliente> clientes = clienteService.obterClientePeloNome(nome);
//        List<ClienteResponse> responses = clientes.stream()
//                .map(this::convertToResponse)
//                .toList();
        ApiResponse<List<Cliente>> response = ApiResponse.sucesso("Clientes encontrados", clientes);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCliente(@Valid @RequestBody ClienteDTO clienteDTO,
                                                   @Valid @PathVariable Long id){
        Cliente cliente = convertDtoToCliente(clienteDTO);

        clienteService.atualizarCliente(id, cliente);
        ApiResponse<Object> response = ApiResponse.sucesso("Dados do cliente atualizados", null);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCliente(@Valid @PathVariable Long id){
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    private ClienteResponse convertToResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getDataCadastro()
        );
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
