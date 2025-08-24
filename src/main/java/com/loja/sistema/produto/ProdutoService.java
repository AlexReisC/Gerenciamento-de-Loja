package com.loja.sistema.produto;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loja.sistema.categoria.CategoriaRepository;
import com.loja.sistema.dtos.response.PageResponse;
import com.loja.sistema.exception.ElementoNaoEncontradoException;
import com.loja.sistema.exception.EntidadeDuplicadaException;
import com.loja.sistema.exception.OperacaoNaoPermitidaException;
import com.loja.sistema.pedido.PedidoRepository;
import com.loja.sistema.produto.dto.request.ProdutoAtualizacaoDTO;
import com.loja.sistema.produto.dto.request.ProdutoFiltro;
import com.loja.sistema.produto.dto.request.ProdutoRequestDTO;
import com.loja.sistema.produto.dto.response.ProdutoResponseDTO;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoMapper produtoMapper;

    private final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.pedidoRepository = null;
        this.produtoMapper = produtoMapper;
    }

    @Transactional
    public ProdutoResponseDTO salvarProduto(ProdutoRequestDTO produtoRequestDto) {
        logger.info("Iniciando cadastro do produto.");

        boolean produtoExiste = produtoRepository.existsByNomeIgnoreCase(produtoRequestDto.nome());
        if (produtoExiste) {
            logger.error("Erro ao cadastrar produto, {} já existe.", produtoRequestDto.nome());
            throw new EntidadeDuplicadaException("Este nome de produto já está em uso");
        }

        categoriaRepository.findById(produtoRequestDto.categoriaId())
                .orElseThrow(() -> new ElementoNaoEncontradoException("Categoria não encontrada."));
        
        Produto produto = produtoMapper.toEntity(produtoRequestDto);

        Produto salvo = produtoRepository.save(produto);
        logger.info("Produto {}, foi cadastrado com ID {}.", salvo.getNome(), salvo.getId());
        return produtoMapper.toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        logger.info("Buscando produto com ID: {}", id);
        Produto produto = produtoRepository.findById(id).orElseThrow(
            () -> new ElementoNaoEncontradoException("Produto não encontrado com ID: " + id)
        );
        
        logger.info("Produto encontrado: {}", produto.getNome());
        return produtoMapper.toResponseDTO(produto);
    }

    @Transactional(readOnly = true)
    public PageResponse<ProdutoResponseDTO> buscarProdutos(Pageable pageable, ProdutoFiltro filtro) {
        logger.info("Buscando 'todos os produtos com 'paginação e filtro");
        
        Specification<Produto> spec = ProdutoSpecification.nomeContem(filtro.nome())
            .and(ProdutoSpecification.categoriaIdIgual(filtro.categoriaId()))
            .and(ProdutoSpecification.precoMaiorOuIgual(filtro.precoMinimo()))
            .and(ProdutoSpecification.precoMenorOuIgual(filtro.precoMaximo()));
        
        Page<Produto> produtosPage = produtoRepository.findByAtivoTrue(spec, pageable);

        List<ProdutoResponseDTO> produtos = produtosPage.getContent().stream()
            .filter(t -> t.getAtivo() == true)
            .map(produtoMapper::toResponseDTO)
            .toList();

        return new PageResponse<>(
            produtos,
            produtosPage.getNumber(),
            produtosPage.getTotalPages(),
            produtosPage.getTotalElements(),
            produtosPage.getSize()
        );
    }

    @Transactional(readOnly = true)
    public PageResponse<ProdutoResponseDTO> buscarProdutosInativos(Pageable pageable) {
        logger.info("Buscando todos os produtos inativos.");
        Page<Produto> produtosInativos = produtoRepository.findByAtivoFalse(pageable);
        
        return new PageResponse<>(
            produtosInativos.getContent().stream()
            .map(produtoMapper::toResponseDTO)
            .toList(),
            produtosInativos.getNumber(),
            produtosInativos.getTotalPages(),
            produtosInativos.getTotalElements(),
            produtosInativos.getSize()
        );
    }
    
    @Transactional(readOnly = true)
    private PageResponse<ProdutoResponseDTO> buscarTodosProdutos(Pageable pageable) {
        logger.info("Buscando todos os produtos.");
        Page<Produto> produtos = produtoRepository.findAll(pageable);
        
        return new PageResponse<>(
            produtos.getContent().stream()
            .map(produtoMapper::toResponseDTO)
            .toList(),
            produtos.getNumber(),
            produtos.getTotalPages(),
            produtos.getTotalElements(),
            produtos.getSize()
        );
    }

    @Transactional(readOnly = true)
    public PageResponse<ProdutoResponseDTO> buscarProdutosPorCategoria(Long categoriaId, Pageable pageable) {
        logger.info("Buscando produtos da categoria com ID: {}", categoriaId);
        Page<Produto> produtos = produtoRepository.findByCategoriaId(categoriaId, pageable);

        return new PageResponse<>(
            produtos.getContent().stream()
                .map(produtoMapper::toResponseDTO)
                .toList(),
            produtos.getNumber(),
            produtos.getTotalPages(),
            produtos.getTotalElements(),
            produtos.getSize()
        );
    }

    @Transactional
    public void atualizarProduto(Long id, ProdutoAtualizacaoDTO produtoAtualizacaoDTO) {
        logger.info("Iniciando atualização do produto com ID: {}", id);
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ElementoNaoEncontradoException("Produto não encontrado com ID: " + id));

        Optional<Produto> produtoByNomeEquals = produtoRepository.findByNomeEqualsAndIdNot(produtoAtualizacaoDTO.nome(), id);

        if (produtoByNomeEquals.isPresent()) {
            logger.error("Erro ao atualizar produto com ID: {}, o nome {} já está em uso pelo produto com ID: {}", id, produtoAtualizacaoDTO.nome(), produtoByNomeEquals.get().getId());

            throw new EntidadeDuplicadaException("Este nome de produto já está associado a outro produto");
        }

        categoriaRepository.findById(produtoAtualizacaoDTO.categoriaId())
            .orElseThrow(() -> new ElementoNaoEncontradoException("Categoria não encontrada com ID: " + produtoAtualizacaoDTO.categoriaId()));

        produtoMapper.atualizarProduto(produtoAtualizacaoDTO, produto);
        produtoRepository.save(produto);
        logger.info("Produto com ID: {} atualizado com sucesso.", id);
    }

    @Transactional
    public void deletarProduto(Long id) {
        logger.info("Iniciando deleção do produto com ID: {}", id);
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ElementoNaoEncontradoException("Produto não encontrado com ID: " + id));

        Optional<String> produtoStatus = pedidoRepository.findStatusByProdutoId(id);
        if (produtoStatus.isPresent() && produtoStatus.get().equals("PENDENTE")) {
            logger.error("Erro ao deletar produto com ID: {}, o produto está associado a um pedido pendente.", id);
            throw new OperacaoNaoPermitidaException("Produto não pode ser deletado enquanto estiver associado a um pedido pendente.");
        }

        produtoRepository.delete(produto);
        logger.info("Produto com ID: {} deletado com sucesso.", id);
    }
}
