'use client'
import { useEffect, useState } from 'react'
import Opcao from "@/app/components/Opcao";

export default function ChatForm() {
  const [mensagens, setMensagens] = useState([
    { texto: 'Boas vindas ao Bot da Furia CS', remetente: 'bot', link:null }
  ])
  //Variaveis com estado, para lidar com a opção selecionada, input de texto do usuário, menu da API do Chat e tempo de carregamento de dados do backend
  const [opcaoSelecionada, setOpcaoSelecionada] = useState('');
  const [descricaoSugestao, setDescricaoSugestao] = useState('');
  const [emailUsuario, setEmailUsuario] = useState('');
  const [opcoesMenu, setOpcoesMenu] = useState({});
  const [tipoSugestao, setTipoSugestao] = useState('melhoria');
  const [carregando, setCarregando] = useState(false);

  // Lista para simulação de torcida:
  const frasesTorcida = {
    '1': [
      "VAI ROLAR LIVE AGORA? 👀",
      "QUEM TÁ ON? 🔴",
      "TÔ COM A FÚRIA! 🔥",
      "QUERO VER O ART APRONTANDO! 🧨"
    ],
    '2': [
      "QUERO SEGUIR TODO MUNDO! 🔥",
      "MANDA O INSTA DO GUERRI! 💬",
      "QUEM TÁ FAZENDO DANCINHA NO TIKTOK? 😂"
    ],
    '3': [
      "MIRAGE É NOSSA CASA! 🏠🔥",
      "A SKIN DO KSCERATO É BRABA DEMAIS 😎",
      "QUERO SABER QUEM CURTE DUST 2! 🏜"
    ],
    '4': [
      "TÁ NA HORA DE MELHORAR ESSE BOT HEIN 😅",
      "DEIXA EU MANDA IDEIAS AÍ GALERA! 💡",
      "FAZ UM BOT QUE IMITA O BAD FALLEN! 😂"
    ]
  };
  
  //Realiza a requisição GET(/chat) para o backend para exibir o menu com as opções disponiveis do backend
  useEffect(() => {
    const fetchMenu = async () => {
      const res = await fetch('http://localhost:8080/chat')
      const data = await res.json()
      setOpcoesMenu(data)
    }
    fetchMenu()
  }, [])

  //Quando o usuário seleciona uma opção é acionada
  const enviarOpcao = async (valor) => {
    //Pega a opção escolhida e exibe a mensagem como usuário com essa opção
    setOpcaoSelecionada(valor);

    //Sorteia a frase de torcida da lista de torcida simulada
    if (frasesTorcida[valor]) {
      const frases = frasesTorcida[valor];
      const aleatoria = frases[Math.floor(Math.random() * frases.length)];
      setMensagens(prev => [...prev, { texto: aleatoria, remetente: 'torcida' }]);
    }//Caso entre uma nova opção sem frase de torcida cadastrada, exibe apenas a opção e o valor dela 
    else {
      setMensagens(prev => [...prev, { texto: `Opção ${valor}`, remetente: 'usuario' }]);
    }
    
    //Altera o estado de carregando para exibir para o usuário
    setCarregando(true);

    

    // Se for qualquer outra opção, cria o json para a requisição o valor da opção escolhida
    const payload = { opcao: valor }
    //Envia o json para POST /chat no backend
    const res = await fetch('http://localhost:8080/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
    //Recebe a resposta da requisição
    const dados = await res.json();
    
   switch(valor){
    case "1":
      verificaLive(dados);
      break;
    case "2":
      retornaRedesSociais(dados);
      break;
    case "3":
      retornaSkinMapa(dados);
      break;
    case "4":
      // Se for a opção 4, apenas mostra o input, sem enviar nada
      return;
    default:
      // Para outras opções, simplesmente mostra a mensagem
      setCarregando(false);
      setMensagens(prev => [...prev, { texto: dados.mensagem, remetente: 'bot' }]);
    }
    
  }

   //caso seja escolhida a opção 1
  const verificaLive = (dados) => {
    //Recebe o json(dados) e verifica se alguem está emLive
    if (dados.emLive) {
      setTimeout(() => {
        setCarregando(false); 
        dados.emLive.forEach(jogador => {
        const { link } = extrairTextoELink(jogador.twitch);
        setMensagens(prev => [
          ...prev,
          {
            texto: `${jogador.jogador} está em live agora 🔥🔥 e esta jogando ${jogador.jogando}.\nAssista em`,
            link: link,  
            remetente: "bot",
          }
        ]);
      }, 1000);
      });
    }//Caso ninguem estiver emLive, exibe que ninguem está em live e o canal da twitch dos jogadores
    else{
      setMensagens(prev => [
        ...prev,
        {
          texto:dados.mensagem, remetente: "bot"
        }
    ]);
    setTimeout(() => {
      setCarregando(false); 
      dados.canais.forEach(canal => {
        const { link } = extrairTextoELink(canal.twitch);
        setMensagens( prev => [
        ...prev, {
          texto: `Siga ${canal.jogador} na Twitch:`,
          link: link,
          remetente: "bot",
        }
      ]);
    }, 500);
    });
  } 
  }
 //Caso seja escolhida a opção 2
  const retornaRedesSociais = (dados) => {
      //Recebe o json(dados) e retorna as redes sociais dos jogadores
      dados.canais.forEach(canal => {
        //Pra cada canal, eu vou extrair o link usando a função abaixo, e dela eu vou pegar apenas o link
        //const { link } = extrairTextoELink(canal.twitch);
        setTimeout(() => {
          setCarregando(false); 
          setMensagens( prev => [
            ...prev, {
              texto: `Jogador: ${canal.jogador}\nCanal da Twitch 🎮:`,
              link: extrairTextoELink(canal.twitch).link,
              remetente: "bot"
            }
          ])
        }, 500);
        setCarregando(true);
        //const { link } = extrairTextoELink(canal.youtube);
        setTimeout(() => {
          setCarregando(false); 
          setMensagens( prev => [
            ...prev, {
              texto: `Jogador: ${canal.jogador}\nCanal do Youtube ▶:`,
              link: extrairTextoELink(canal.youtube).link,
              remetente: "bot"
            }
          ])
        }, 500);
        setCarregando(true);
        //const { instagram } = extrairTextoELink(canal.instagram);
        setTimeout(() => {
          setCarregando(false); 
          setMensagens( prev => [
            ...prev, {
              texto: `Jogador: ${canal.jogador}\nInstagram 📸:`,
              link: extrairTextoELink(canal.instagram).link,
              remetente: "bot"
            }
          ])
        }, 500);
      });
  }

 //caso seja escolhida a opção 3
  const retornaSkinMapa = (dados) => {
    //Extrai do Json(dados) os dados de skin e Mapa favoritos de cada jogador
    
    dados.skins_mapas.forEach(item => {
    setTimeout(() => {
      setCarregando(false);
      setMensagens( prev => [
        ...prev, {
          texto: `Jogador: ${item.jogador}\nSkinFavorita⭐: ${item.skinNome} da ${item.skinArma}🔫\nMapaFavorito🗺: ${item.mapaFavorito}`,
          remetente: "bot"
        }
      ])
    }, 1000);
    });
  }

  //Caso seja escolhida a opção 4, é necessário habilitar o campo de texto para o usuário informar os atributos de sugestão incluindo o email para entrar em contato caso seja necessário
  const enviarTextoLivre = async () => {
    setMensagens(prev => [...prev, { texto: descricaoSugestao, remetente: 'usuario' }])
    

    //Pega os valores recebidos do html que agora estão nas variaveis de estado e constroi o objeto Sugestão a ser salvo
    const payload = {
      opcao: '4',
      tipo: tipoSugestao,
      descricao: descricaoSugestao,
      emailUsuario: emailUsuario
    }
    setCarregando(true);
    //Com os valores recebidos envia a requisição para criar uma sugestão em POST /sugestoes do backend    
    const res = await fetch('http://localhost:8080/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
    //Recebe a confirmação se foi salvo Sugestão no banco de dados
    const dados = await res.json()

    

    //Retira tela de carregamento, altera estados dos atributos de sugestão e exibe mensagem no bot
    setTimeout(() => {
      setCarregando(false);
      setDescricaoSugestao('')
      setEmailUsuario('')
      setOpcaoSelecionada('')
      setMensagens(prev => [...prev, { texto: dados.mensagem, remetente: 'bot' }]);
    }, 1500);

  }
  //Função para lidar com links, vai buscar no texto do json recebido se tem um link, se tiver separa texto de link
  function extrairTextoELink(texto) {
    const regex = /(https?:\/\/[^\s]+)/;
    const partes = texto.split(regex);
  
    if (partes.length > 1) {
      return {
        texto: partes[0].trim(),
        link: partes[1].trim(),
      };
    }
  
    return { texto, link: null };
  } 

  //HTML e CSS do componente Chat
  return (
    <div className="max-w-[55vw] max-h-[35vw] overflow-y-auto mx-auto mt-10 p-5 bg-white/50 rounded-[30px] shadow-xl/30">

      <h2 className="text-center font-bold mb-4">CHAT FURIA CS</h2>

      {/*Mensagens*/}
      <div className="space-y-2 mb-4">
      
        {mensagens.map((msg, i) => (
        //Mapeia cada msg e verifica qual o remetente para adicionar as classes para identificar por cor e por alinhamento as msg do usuario e do bot
          <div key={i} className={`flex ${msg.remetente === 'bot' ? 'justify-start' : 'justify-end'}`}>
            <div className={`text-black text-stro shadow-md/30 border-black p-4 rounded-2xl max-w-sm break-words whitespace-pre-wrap
              ${msg.remetente === 'bot' ? 'bg-blue-700' : 'bg-yellow-200'}`}>
              
              {msg.link ? (
                <>
                  <span>{msg.texto}</span>
                  <br />
                  <a
                    href={msg.link}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="underline bg-blue-700/20 hover:bg-white/50"
                  >
                    {msg.link}
                  </a>
                </>
              ) : (
                msg.texto
              )}
            </div>
          </div>
        ))}

        {/*Quando estiver buscando dados do backend exibe tela de carregamento*/}
        {carregando && (
          <div className="flex justify-start">
            <div className="bg-blue-700 text-white px-4 py-2 rounded-2xl flex items-center space-x-1">
              <span className="w-2 h-2 bg-white rounded-full animate-bounce [animation-delay:0ms]"></span>
              <span className="w-2 h-2 bg-white rounded-full animate-bounce [animation-delay:200ms]"></span>
              <span className="w-2 h-2 bg-white rounded-full animate-bounce [animation-delay:400ms]"></span>
            </div>
          </div>
        )}
      </div>

      {/*Opções
        Para escalabilidade do chat, utilizei de um componente para exibição das opções, o valor de cada opção será pego pelo GET de /chat do backend
        */}

      {opcaoSelecionada !== '4' && (
        <div className="flex flex-wrap gap-2 justify-center mb-4">
          {Object.entries(opcoesMenu).map(([key, value]) => (
            <Opcao
              key={key}
              valor={key}
              nome={value}
              funcao={() => enviarOpcao(key)}
            />
          ))}
        </div>
      )}

      {/* Campo de digitação livre para opção 4 */}
      {opcaoSelecionada === '4' && (
        <div className="flex-col items-center border border-black rounded px-2 py-1 bg-gray-100 ">
          <select
            value={tipoSugestao}
            onChange={(e) => setTipoSugestao(e.target.value)}
            className="w-full px-3 py-2 border border-gray-400 rounded"
          >
            <option value="melhoria">Melhoria ⚙ </option>
            <option value="bug">Bug encontrado 😬</option>
            <option value="livre">Sugestão livre 😊</option>
          </select>

          <input
            type="email"
            value={emailUsuario}
            onChange={(e) => setEmailUsuario(e.target.value)}
            placeholder="Seu e-mail, caso necessário entraremos em contato para discutir sua sugestão 😁"
            className="w-full mb-3 mt-3 px-3 py-2 border border-gray-400 rounded"
          />
          <input
            type="text"
            value={descricaoSugestao}
            onChange={(e) => setDescricaoSugestao(e.target.value)}
            placeholder="Digite sua sugestão"
            className="w-full mb-3 px-3 py-2 border border-gray-400 rounded"
          />
          <button
            onClick={enviarTextoLivre}
            className="bg-green-400 hover:bg-green-500 text-white font-semibold px-4 py-1 rounded"
          >
            Enviar
          </button>
        </div>
      )}
    </div>
  )
}
