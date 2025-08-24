# Gerenciamento de Loja

Este projeto é um sistema de gerenciamento para lojas, desenvolvido em Java com Spring Boot. Permite o controle de categorias, clientes, produtos, pedidos e usuários, oferecendo uma API REST para integração e automação de processos comerciais.

## Funcionalidades
- Cadastro, edição e exclusão de categorias, clientes, produtos e usuários
- Gerenciamento de pedidos e itens do pedido
- Validação e tratamento de exceções customizadas
- Mapeamento de entidades e DTOs
- Filtros e especificações para consultas avançadas

## Estrutura do Projeto
```
src/
	main/
		java/com/loja/sistema/
			categoria/
			cliente/
			dtos/
			exception/
			pedido/
			produto/
			usuario/
		resources/
			application.properties
			static/
			templates/
	test/
		java/com/loja/sistema/
```

## Instalação e Execução
- Clone o repositório `git clone github.com/AlexReisC/Gerenciamento-de-Loja`
- Navegue até o diretório do projeto `cd Gerenciamento-de-Loja`
- As configurações do banco de dados e outras propriedades estão em `src/main/resources/application.properties`.
- Execute (dois modos):
  - Via Maven: `./mvnw spring-boot:run`
  - Via Docker Compose: `docker-compose up --build`

## Requisitos
- Java 17+
- Maven 3.8+
- Docker (opcional, para execução com docker-compose)

## ENDPOINTS

### Categorias
- `GET /categorias` — Lista todas as categorias
- `GET /categorias/{id}` — Busca categoria por ID
- `POST /categorias` — Cria uma nova categoria
- `PUT /categorias/{id}` — Atualiza uma categoria
- `DELETE /categorias/{id}` — Remove uma categoria

### Clientes
- `GET /clientes` — Lista todos os clientes
- `GET /clientes/{id}` — Busca cliente por ID
- `POST /clientes` — Cria um novo cliente
- `PUT /clientes/{id}` — Atualiza um cliente
- `DELETE /clientes/{id}` — Remove um cliente

### Produtos
- `GET /produtos` — Lista todos os produtos
- `GET /produtos/{id}` — Busca produto por ID
- `POST /produtos` — Cria um novo produto
- `PUT /produtos/{id}` — Atualiza um produto
- `DELETE /produtos/{id}` — Remove um produto

### Pedidos
- `GET /pedidos` — Lista todos os pedidos
- `GET /pedidos/{id}` — Busca pedido por ID
- `POST /pedidos` — Cria um novo pedido
- `PUT /pedidos/{id}` — Atualiza um pedido
- `DELETE /pedidos/{id}` — Remove um pedido

### Usuários
- `GET /usuarios` — Lista todos os usuários
- `GET /usuarios/{id}` — Busca usuário por ID
- `POST /usuarios` — Cria um novo usuário
- `PUT /usuarios/{id}` — Atualiza um usuário
- `DELETE /usuarios/{id}` — Remove um usuário

## Testes
- Os testes unitários estão em `src/test/java/com/loja/sistema/`.
- Para executar:
	 ```shell
	 ./mvnw test
	 ```

## Contribuição
1. Fork este repositório
2. Crie uma branch: `git checkout -b feature/nome-da-feature`
3. Faça commit das suas alterações
4. Envie um pull request

## Autor
- Alex Reis: [LinkedIn](https://www.linkedin.com/in/alex-reis-cavalcante)

## Licença
Este projeto está sob a licença MIT.