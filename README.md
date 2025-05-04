# Experiência Conversacional FURIA

`Desafio do processo seletivo de assistente de engenheiro de software da Furia.`<br>`Proposta: Challenge #1 - Experiência Conversacional FURIA Nível: Normal Crie um chatbot ou interface conversacional para os fãs do time de CS da FURIA! Pode ser Telegram bot, web chat ou app. Dica: pense como fã. O que você gostaria de ver ali? `


<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white" />
  <img src="https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB" />
 	<img src="https://img.shields.io/badge/Next-black?style=for-the-badge&logo=next.js&logoColor=white" />
  <img src="https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white" />

</div>

# Tecnologias 🛠
- Linguagens: Java em BackEnd, Javascript em FrontEnd
- Banco de dados relacional: MySQL
- ORM: Hibernate
- FrameWork e Dependências:
  - BackEnd em [Spring](https://spring.io/)
    - [Data JPA](https://spring.io/projects/spring-data-jpa)
    - [Lombok](https://projectlombok.org/)
    - [DevTools](https://docs.spring.io/spring-boot/reference/using/devtools.html)
    - [Spring Web](https://mvnrepository.com/artifact/org.springframework/spring-web)
    - [MySQLDriver](https://mvnrepository.com/artifact/com.mysql/mysql-connector-j)
  - FrontEnd com [NextJs](https://nextjs.org/):
    - [React](https://react.dev/)
    - [TaillWindCSS](https://tailwindcss.com/)
- APIs Externas
  - [Twitch API](https://dev.twitch.tv/docs/api/)
 

## 🚀 Como Rodar o Projeto Localmente

### 1. **Crie as credenciais da Twitch**
Para utilizar a API da Twitch, você precisa gerar suas próprias credenciais. Siga os passos abaixo para criar uma conta de desenvolvedor e obter seu `client_id` e `client_secret`:

1. Acesse [Twitch Developer Console](https://dev.twitch.tv/console/apps).
2. Crie uma nova aplicação clicando em "Register Your Application".
3. Salve o `client_id` e `client_secret` gerados.

### 2. **Configuração do Ambiente**
Crie um arquivo `application.properties` em [`/src/main/resources/`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/tree/main/backend/src/main/resources). Você pode usar este arquivo: [`/src/main/resources/application-example.properties`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/blob/main/backend/src/main/resources/application-example.properties), ele já esta no mesmo diretório basta trocar o nome e substitua pelas suas credenciais da Twitch e demais informações:
Para usuario e senha do banco de dados, nas linhas 4 e 5, foi utilizado de variaveis de ambiente com os valores de usuario(`DB_USER`) e senha(`DB_PASSWORD`) do banco, portanto será necessário criar essas variaveis: 
  
![image](https://github.com/user-attachments/assets/b8508388-c9ac-4f99-a195-b09aa476709a)
  
Também vai ser necessário colocar o número da porta do MySQL na linha 3, troque o `3306` pela porta em que esta o seu MySQL, nessa mesma linha garante que a database `chatfuriacs` seja criada ao rodar o projeto
  
![image](https://github.com/user-attachments/assets/be0bb536-0e35-4edf-9148-0defdb757684)

Por fim, você vai colocar `client_id` e `client_secret`, da API da Twich, nas linhas 12 e 13:

![image](https://github.com/user-attachments/assets/3d54c341-2c8a-4a8d-b304-a43b5cc1e9ca)


### 3. **Baixando depêndencias**
- Backend:
  - Com o projeto baixado, e o [Maven](https://maven.apache.org/) instalado, utilize a IDE de sua preferência, baixe as dependencias do projeto com o Maven, no terminal você vai entrar no diretorio: [`/backend/`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/tree/main/backend) usar o comando `mvn install`.
- FrontEnd:
  - Necessario ter o [Node](https://nodejs.org/pt) instalado, abra um terminal dentro do diretório [`/frontend/chatbot-furia/`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/tree/main/frontend/chatbot-furia) e use os seguintes comandos:
    - `npm install`: instala as dependências e cria a pasta /node_modules
    - `npm run dev`: com tudo instalado roda a aplicação, informando em qual porta o frontend está rodando, normalmente o NextJs roda em `http://localhost:3000`
  - A rota do chatbot está em `/chat`, mas eu configurei para o `"/"` redirecionar para a rota do chat, um exemplo: `http://localhost:3000` vai redirecionar para `http://localhost:3000/chat`
 
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
  - PUT (`/nickName`) Colocar no body os atributos que deseja atualizar do jogador, com a exceção do `nickname` pois é o primaryKey dele, exemplo: `{` <br>
     `"nome": "nome novo"` <br>
      `"nascimento": "nova data de nascimento do jogador, em YYYY-MM-DD ex: 1991-05-30",` <br>
      `"twitch": "novo link do canal da twitch do jogador, ex: https://www.twitch.tv/gafallen",` <br>
      `]`

    FrontEnd:
      - Interação com o chatbot usando a API do backend e API da Twitch em `localhost:3000/chat`
      ![image](https://github.com/user-attachments/assets/6ed0f75e-155c-4627-a63d-dcf3b2005bff)



