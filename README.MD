# Sistema de Gerenciamento de Livros - Biblioteca

Este é um projeto em Java para gerenciar um sistema de biblioteca utilizando sockets para comunicação entre cliente e servidor.

## Funcionalidades

- Listagem de livros disponíveis na biblioteca.
- Cadastro de novos livros.
- Aluguel e devolução de livros.

## Estrutura do Projeto

- *Livro.java:* Representa a classe Livro com os atributos necessários (autor, nome, gênero e número de exemplares).
- *Servidor.java:* Implementa o servidor que controla as operações de gerenciamento de livros.
- *Cliente.java:* Implementa o cliente que interage com o servidor via socket.
- *livros.json:* Arquivo JSON utilizado como "base de dados" para armazenar os livros da biblioteca.

## Pré-requisitos

- Java 17 instalado no sistema.
- IDE Java (por exemplo, IntelliJ IDEA, Eclipse) para abrir e executar o projeto.

## Como Usar

1. Clone o repositório para sua máquina local.
2. Abra o projeto em sua IDE Java.
3. Execute Servidor.java para iniciar o servidor.
4. Execute Cliente.java para interagir com o sistema como cliente.

## Exemplo de Arquivo JSON (livros.json)

```json
{
  "livros": [
    {
      "autor": "Marco Aurélio",
      "nome": "Meditações",
      "genero": "Filosofia",
      "numExemplares": 4
    },
    {
      "autor": "Jane Austen",
      "nome": "Orgulho e Preconceito",
      "genero": "Romance",
      "numExemplares": 4
    },
    {
      "autor": "David Goggins",
      "nome": "Nada Pode Me Ferir",
      "genero": "Autoajuda",
      "numExemplares": 2
    },
    {
      "autor": "H.G. Wells",
      "nome": "O Homem Invisível",
      "genero": "Ficção Científica",
      "numExemplares": 1
    }
  ]
}