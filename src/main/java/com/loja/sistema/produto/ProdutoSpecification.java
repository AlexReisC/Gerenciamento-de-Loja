package com.loja.sistema.produto;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecification {
    public static Specification<Produto> nomeContem(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (nome == null || nome.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<Produto> categoriaIdIgual(Long categoriaId) {
        return (root, query, criteriaBuilder) -> {
            if (categoriaId == null || categoriaId <= 0) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("id_categoria"), categoriaId);
        };
    }

    public static Specification<Produto> precoMaiorOuIgual(BigDecimal precoMin) {
        return (root, query, criteriaBuilder) -> {
            if (precoMin == null || precoMin.compareTo(BigDecimal.ZERO) <= 0) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), precoMin);
        };
    }

    public static Specification<Produto> precoMenorOuIgual(BigDecimal precoMax) {
        return (root, query, criteriaBuilder) -> {
            if (precoMax == null || precoMax.compareTo(BigDecimal.ZERO) <= 0) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("preco"), precoMax);
        };
    }
}
