package com.empresa.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empresa.loja.entity.Cliente;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    List<Cliente> findByNome(String nome);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCpf(String cpf);
} 