INSERT INTO roles (id, nome, funcao) values (4, 'ROLE_LEADER', 'Líder');
INSERT INTO roles (id, nome, funcao) values (5, 'ROLE_RH', 'Recursos Humanos');


-- Líder
INSERT INTO roles_permissaos (role_id, permissao_id) values (4, 1);
INSERT INTO roles_permissaos (role_id, permissao_id) values (4, 2);
INSERT INTO roles_permissaos (role_id, permissao_id) values (4, 4);
INSERT INTO roles_permissaos (role_id, permissao_id) values (4, 5);
INSERT INTO roles_permissaos (role_id, permissao_id) values (4, 13);
INSERT INTO roles_permissaos (role_id, permissao_id) values (4, 14);
INSERT INTO roles_permissaos (role_id, permissao_id) values (4, 16);

-- Recursos Humanos

INSERT INTO roles_permissaos (role_id, permissao_id) values (5, 12);
INSERT INTO roles_permissaos (role_id, permissao_id) values (5, 18);

-- gerente
INSERT INTO roles_permissaos (role_id, permissao_id) values (3, 10);