-- Inserindo redes
INSERT INTO redes_sociais(instagram, twitch, youtube) VALUES
('https://www.instagram.com/fallen', 'https://www.twitch.tv/gafallen', 'https://www.youtube.com/c/Fallen'),
('https://www.instagram.com/kscerato','https://www.twitch.tv/kscerato', 'Não tem Youtube 😭'),
('https://www.instagram.com/yuurihfps','https://www.twitch.tv/yuurih','Não tem Youtube 😭'),
('Não tem Instragram 😭', 'https://www.twitch.tv/molodoy1818', 'Não tem Youtube 😭'),
('https://www.instagram.com/yek1ndar', 'https://www.twitch.tv/yekindar', 'Não tem Youtube 😭'),
('Não tem Instagram 😭', 'https://www.twitch.tv/siddecs','https://youtube.com/@siddecsgo1110?si=cu9DtqUIBSUZUyfM');

-- Inserindo mapa
INSERT INTO mapa_favorito(nome)VALUES
('DUST 2'),
('Nuke'),
('Mirage'),
('Inferno');

-- Inserindo skin
INSERT INTO skin_favorita(arma,nome) VALUES
 ('SSG-08', 'The Dark Knight'),
 ('M4A1-S','Hyper Beast'),
 ('AK-47','Wild Lotus'),
 ('Desert Eagle', 'Blaze'),
 ('Knife', 'Butterfly');

-- Inserindo jogadores
INSERT INTO jogador (nick_name, nome, nascimento, mapa_favorito_id, redes_sociais_id, skin_favorita_id) VALUES
('FalleN','Gabriel Toledo', '1991-05-30', 1, 1, 1),
('KSCERATO','Kaike Silva Cerato', '1999-09-12', 2, 2, 2),
('Yuurih','Yuri Gomes dos Santos Boian', '1999-12-22', 3, 3, 3),
('Molodoy', 'Danil Golubenko', '2005-01-10', 4, 4, 2),
('YEKINDAR', 'Mareks Gaļinskis', '1999-10-04', 1, 5, 4),
('Sidde', 'Sidnei Macedo Pereira Filho', '1997-02-06', 1, 6, 5);
