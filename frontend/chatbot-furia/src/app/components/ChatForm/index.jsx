'use client'
import { useEffect, useState } from 'react'
import Opcao from "@/app/components/Opcao";

export default function ChatForm() {
  const [mensagens, setMensagens] = useState([
    { texto: 'Boas vindas ao Bot da Furia CS', remetente: 'bot' }
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

    // Se for a opção 4, apenas mostra o input, sem enviar nada
    if (valor === '4') return;

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
      setCarregando(false);
      break;
    case "2":
      retornaRedesSociais(dados);
      setCarregando(false);
      break;
    default:
      // Para outras opções, simplesmente mostra a mensagem
      setCarregando(false);
      setMensagens(prev => [...prev, { texto: dados.mensagem, remetente: 'bot' }]);
    }
    
  }

  //Caso opção tenha valor = 1
  const verificaLive = (dados) => {
    //Recebe o json(data) e verifica se alguem está emLive
    if (dados.emLive) {
      dados.emLive.forEach(jogador => {
        setMensagens(prev => [
          ...prev,
          {
            texto: `${jogador.jogador} está em live agora 🔥🔥 e esta jogando ${jogador.jogando}.\n Assista em: ${jogador.canal}`,
            remetente: "bot",   
          }
        ]);
      });
    }//Caso ninguem estiver emLive, exibe que ninguem está em live e o canal da twitch dos jogadores
    else{
      setMensagens(prev => [
        ...prev,
        {
          texto:dados.mensagem, remetente: "bot"
        }
      ]);
      dados.canais.forEach(canal => {
        setMensagens( prev => [
        ...prev, {
          texto: `Siga ${canal.jogador} na Twitch:\n ${canal.twitch}`,
          remetente: "bot"
        }
      ]);
    });
  } 
  }

  const retornaRedesSociais = (dados) => {
      //Recebe o json(data) e retorna as redes sociais dos jogadores
      dados.canais.forEach(canal => {
          setMensagens( prev => [
            ...prev, {
              texto: `Jogador: ${canal.jogador}\nCanal da Twitch 🎮:\n${canal.twitch}\nCanal do Youtube ▶:\n${canal.youtube}\nInstagram 📸:\n${canal.instagram}`,
              remetente: "bot"
            }
          ])
      })
  }


  //Caso seja escolhido a opção 4, é necessário habilitar o campo de texto para o usuário informar os atributos de sugestão incluindo o email para entrar em contato caso seja necessário
  const enviarTextoLivre = async () => {
    setMensagens(prev => [...prev, { texto: descricaoSugestao, remetente: 'usuario' }])
    setCarregando(true);

    //Pega os valores recebidos do html que agora estão nas variaveis de estado e constroi o objeto Sugestão a ser salvo
    const payload = {
      opcao: '4',
      tipo: tipoSugestao,
      descricao: descricaoSugestao,
      emailUsuario: emailUsuario
    }

    //Com os valores recebidos envia a requisição para criar uma sugestão em POST /sugestoes do backend    
    const res = await fetch('http://localhost:8080/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
    //Recebe a confirmação se foi salvo Sugestão no banco de dados
    const text = await res.text()
    //Retira tela de carregamento, altera estados dos atributos de sugestão e exibe mensagem no bot
    setTimeout(() => {
      setCarregando(false);
      setDescricaoSugestao('')
      setEmailUsuario('')
      setOpcaoSelecionada('')
      setMensagens(prev => [...prev, { texto: text, remetente: 'bot' }]);
    }, 1500);

  }
  //HTML e CSS do componente Chat
  return (
    <div className="max-w-[55vw] max-h-[35vw] overflow-y-auto mx-auto mt-10 p-5 bg-white/50 rounded-[30px] shadow-xl/30">

      <h2 className="text-center font-bold mb-4">CHAT FURIA CS</h2>

      {/*Mensagens*/}
      <div className="space-y-2 mb-4">
        {mensagens.map((msg, idx) => (
          <div key={idx} className={`flex ${msg.remetente === 'bot' ? 'justify-start' : 'justify-end'}`}>
            <div className={`text-black text-stro shadow-md/30 border-black p-4 rounded-2xl max-w-sm break-words whitespace-pre-wrap
              ${msg.remetente === 'bot' ? 'bg-blue-800' : 'bg-yellow-200'}`}>
              {msg.texto}
           </div>
          </div>
        ))}
        {/*Quando estiver buscando dados do backend exibe tela de carregamento*/}
        {carregando && (
          <div className="flex justify-start">
            <div className="bg-blue-400 text-white px-4 py-2 rounded-2xl flex items-center space-x-1">
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
            <option value="melhoria">Melhoria</option>
            <option value="bug">Bug encontrado</option>
            <option value="livre">Sugestão livre</option>
          </select>

          <input
            type="email"
            value={emailUsuario}
            onChange={(e) => setEmailUsuario(e.target.value)}
            placeholder="Seu e-mail"
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
