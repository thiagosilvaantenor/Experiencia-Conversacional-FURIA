'use client'
import { fetchMenu, postChat } from '@/app/lib/chat/consumoApi';
import frasesTorcida from '@/app/lib/chat/frasesTorcida';
import { handleOpcao1, handleOpcao2, handleOpcao3 } from '@/app/lib/chat/handlersOpcao';
import { useEffect, useState } from 'react';
import Opcao from '../Opcao';

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

  
  
  //Realiza a requisição GET(/chat) para o backend para exibir o menu com as opções disponiveis do backend
  useEffect(() => {
    fetchMenu().then(setOpcoesMenu);
  }, [])

  const enviarOpcao = async (valor) => {
    setOpcaoSelecionada(valor)

    if (frasesTorcida[valor]) {
      const frases = frasesTorcida[valor]
      const aleatoria = frases[Math.floor(Math.random() * frases.length)]
      setMensagens(prev => [...prev, { texto: aleatoria, remetente: 'torcida' }])
    } else {
      setMensagens(prev => [...prev, { texto: `Opção ${valor}`, remetente: 'usuario' }])
    }

    setCarregando(true)
    const payload = { opcao: valor }
    const dados = await postChat(payload)

    switch (valor) {
      case "1":
        handleOpcao1(dados, setMensagens, setCarregando)
        break
      case "2":
        handleOpcao2(dados, setMensagens, setCarregando)
        break
      case "3":
        handleOpcao3(dados, setMensagens, setCarregando)
        break
      case "4":
        return // apenas exibe input
      default:
        setCarregando(false)
        setMensagens(prev => [...prev, { texto: dados.mensagem, remetente: 'bot' }])
    }
  }

  const enviarTextoLivre = async () => {
    setMensagens(prev => [...prev, { texto: descricaoSugestao, remetente: 'usuario' }])
    const payload = {
      opcao: '4',
      tipo: tipoSugestao,
      descricao: descricaoSugestao,
      emailUsuario
    }

    setCarregando(true)
    const dados = await postChat(payload)
    setTimeout(() => {
      setCarregando(false)
      setDescricaoSugestao('')
      setEmailUsuario('')
      setOpcaoSelecionada('')
      setMensagens(prev => [...prev, { texto: dados.mensagem, remetente: 'bot' }])
    }, 1500)
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
