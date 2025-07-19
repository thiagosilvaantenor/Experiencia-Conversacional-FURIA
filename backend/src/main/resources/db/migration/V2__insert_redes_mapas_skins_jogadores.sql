-- Inserindo redes
INSERT INTO redes_sociais(instagram, twitch, youtube) VALUES
    ('https://www.instagram.com/fallen', 'https://www.twitch.tv/gafallen', 'https://www.youtube.com/c/Fallen');
INSERT INTO redes_sociais(instagram, twitch, youtube) VALUES
    ('https://www.instagram.com/kscerato','https://www.twitch.tv/kscerato', 'Não tem Youtube 😭');
INSERT INTO redes_sociais(instagram, twitch, youtube) VALUES
    ('https://www.instagram.com/yuurihfps','https://www.twitch.tv/yuurih','Não tem Youtube 😭');
INSERT INTO redes_sociais(instagram, twitch, youtube) VALUES
    ('Não tem Instragram 😭', 'https://www.twitch.tv/molodoy1818', 'Não tem Youtube 😭');
INSERT INTO redes_sociais(instagram, twitch, youtube) VALUES
    ('https://www.instagram.com/yek1ndar', 'https://www.twitch.tv/yekindar', 'Não tem Youtube 😭');
INSERT INTO redes_sociais(instagram, twitch, youtube) VALUES
    ('Não tem Instagram 😭', 'https://www.twitch.tv/siddecs','https://youtube.com/@siddecsgo1110?si=cu9DtqUIBSUZUyfM');

-- Inserindo mapa
INSERT INTO mapa_favorito(nome)VALUES
    ('DUST 2');
INSERT INTO mapa_favorito(nome)VALUES
    ('Nuke');
INSERT INTO mapa_favorito(nome)VALUES
    ('Mirage');
INSERT INTO mapa_favorito(nome)VALUES
    ('Inferno');

-- Inserindo skin
INSERT INTO skin_favorita(arma,nome) VALUES
    ('SSG-08', 'The Dark Knight');
INSERT INTO skin_favorita(arma,nome) VALUES
    ('M4A1-S','Hyper Beast');
INSERT INTO skin_favorita(arma,nome) VALUES
    ('AK-47','Wild Lotus');
INSERT INTO skin_favorita(arma,nome) VALUES
    ('Desert Eagle', 'Blaze');
INSERT INTO skin_favorita(arma,nome) VALUES
    ('Knife', 'Butterfly');

-- Inserindo jogadores
INSERT INTO jogador (nick_name, nome, nascimento, mapa_favorito_id, redes_sociais_id, skin_favorita_id) VALUES
    ('FalleN','Gabriel Toledo', '1991-05-30', 1, 1, 1);
INSERT INTO jogador (nick_name, nome, nascimento, mapa_favorito_id, redes_sociais_id, skin_favorita_id) VALUES
    ('KSCERATO','Kaike Silva Cerato', '1999-09-12', 2, 2, 2);
INSERT INTO jogador (nick_name, nome, nascimento, mapa_favorito_id, redes_sociais_id, skin_favorita_id) VALUES
    ('Yuurih','Yuri Gomes dos Santos Boian', '1999-12-22', 3, 3, 3);
INSERT INTO jogador (nick_name, nome, nascimento, mapa_favorito_id, redes_sociais_id, skin_favorita_id) VALUES
    ('Molodoy', 'Danil Golubenko', '2005-01-10', 4, 4, 2);
INSERT INTO jogador (nick_name, nome, nascimento, mapa_favorito_id, redes_sociais_id, skin_favorita_id) VALUES
    ('YEKINDAR', 'Mareks Gaļinskis', '1999-10-04', 1, 5, 4);
INSERT INTO jogador (nick_name, nome, nascimento, mapa_favorito_id, redes_sociais_id, skin_favorita_id) VALUES
    ('Sidde', 'Sidnei Macedo Pereira Filho', '1997-02-06', 1, 6, 5);
