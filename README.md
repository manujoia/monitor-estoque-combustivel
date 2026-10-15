# Monitor de Estoque de Combustível

Programa em Java (console) que simula o controle de estoque de combustível em postos, com alerta de reabastecimento e geração de pedidos. Projeto pessoal inspirado na operação de abastecimento e logística de combustíveis.

## O que faz
- Lista o estoque por posto e por tanque (gasolina, etanol e diesel), com percentual de ocupação e dias de autonomia.
- Alerta tanques com menos de 3 dias de autonomia.
- Registra vendas e recebimentos, validando estoque insuficiente e capacidade do tanque.
- Gera pedidos de reabastecimento até 90% da capacidade e soma o total de litros.

## Tecnologias
Java 17+, programação orientada a objetos (classes, enum, encapsulamento, tratamento de exceções), coleções e streams.

## Estrutura
- `src/`: código-fonte (`Tanque`, `Posto`, `TipoCombustivel`, `ServicoReabastecimento`, `Main`).
- `schema.sql`: modelo de tabelas em Oracle SQL para uma versão futura com banco de dados (ainda não integrado ao Java).

## Como rodar
cd src
javac -encoding UTF-8 *.java
java Main

## Próximos passos
- Integrar o Java ao Oracle com JDBC usando o `schema.sql`.
- Criar testes unitários com JUnit.
- Expor os dados por uma API REST.
