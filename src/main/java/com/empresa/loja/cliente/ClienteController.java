package com.empresa.loja.cliente;

import com.empresa.loja.dtos.request.ClienteDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Cliente> registrarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteResponse = clienteService.salvarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
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
    public ResponseEntity<Cliente> listarClientePeloID(@PathVariable @Min(value = 1, message = "ID deve ser maior que zero") Long id){
        Cliente cliente = clienteService.obterClientePeloID(id);

        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Cliente>> listarClientesPeloNome(@RequestParam(required = true, defaultValue = "nome") @Size(min = 3, message = "O nome deve conter pelo menos 3 letras") String nome){
        List<Cliente> clientes = clienteService.obterClientePeloNome(nome);
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarCliente(@Valid @RequestBody ClienteDTO clienteDTO,
                                                   @Valid @PathVariable Long id){
        clienteService.atualizarCliente(id, clienteDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCliente(@Valid @PathVariable Long id){
        clienteService.deletarCliente(id);
        return ResponseEntity.ok().build();
    }
}
