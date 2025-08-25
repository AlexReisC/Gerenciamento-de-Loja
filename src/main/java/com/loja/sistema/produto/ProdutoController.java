package com.loja.sistema.produto;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loja.sistema.dtos.response.PageResponse;
import com.loja.sistema.produto.dto.request.ProdutoAtualizacaoDTO;
import com.loja.sistema.produto.dto.request.ProdutoFiltro;
import com.loja.sistema.produto.dto.request.ProdutoRequestDTO;
import com.loja.sistema.produto.dto.response.ProdutoResponseDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.salvarProduto(produtoRequestDTO);
        return ResponseEntity.created(URI.create("/api/produtos/" + produtoResponseDTO.id())).body(produtoResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProduto(
        @PathVariable @NotNull @DecimalMin(value = "1", message = "ID deve ser um número positivo") Long id
    ) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProdutoResponseDTO>> buscarTodosProdutos(
        @DecimalMin(value = "0", message = "A página deve ser zero ou maior") @RequestParam(defaultValue = "0") int page,
        @DecimalMin(value = "1", message = "O tamanho da página deve ser maior ou igual a 1") @RequestParam(defaultValue = "10") int size,
        @Pattern(regexp = "id|nome|preco|data_cadastro|''", message = "Campo de ordenação inválido") @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction,
        @Valid ProdutoFiltro filtro
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageResponse<ProdutoResponseDTO> paginaProdutos = produtoService.buscarProdutos(PageRequest.of(page, size, sort), filtro);
        return ResponseEntity.ok(paginaProdutos);
    }
    
    @GetMapping("/categoria/{categoria_id}")
    public ResponseEntity<PageResponse<ProdutoResponseDTO>> buscarProdutosPorCategoria(
        @PathVariable @NotNull @DecimalMin(value = "1", message = "ID deve ser um número positivo") Long categoriaId,
        @DecimalMin(value = "0", message = "A página deve ser zero ou maior") @RequestParam(defaultValue = "0") int page,
        @DecimalMin(value = "1", message = "O tamanho da página deve ser maior ou igual a 1") @RequestParam(defaultValue = "10") int size,
        @Pattern(regexp = "id|nome|preco|dataCadastro|''", message = "Campo de ordenação inválido") @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageResponse<ProdutoResponseDTO> paginaProdutos = produtoService.buscarProdutosPorCategoria(categoriaId, PageRequest.of(page, size, sort));
        return ResponseEntity.ok(paginaProdutos);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarProduto(
        @PathVariable @NotNull @DecimalMin(value = "1", message = "ID deve ser um número positivo") Long id,
        @RequestBody @Valid ProdutoAtualizacaoDTO produtoAtualizacaoDTO
    ) {
        produtoService.atualizarProduto(id, produtoAtualizacaoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(
        @PathVariable @NotNull @DecimalMin(value = "1", message = "ID deve ser um número positivo") Long id
    ) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
