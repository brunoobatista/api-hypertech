CREATE TABLE IF NOT EXISTS roles (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(50) NOT NULL,
  funcao VARCHAR(80)
);

DROP TABLE IF EXISTS usuario_permissao CASCADE;
DROP TABLE IF EXISTS permissao CASCADE;

CREATE TABLE IF NOT EXISTS permissao (
  id BIGSERIAL PRIMARY KEY ,
  nome VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles_permissaos (
  permissao_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (permissao_id, role_id),
  FOREIGN KEY (permissao_id) REFERENCES permissao(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS usuarios_roles (
  usuario_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (usuario_id, role_id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Reorganizar id de usuários
UPDATE usuario SET id = 10000+nextval('usuario_id_seq');
ALTER SEQUENCE usuario_id_seq RESTART WITH 1;
UPDATE usuario SET id = nextval('usuario_id_seq');


-- autorizações
INSERT INTO permissao (id, nome) values (1, 'READ_PRODUTO');
INSERT INTO permissao (id, nome) values (2, 'WRITE_PRODUTO');
INSERT INTO permissao (id, nome) values (3, 'FULL_PRODUTO');

INSERT INTO permissao (id, nome) values (4, 'READ_CLIENTE');
INSERT INTO permissao (id, nome) values (5, 'WRITE_CLIENTE');
INSERT INTO permissao (id, nome) values (6, 'FULL_CLIENTE');

INSERT INTO permissao (id, nome) values (7, 'READ_FORNECEDOR');
INSERT INTO permissao (id, nome) values (8, 'WRITE_FORNECEDOR');
INSERT INTO permissao (id, nome) values (9, 'FULL_FORNECEDOR');

INSERT INTO permissao (id, nome) values (10, 'READ_USUARIO');
INSERT INTO permissao (id, nome) values (11, 'WRITE_USUARIO');
INSERT INTO permissao (id, nome) values (12, 'FULL_USUARIO');


INSERT INTO permissao (id, nome) values (13, 'READ_VENDA');
INSERT INTO permissao (id, nome) values (14, 'WRITE_VENDA');
INSERT INTO permissao (id, nome) values (15, 'FULL_VENDA');

INSERT INTO permissao (id, nome) values (16, 'READ_REPORT');
INSERT INTO permissao (id, nome) values (17, 'WRITE_REPORT');
INSERT INTO permissao (id, nome) values (18, 'FULL_REPORT');

INSERT INTO permissao (id, nome) values (100, 'ROOT');



INSERT INTO roles (id, nome, funcao) values (1, 'ROLE_USUARIO', 'Usuário');
INSERT INTO roles (id, nome, funcao) values (2, 'ROLE_ADMIN', 'Administrador');
INSERT INTO roles (id, nome, funcao) values (3, 'ROLE_GERENTE', 'Gerente');
INSERT INTO roles (id, nome, funcao) values (100, 'ROLE_ROOT', 'ROOT');


-- usuario
INSERT INTO roles_permissaos (role_id, permissao_id) values (1, 1);
INSERT INTO roles_permissaos (role_id, permissao_id) values (1, 4);
INSERT INTO roles_permissaos (role_id, permissao_id) values (1, 7);
INSERT INTO roles_permissaos (role_id, permissao_id) values (1, 13);
INSERT INTO roles_permissaos (role_id, permissao_id) values (1, 14);
INSERT INTO roles_permissaos (role_id, permissao_id) values (1, 16);

-- admin
INSERT INTO roles_permissaos (role_id, permissao_id) values (2, 3);
INSERT INTO roles_permissaos (role_id, permissao_id) values (2, 6);
INSERT INTO roles_permissaos (role_id, permissao_id) values (2, 9);
INSERT INTO roles_permissaos (role_id, permissao_id) values (2, 12);
INSERT INTO roles_permissaos (role_id, permissao_id) values (2, 15);
INSERT INTO roles_permissaos (role_id, permissao_id) values (2, 18);

-- gerente
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 1);
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 2);
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 4);
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 5);
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 7);
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 8);
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 13);
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 18);

-- root
INSERT INTO roles_permissaos (role_id, permissao_id) values (100, 100);

-- role para usuario
INSERT INTO usuarios_roles (usuario_id, role_id) values (1, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) values (2, 1);