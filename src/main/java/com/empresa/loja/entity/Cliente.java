package com.empresa.loja.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não deve ficar em branco")
    @Size(min = 3, max = 100, message = "O nome deve conter pelo menos três caracteres e no máximo 100")
    private String nome;

    @NotBlank 
    @Email(message = "Confirme que o email é válido")
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "O cpf não deve ficar em branco")
    @CPF(message = "Digite um CPF válido")
    @Column(unique = true, nullable = false)
    private String cpf;
    
    @NotBlank(message = "O telefone não pode ficar em branco")
    private String telefone;
    
    private LocalDateTime dataCadastro;
    
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
    
    public Cliente(){}

    public Cliente(String nome, String email, String cpf, String telefone, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataCadastro = LocalDateTime.now();
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    
    
}
