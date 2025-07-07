package com.empresa.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.empresa.loja.entity.Cliente;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    List<Cliente> findByNomeContaining(String nome);
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCpf(String cpf);
    @Modifying
    @Query("UPDATE Cliente c SET c.nome = :nome, c.email = :email, c.telefone = :telefone WHERE c.id = :id")
    void updateClienteById(@Param("id") Long id, @Param("nome") String nome, @Param("email") String email,
                           @Param("telefone") String telefone);
} 