-- AJUSTAR USUÁRIO
INSERT INTO usuarios (id, nome, email, username, password) values (1, 'Administrador', 'admin-tendtudobusiness@gmail.com', 'admin','$2a$10$YZw1FJpjsg6.kM1D.ExxxOq5hJWurSaVHnubYTZ4tcv2YQxHEEIa6');

INSERT INTO usuarios_roles (usuario_id, role_id) values (1, 2);

ALTER SEQUENCE usuarios_id_seq RESTART WITH 5;
