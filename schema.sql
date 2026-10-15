-- Modelo de dados Oracle SQL para uma futura versão com banco de dados
CREATE TABLE posto (
    id_posto   NUMBER PRIMARY KEY,
    nome       VARCHAR2(100) NOT NULL,
    cidade     VARCHAR2(80)  NOT NULL
);

CREATE TABLE tanque (
    id_tanque                NUMBER PRIMARY KEY,
    id_posto                 NUMBER NOT NULL REFERENCES posto (id_posto),
    tipo_combustivel         VARCHAR2(30) NOT NULL,
    capacidade_litros        NUMBER(10,2) NOT NULL,
    estoque_litros           NUMBER(10,2) NOT NULL,
    consumo_medio_diario     NUMBER(10,2) NOT NULL,
    CONSTRAINT ck_estoque CHECK (estoque_litros >= 0 AND estoque_litros <= capacidade_litros)
);

CREATE TABLE movimentacao (
    id_movimentacao  NUMBER PRIMARY KEY,
    id_tanque        NUMBER NOT NULL REFERENCES tanque (id_tanque),
    tipo             VARCHAR2(10) NOT NULL CHECK (tipo IN ('VENDA', 'RECEBIMENTO')),
    litros           NUMBER(10,2) NOT NULL CHECK (litros > 0),
    data_hora        TIMESTAMP DEFAULT SYSTIMESTAMP
);

-- Tanques com menos de 3 dias de autonomia
SELECT p.nome, p.cidade, t.tipo_combustivel,
       ROUND(t.estoque_litros / t.consumo_medio_diario, 1) AS dias_autonomia
FROM   tanque t
JOIN   posto p ON p.id_posto = t.id_posto
WHERE  t.estoque_litros / t.consumo_medio_diario < 3;
