CREATE TABLE tipo (
	id BIGSERIAL PRIMARY KEY,
	tipo VARCHAR(40) NOT NULL
);

CREATE TABLE produto (
	id BIGSERIAL PRIMARY KEY,
	nome VARCHAR(40) NOT NULL,
	estoque BIGINT DEFAULT 0 NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
	id_tipo BIGINT NOT NULL,
	FOREIGN KEY(id_tipo) REFERENCES tipo(id)
);

CREATE TABLE fornecedor_produto (
  id_produto BIGINT NOT NULL,
  id_fornecedor BIGINT NOT NULL,
  PRIMARY KEY (id_produto, id_fornecedor),
  FOREIGN KEY (id_produto) REFERENCES produto(id),
  FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id)
);
