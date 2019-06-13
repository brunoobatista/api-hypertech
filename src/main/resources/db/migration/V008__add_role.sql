INSERT INTO roles (id, nome, funcao) values (5, 'ROLE_LEADER', 'Líder');
INSERT INTO roles (id, nome, funcao) values (6, 'ROLE_RH', 'Recursos Humanos');


-- Líder
INSERT INTO roles_permissaos (role_id, permissao_id) values (5, 1);
INSERT INTO roles_permissaos (role_id, permissao_id) values (5, 2);
INSERT INTO roles_permissaos (role_id, permissao_id) values (5, 4);
INSERT INTO roles_permissaos (role_id, permissao_id) values (5, 5);
INSERT INTO roles_permissaos (role_id, permissao_id) values (5, 13);
INSERT INTO roles_permissaos (role_id, permissao_id) values (5, 14);
INSERT INTO roles_permissaos (role_id, permissao_id) values (5, 16);

-- Recursos Humanos

INSERT INTO roles_permissaos (role_id, permissao_id) values (6, 12);
INSERT INTO roles_permissaos (role_id, permissao_id) values (6, 18);

-- gerente
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 10);