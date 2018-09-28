CREATE TABLE usuario (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL UNIQUE,
  username VARCHAR(150) NOT NULL UNIQUE,
  password VARCHAR(150) NOT NULL
);

CREATE TABLE permissao (
  id BIGSERIAL PRIMARY KEY ,
  descricao VARCHAR(50) NOT NULL
);

CREATE TABLE usuario_permissao (
  id_usuario BIGINT NOT NULL,
  id_permissao BIGINT NOT NULL,
  PRIMARY KEY (id_usuario, id_permissao),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id),
  FOREIGN KEY (id_permissao) REFERENCES permissao(id)
);

INSERT INTO usuario (id, nome, email, username, password) values (1, 'Administrador', 'admin@algamoney.com', 'admin','$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario (id, nome, email, username, password) values (2, 'Maria Silva', 'maria@algamoney.com', 'maria','$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permissao (id, descricao) values (1, 'ROLE_CADASTRAR_TIPO');
INSERT INTO permissao (id, descricao) values (2, 'ROLE_PESQUISAR_TIPO');

INSERT INTO permissao (id, descricao) values (3, 'ROLE_CADASTRAR_PRODUTO');
INSERT INTO permissao (id, descricao) values (4, 'ROLE_REMOVER_PRODUTO');
INSERT INTO permissao (id, descricao) values (5, 'ROLE_PESQUISAR_PRODUTO');


-- admin
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 1);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 3);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 4);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 5);

-- maria
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 5);