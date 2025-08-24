package com.loja.sistema.produto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.loja.sistema.produto.dto.request.ProdutoAtualizacaoDTO;
import com.loja.sistema.produto.dto.request.ProdutoRequestDTO;
import com.loja.sistema.produto.dto.response.ProdutoResponseDTO;
import com.loja.sistema.produto.dto.response.ProdutoResumoDTO;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    @Mapping(target = "id", ignore = true)
    Produto toEntity(ProdutoRequestDTO produtoDTO);

    ProdutoResponseDTO toResponseDTO(Produto produto);

    ProdutoResumoDTO toResumoDTO(Produto produto);

    void atualizarProduto(ProdutoAtualizacaoDTO produtoAtualizacaoDTO, @MappingTarget Produto produto);
}
