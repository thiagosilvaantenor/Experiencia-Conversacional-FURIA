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

## 📑 Sumário

- [Experiência Conversacional FURIA](#experiência-conversacional-furia)
- [Tecnologias 🛠](#tecnologias-)
- [🚀 Como Rodar o Projeto Localmente](#-como-rodar-o-projeto-localmente)
  - [1. Crie as credenciais da Twitch](#1-crie-as-credenciais-da-twitch)
  - [2. Configuração do Ambiente](#2-configuração-do-ambiente)
  - [3. Baixando dependências](#3-baixando-dependências)
  - [4. Rodando pela primeira vez](#4-rodando-pela-primeira-vez)
- [Preview](#-preview)
  - [1. BackEnd](#1-backend)
    - [1.1. Interação com o Chat em `/chat`](#11-interação-com-o-chat-em-chat)
    - [1.2. Listagem de Sugestão em `/sugestoes`](#12-listagem-de-sugestão-em-sugestoes)
    - [1.3. Cadastro e listagem de Jogador em `/jogador/`](#13-cadastro-e-listagem-de-jogador-em-jogador)
  - [2. FrontEnd](#2-frontend)
- [Autor](#autor)


## Tecnologias 🛠
- Linguagens: Java em BackEnd, Javascript em FrontEnd
- Banco de dados relacional: MySQL
- ORM: Hibernate
- Migrations: [Flyway](https://mvnrepository.com/artifact/org.flywaydb/flyway-core)
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
2. Crie uma nova aplicação clicando em "Register Your Application / Registre seu aplicativo".
3. Em Nome, coloque um nome unico qualquer, ex: `APIKEYCHATBOT{seuUsuariodaTwitch}`
4. Em URLs de redirecionamento OAuth, coloque `http://localhost:8080/chat`
5. Em categoria coloque `ChatBot`
6. Em tipo de cliente deixe como `Confidencial`
7. Clique em `criar`
8. Salve o `client_id` e `client_secret` gerados.

### 2. **Configuração do Ambiente**
Crie um arquivo `application.properties` em [`/src/main/resources/`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/tree/main/backend/src/main/resources). Você pode usar este arquivo: [`/src/main/resources/application-example.properties`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/blob/main/backend/src/main/resources/application-example.properties), ele já esta no mesmo diretório basta trocar o nome e substitua pelas suas credenciais da Twitch e demais informações:
Para usuario e senha do banco de dados, nas linhas 4 e 5, foi utilizado de variaveis de ambiente com os valores de usuario(`DB_USER`) e senha(`DB_PASSWORD`) do banco, você pode criar essas variaveis, com estes nomes:
  
![image](https://github.com/user-attachments/assets/b8508388-c9ac-4f99-a195-b09aa476709a)

Ou apenas colocar o valor, ex: `spring.datasource.username=username`
  
Também vai ser necessário colocar o número da porta do MySQL na linha 3, troque o `3306` pela porta em que esta o seu MySQL, nessa mesma linha garante que a database `chatfuriacs` seja criada ao rodar o projeto
  
![image](https://github.com/user-attachments/assets/be0bb536-0e35-4edf-9148-0defdb757684)

Por fim, você vai colocar `client_id` e `client_secret`, da API da Twich, nas linhas 12 e 13:

![image](https://github.com/user-attachments/assets/3d54c341-2c8a-4a8d-b304-a43b5cc1e9ca)


### 3. **Baixando dependências**
- Backend:
  - Com o projeto baixado, e o [Maven](https://maven.apache.org/) instalado, utilize a IDE de sua preferência, baixe as dependencias do projeto com o Maven, no terminal você vai entrar no diretorio: [`/backend/`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/tree/main/backend) usar o comando `mvn install`.
- FrontEnd:
  - Necessario ter o [Node](https://nodejs.org/pt) instalado, abra um terminal dentro do diretório [`/frontend/chatbot-furia/`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/tree/main/frontend/chatbot-furia) e use os seguintes comandos:
    - `npm install`: instala as dependências e cria a pasta /node_modules
    - `npm run dev`: com tudo instalado roda a aplicação, informando em qual porta o frontend está rodando, normalmente o NextJs roda em `http://localhost:3000`
  - A rota do chatbot está em `/chat`, mas eu configurei para o `"/"` redirecionar para a rota do chat, um exemplo: `http://localhost:3000` vai redirecionar para `http://localhost:3000/chat`

### 4. **Rodando pela primeira vez**
- Backend:
  - dentro do diretorio: [`/backend/`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/tree/main/backend) usar o comando `mvn spring-boot:run`.
  - Caso ocorra tudo certo, após alguns segundos o resultado no terminal será algo parecido com a imagem abaixo:

    ![image](https://github.com/user-attachments/assets/0645e790-675d-4768-a6f5-883de67a5f59)

  - Quando é rodado o backend pela primeira vez a JPA e o Flyway realizam:
    - Criação da database `chatfuriacs`
    - Criação das tabelas: `redes_sociais`, `mapa_favorito`, `skin_favorita`, `jogador` e `sugestao`
    - Inserção de dados dos jogadores atuais da Furia incluindo as redesSociais, mapas favoritos e skins favoritas
  - Caso queira testar apenas o backend é possivel utiliziar Postman ou Insominia
- FrontEnd:
  -  Com as dependências instaladas, basta usar `npm run dev`, dentro do diretório [`/frontend/chatbot-furia/`](https://github.com/thiagosilvaantenor/Experiencia-Conversacional-FURIA/tree/main/frontend/chatbot-furia)
  -  Caso ocorra tudo certo o resultado no terminal será algo parecido com a imagem abaixo:

    ![image](https://github.com/user-attachments/assets/6f2d4c95-f76a-42de-b269-e5c655942a73)
    ![image](https://github.com/user-attachments/assets/7608a84a-7640-49df-aff0-88f13f825a80)

  -  Abra o navegador de sua preferência e entre em no endereço informado no terminal em `local`, seguindo a imagem acima eu devo entrar em `http://localhost:3030` isso irá te redirecionar para a tela de interação com o chatbot em `/chat`
    
    ![image](https://github.com/user-attachments/assets/18e750c0-66fd-4c5b-8d66-f514258d181b)

  -  Aqui é necessário o backend estar rodando também, se as opções, botões verdes, não aparecerem é por que o backend não esta rodando

## 🔎 Preview
### 1. **BackEnd**:

#### 1.1. **Interação com o Chat em `/chat`**
  - GET : Exibe o menu com as opções cadastradas
    ![image](https://github.com/user-attachments/assets/11e69199-da24-4c23-9f53-a1ca72756a76)

  - POST: Colocar no body `{` <br> `"opcao":"numero da opcao desejada"` <br> `}`
    - POST{"opcao" : "1"} : 
      Utiliza da API da Twich para verificar, enviando o canal da twitch de cada jogador cadastrado, se um ou mais estão em live. Se pelo menos 1 jogador estiver, retorna o nome dele, o link do canal da twitch e qual jogo estão jogando, caso nenhum esteja, retorna os nomes do jogadores e os links do canal da twitch de cada um. Exemplo:
      ![image](https://github.com/user-attachments/assets/03497536-e136-4334-b5fe-a0c8101c72c1)
    - POST{"opcao" : "2"} : Retorna as redes sociais dos jogadores cadastrados no banco de dados da API do chat
      ![image](https://github.com/user-attachments/assets/de3ccfb0-20b9-4a71-b491-ba79ccf3fa64)

    - POST{"opcao" : "3"} : Retorna as skins e mapas favoritos de cada jogador
    ![image](https://github.com/user-attachments/assets/1f654ab9-f3e2-4156-9a53-8ee1443521ba)

    - POST `{` <br>
    `"tipo": "tipo de sugestão, ex: Melhoria",` <br>
    `"descricao": "colocar aqui a sua suguestão",` <br>
    `"emailUsuario": "colocar aqui seu email para caso necessario entrar em contato para entender melhor sua sugestão"` <br>
  `}`  : Salva uma sugestão no banco de dados com os dados enviados no body
    ![image](https://github.com/user-attachments/assets/485ce52d-0e94-41ca-85cd-e8fc87ac5ad7)
  
#### 1.2. **Listagem de Sugestão em `/sugestoes`**
  - GET: Exibe todos as sugestões cadastrados
    ![image](https://github.com/user-attachments/assets/216a9bd3-54b2-456a-90c3-76fa890570e5)

  - GET (`/sugestoes/id`): Exibe a sugestão que tenha o id informado
    ![image](https://github.com/user-attachments/assets/5a803366-a28d-4fd2-a5ed-dee9ec701a67)

#### 1.3. **Cadastro e listagem de Jogador em `/jogador/`**
  - GET: Exibe todos os jogadores cadastrados
  ![image](https://github.com/user-attachments/assets/6fb4f682-545d-44ce-a693-942e62475585)

  - GET (`/jogador/nickName`): Exibe o jogador daquele nickName(id) especifico
  ![image](https://github.com/user-attachments/assets/16221bfe-eead-4e99-8fd1-ab6739d63bc3)

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
    ![image](https://github.com/user-attachments/assets/9ad17c6e-92b2-48bf-a82e-f8a4038c68e1)

  - PUT (`/nickName`) Colocar no body os atributos que deseja atualizar do jogador, com a exceção do `nickname` pois é o primaryKey dele, exemplo: `{` <br>
     `"nome": "nome novo"` <br>
      `"nascimento": "nova data de nascimento do jogador, em YYYY-MM-DD ex: 1991-05-30",` <br>
      `"twitch": "novo link do canal da twitch do jogador, ex: https://www.twitch.tv/gafallen",` <br>
      `]`
    ![image](https://github.com/user-attachments/assets/e398baa7-1ada-4049-b292-bd636bc54dca)

### 2. **FrontEnd**:
  - Interação com o chatbot usando a API do backend e API da Twitch em `localhost:3000/chat`
   ![image](https://github.com/user-attachments/assets/6ed0f75e-155c-4627-a63d-dcf3b2005bff)
    - Caso selecione a primeira opção, `Jogadores que estão em live agora`:
   ![ChatFuria-—-Mozilla-Firefox-2025-05-04-13-07-28](https://github.com/user-attachments/assets/8598875d-0ddd-4500-a2b8-7fa347d00883)

    - Caso selecione a segunda opção, `Redes sociais dos jogadores`:
    ![ChatFuria-—-Mozilla-Firefox-2025-05-04-13-16-38](https://github.com/user-attachments/assets/924da967-160b-46a5-ac1e-574715b83e3a)

    - Caso selecione a terceira opção, `Mapas e skins favoritos`:
    ![ChatFuria-—-Mozilla-Firefox-2025-05-04-13-17-17](https://github.com/user-attachments/assets/75f544de-8d71-4777-8b2c-5d9173bb1871)

    - Caso selecione a quarta opção, `Mandar suguestão para o chat`: 
    ![ChatFuria-—-Mozilla-Firefox-2025-05-04-13-17-59](https://github.com/user-attachments/assets/72df4104-b71e-4fc4-a441-ef618ddd2064)

## Autor

<div align="center">
<a href="https://www.linkedin.com/in/thiago-antenor/">
<img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/99970279?v=4" width="100px;" alt="foto do autor"/>
 <br />
 <sub><b>Thiago Silva Antenor</b></sub></a> <a href="https://www.linkedin.com/in/thiago-antenor/" title="Linkedin"> 🧑🏾‍💻</a>


Feito por Thiago Silva Antenor 👨🏾‍💻 Entre em contato!

[![Linkedin Badge](https://img.shields.io/badge/-Thiago-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/thiago-antenor/)](https://www.linkedin.com/in/thiago-antenor/) 
[![Gmail Badge](https://img.shields.io/badge/-thiagoantenor31@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:thiagoantenor31.com)](mailto:thiagoantenor31.com)
</div>
