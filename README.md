# Documentação do Projeto: API REST para Sistema de Gerenciamento de Loja

## Visão Geral

Este projeto consiste em uma API REST desenvolvida para gerenciar operações de uma loja, incluindo cadastro de produtos, gerenciamento de estoque, controle de vendas e administração de clientes. A API foi construída utilizando práticas modernas de desenvolvimento, visando escalabilidade, segurança e facilidade de manutenção.

## Estrutura do Projeto

- **Pacotes Principais:**
    - `controller`: Contém os controladores responsáveis pelo mapeamento dos endpoints REST.
    - `service`: Implementa a lógica de negócio da aplicação.
    - `repository`: Responsável pela comunicação com o banco de dados.
    - `entity`: Define as entidades do domínio, como Produto, Cliente, Pedido, etc.
    - `dto`: Objetos de transferência de dados para comunicação entre camadas.
    - `exception`: Gerenciamento centralizado de exceções e erros da API.
    - `config`: Configurações gerais do projeto, como segurança, CORS, etc.

## Endpoints Principais

- **Produtos**
    - `GET /produtos`: Lista todos os produtos cadastrados.
    - `GET /produtos/{id}`: Consulta detalhes de um produto específico.
    - `POST /produtos`: Cadastra um novo produto.
    - `PUT /produtos/{id}`: Atualiza informações de um produto.
    - `DELETE /produtos/{id}`: Remove um produto do sistema.

- **Clientes**
    - `GET /clientes`: Lista todos os clientes.
    - `GET /clientes/{id}`: Consulta detalhes de um cliente.
    - `POST /clientes`: Cadastra um novo cliente.
    - `PUT /clientes/{id}`: Atualiza informações de um cliente.
    - `DELETE /clientes/{id}`: Remove um cliente do sistema.

- **Pedidos/Vendas**
    - `GET /pedidos`: Lista todos os pedidos realizados.
    - `GET /pedidos/{id}`: Consulta detalhes de um pedido.
    - `POST /pedidos`: Realiza um novo pedido/venda.
    - `PUT /pedidos/{id}`: Atualiza informações de um pedido.
    - `DELETE /pedidos/{id}`: Cancela um pedido.

- **Estoque**
    - `GET /estoque`: Consulta o estoque atual de produtos.
    - `PUT /estoque/{produtoId}`: Atualiza a quantidade em estoque de um produto.

## Dependências Principais

- **Spring Boot**: Framework principal para desenvolvimento da API.
- **Spring Data JPA**: Abstração para persistência e consultas ao banco de dados.
- **Spring Web**: Suporte à construção de aplicações web e RESTful.
- **Spring Security**: Implementação de autenticação e autorização.
- **Banco de Dados**: Pode ser utilizado H2, PostgreSQL (**atualmente este**), MySQL, etc.

## Observações

- A API segue princípios RESTful, utilizando os métodos HTTP adequados para cada operação.
- As respostas seguem o padrão JSON.
- O tratamento de erros é centralizado, retornando mensagens claras e status HTTP apropriados.
- O projeto está estruturado para facilitar a manutenção e a adição de novas funcionalidades.

---