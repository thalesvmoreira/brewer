INSERT INTO permissao VALUES (1, 'CADASTRAR_CIDADE');
INSERT INTO permissao VALUES (2, 'CADASTRAR_USUARIO');

INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (1, 2);

INSERT INTO usuario_grupo (id_usuario, id_grupo) VALUES (
    (SELECT id FROM usuario WHERE email = 'admin@brewer.com'), 1);