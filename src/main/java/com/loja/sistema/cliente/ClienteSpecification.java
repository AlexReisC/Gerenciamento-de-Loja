package com.loja.sistema.cliente;


import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ClienteSpecification {
    public static Specification<Cliente> nomeContem(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (nome != null && !nome.isEmpty()){
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
            }
            return null;
        };
    }

    public static Specification<Cliente> dataCadastroIgual(LocalDateTime data) {
        return (root, query, criteriaBuilder) ->
            data == null ? null : criteriaBuilder.equal(root.get("data_cadastro"), data);
    }

}
