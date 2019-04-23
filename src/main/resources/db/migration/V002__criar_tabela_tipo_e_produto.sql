CREATE TABLE IF NOT EXISTS tipo (
	id BIGSERIAL PRIMARY KEY,
	tipo VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS produto (
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(40) NOT NULL,
	estoque BIGINT DEFAULT 0 NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	tipo_id BIGINT NOT NULL,
	FOREIGN KEY(tipo_id) REFERENCES tipo(id)
);

CREATE TABLE IF NOT EXISTS fornecedor_produto (
  produto_id BIGINT NOT NULL,
  fornecedor_id BIGINT NOT NULL,
  PRIMARY KEY (produto_id, fornecedor_id),
  FOREIGN KEY (produto_id) REFERENCES produto(id),
  FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id)
);
