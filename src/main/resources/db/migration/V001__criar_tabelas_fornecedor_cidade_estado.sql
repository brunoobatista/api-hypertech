CREATE TABLE estado (
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(40) NOT NULL,
  uf VARCHAR(3) NOT NULL
);

CREATE TABLE cidade (
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(40) NOT NULL,
  id_estado BIGINT NOT NULL,
  FOREIGN KEY(id_estado) REFERENCES estado(id)
);

CREATE TABLE fornecedor (
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(40) NOT NULL,
  nome_fantasia VARCHAR(60) NOT NULL,
  cpf_cnpj VARCHAR(20),
  id_cidade BIGINT NOT NULL,
  endereco VARCHAR(60),
  numero VARCHAR(20),
  complemento VARCHAR(60),
  telefone VARCHAR(30),
  telefone_op VARCHAR(30),
  FOREIGN KEY(id_cidade) REFERENCES cidade(id)
);
