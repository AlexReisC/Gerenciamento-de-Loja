package com.empresa.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.loja.entity.Endereco;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    @Modifying
    @Query("UPDATE Endereco e SET e.cep = :cep, e.logradouro = :logradouro, " +
            "e.bairro = :bairro, e.cidade = :cidade, e.estado = :estado WHERE e.id = :id")
    void updateEnderecoById(@Param("cep") String cep, @Param("logradouro") String logradouro, @Param("bairro") String bairro,
                           @Param("cidade") String cidade, @Param("estado") String estado);
}
