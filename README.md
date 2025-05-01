# Experiência Conversacional FURIA

`Desafio do processo seletivo de assistente de engenheiro de software da Furia, criar um ChatBot/ Interface Conversacional para interagir com os fans do time de CounterStrike da Furia`

# Tecnologias
- Linguagem: Java
- Banco de dados relacional: MySQL
- ORM: Hibernate
- FrameWork e Dependências: Spring
  
  - Data JPA
  - Lombok
  - DevTools
  - Spring Web
  - MySQLDriver
 

# Para Rodar:
Com o projeto baixado, e o [Maven](https://maven.apache.org/) instalado, utilize a IDE de sua preferência, baixe as dependencias do projeto com o Maven.
Vai ser necessário atualizar informações do [`/src/main/resources/application.propperties`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/blob/main/src/main/resources/application.properties)

Para usuario e senha do banco de dados, nas linhas 4 e 5: 

![image](https://github.com/user-attachments/assets/b8508388-c9ac-4f99-a195-b09aa476709a)

Também vai ser necessário colocar o número da porta do MySQL na linha 3, troque o `3306` pela porta em que esta o seu MySQL ![image](https://github.com/user-attachments/assets/be0bb536-0e35-4edf-9148-0defdb757684)
<br> Pronto, pode rodar o projeto!<br>
Caso queira testar o backend utilize Postman ou Insomnia.

# Status
🚧Em construção🚧

O que já esta pronto:

BackEnd:

- Interação com o Chat em `/chat`
  - GET : Exibe o menu com as opções: 1 - Jogadores que estão em live agora, 2 - Redes sociais dos jogadores, 3 - Mapas e skins favoritas dos jogadores, 4 - Mandar uma suguestão para o chat
  - POST: Colocar no body `{` <br> `"opcao":"numero da opcao desejada"` <br> `}`
  - POST para sugestão, além da opção 4 colocar no body:
  `{` <br>
    `"tipo": "tipo de sugestão, ex: Melhoria",` <br>
    `"descricao": "colocar aqui a sua suguestão",` <br>
    `"emailUsuario": "colocar aqui seu email para caso necessario entrar em contato para entender melhor sua sugestão"` <br>
  `}` 
- Listagem de Sugestão em `/sugestoes`
  - GET: Exibe todos as sugestões cadastrados
  - GET (`/sugestoes/id`): Exibe o jogador daquele nickName(id) especifico
- Cadastro e listagem de Jogador em `/jogador/`
  - GET: Exibe todos os jogadores cadastrados
  - GET (`/jogador/nickName`): Exibe o jogador daquele nickName(id) especifico
  - POST Cadastra o jogador com os seguintes dados do body: `{`
   <br> `"nickName": "nick do jogador, ex: FalleN",` <br>
    `"nome": "nome do jogador, ex: Gabriel Toledo",` <br>
    `"nascimento": "data de nascimento do jogador, em YYYY-MM-DD ex: 1991-05-30",` <br>
    `"twitch": "link do canal da twitch do jogador, ex: https://www.twitch.tv/gafallen",` <br>
    `"instagram": "link do instagram do jogador, ex: https://www.instagram.com/fallen",` <br>
    `"youtube": "link do canal do youtube do jogador, ex: https://www.youtube.com/c/Fallen",` <br>
    `"nomeMapa": "nome do mapa favorito do jogador, ex: DUST 2",` <br>
    `"nomeSkin": "nome da skin favorita do jogador, ex: The Dark Knight",` <br>
    `"arma": "nome da arma que pertence a skin favorita do jogador, ex: SSG 08"` <br> `} `
