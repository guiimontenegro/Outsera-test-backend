# Golden Raspberry Awards API

Este projeto é uma API Spring Boot para gerenciar dados de filmes premiados com o Golden Raspberry Award. Ele utiliza um banco de dados H2 em memória para testes e desenvolvimento.

# Tecnologias

Java 17+

Spring Boot 3

Spring Data JPA

H2 Database (em memória)

# Como rodar

Clone o repositório:

git clone https://seu-repositorio.git](https://github.com/guiimontenegro/Outsera-test-backend.git
cd seu-projeto

Rode o projeto usando Maven ou Gradle:

./mvnw spring-boot:run

# ou

./gradlew bootRun

O projeto vai iniciar em http://localhost:8080.

Acessando o H2 Console

O H2 Console permite visualizar os dados carregados em memória.

URL do console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:raspberrydb

Usuário: sa

Senha: (em branco)

Obs: Certifique-se de que o application.yml contém a configuração para habilitar o console do H2:

spring:
h2:
console:
enabled: true
path: /h2-console

Endpoints

/movies → Listar todos os filmes

/movies/{id} → Consultar filme por ID

/movies (POST) → Adicionar um novo filme
