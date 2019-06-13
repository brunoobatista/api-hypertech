CREATE TABLE IF NOT EXISTS clientes (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL UNIQUE,
  tipo_pessoa VARCHAR(15),
  cpf_cnpj VARCHAR(20) UNIQUE,
  password VARCHAR(150),
  confirmado BOOL DEFAULT false
);

