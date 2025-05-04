-- Tabela: redes_sociais
CREATE TABLE redes_sociais (
                               redes_sociais_id INT AUTO_INCREMENT,
                               twitch VARCHAR(255),
                               instagram VARCHAR(255),
                               youtube VARCHAR(255),
                               PRIMARY KEY (redes_sociais_id)
);

-- Tabela: mapa_favorito
CREATE TABLE mapa_favorito (
                               id INT AUTO_INCREMENT,
                               nome VARCHAR(255),
                               PRIMARY KEY (id)
);

-- Tabela: skin_favorita
CREATE TABLE skin_favorita (
                               id INT AUTO_INCREMENT,
                               nome VARCHAR(255),
                               arma VARCHAR(255),
                               PRIMARY KEY (id)
);

-- Tabela: jogador
CREATE TABLE jogador (
                         nick_name VARCHAR(255),
                         nome VARCHAR(255),
                         nascimento DATE,
                         redes_sociais_id INT,
                         mapa_favorito_id INT NOT NULL,
                         skin_favorita_id INT NOT NULL,
                         PRIMARY KEY (nick_name)
);

-- Tabela: sugestao
CREATE TABLE sugestao (
                          sugestao_id BIGINT AUTO_INCREMENT,
                          tipo VARCHAR(255),
                          descricao TEXT,
                          email_usuario VARCHAR(255),
                          PRIMARY KEY (sugestao_id)
);

-- Constraints de chave estrangeira
ALTER TABLE jogador
    ADD CONSTRAINT fk_jogador_redes FOREIGN KEY (redes_sociais_id) REFERENCES redes_sociais(redes_sociais_id),
    ADD CONSTRAINT fk_jogador_mapa FOREIGN KEY (mapa_favorito_id) REFERENCES mapa_favorito(id),
    ADD CONSTRAINT fk_jogador_skin FOREIGN KEY (skin_favorita_id) REFERENCES skin_favorita(id);