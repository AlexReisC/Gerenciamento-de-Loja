package com.loja.sistema.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
    boolean existsByCpf(String cpf);
    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE c.email = :email AND c.id != :id")
    Optional<Cliente> findByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
}
