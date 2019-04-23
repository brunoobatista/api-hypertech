CREATE TABLE IF NOT EXISTS estado (
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(40) NOT NULL,
  uf VARCHAR(3) NOT NULL
);

CREATE TABLE IF NOT EXISTS cidade (
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(40) NOT NULL,
  estado_id BIGINT NOT NULL,
  FOREIGN KEY(estado_id) REFERENCES estado(id)
);

CREATE TABLE IF NOT EXISTS fornecedor (
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(40) NOT NULL,
  nome_fantasia VARCHAR(60) NOT NULL,
  cpf_cnpj VARCHAR(20),
  cidade_id BIGINT NOT NULL,
  endereco VARCHAR(60),
  numero VARCHAR(20),
  complemento VARCHAR(60),
  telefone VARCHAR(30),
  telefone_op VARCHAR(30),
  FOREIGN KEY(cidade_id) REFERENCES cidade(id)
);
