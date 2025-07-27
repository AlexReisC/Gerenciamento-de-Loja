package com.empresa.loja.cliente;

import com.empresa.loja.dtos.request.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "id", ignore = true)
    Cliente toEntity(ClienteDTO dto);

    ClienteDTO toDTO(Cliente cliente);
}
