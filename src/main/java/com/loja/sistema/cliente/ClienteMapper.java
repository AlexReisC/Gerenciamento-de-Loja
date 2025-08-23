package com.loja.sistema.cliente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.loja.sistema.cliente.dto.request.ClienteAtualizacaoDTO;
import com.loja.sistema.cliente.dto.request.ClienteRequestDTO;
import com.loja.sistema.cliente.dto.response.ClienteResponseDTO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    @Mapping(target = "id", ignore = true)
    Cliente toEntity(ClienteRequestDTO clienteRequestDTO);

    ClienteResponseDTO toResponse(Cliente cliente);
    
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(ClienteAtualizacaoDTO clienteAtualizacaoDTO, @MappingTarget Cliente cliente);
}
