package com.empresa.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.loja.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}
