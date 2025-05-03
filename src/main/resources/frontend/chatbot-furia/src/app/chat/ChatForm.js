'use client'
import {useEffect, useState} from 'react'
import Opcao from "@/app/chat/Opcao";

export default function ChatFuria() {
  const [mensagens, setMensagens] = useState([
    { texto: 'Boas vindas ao Bot da Furia CS', remetente: 'bot' }
  ])
  const [opcaoSelecionada, setOpcaoSelecionada] = useState('');
  const [respostaTexto, setRespostaTexto] = useState('');
  const [opcoesMenu, setOpcoesMenu] = useState({});

  useEffect(() => {
    const fetchMenu = async () => {
      const res = await fetch('http://localhost:8080/chat')
      const data = await res.json()
      setOpcoesMenu(data)
    }
    fetchMenu()
  }, [])


  const enviarOpcao = async (valor) => {
    setMensagens(prev => [...prev, { texto: `Opção ${valor}`, remetente: 'usuario' }])
    setOpcaoSelecionada(valor)

    const payload = { opcao: valor }
    const res = await fetch('http://localhost:8080/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
    const text = await res.text()

    setMensagens(prev => [...prev, { texto: text, remetente: 'bot' }])

  }

  const enviarTextoLivre = async () => {
    setMensagens(prev => [...prev, { texto: respostaTexto, remetente: 'usuario' }])

    //TODO: Pegar valor de tipo e email do usuario pelo forms
    const payload = {
      opcao: '4',
      tipo: 'Sugestão',
      descricao: respostaTexto,
      emailUsuario: 'usuario@teste.com'
    }

    const res = await fetch('http://localhost:8080/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
    const text = await res.text()
    setMensagens(prev => [...prev, { texto: text, remetente: 'bot' }])
    setRespostaTexto('')
  }

  return (
      <div className=" max-w-md mx-auto mt-10 p-5 bg-gray-300 rounded-[50px]">
        <h2 className="text-center font-bold mb-4">CHAT FURIA CS</h2>

        {/*Mensagens*/}
        <div className="space-y-2 mb-4">
          {mensagens.map((msg, idx) => (
              <div key={idx} className={`flex ${msg.remetente === 'bot' ? 'justify-start' : 'justify-end'}`}>
                <div className={`text-white p-4 rounded-2xl max-w-sm break-words whitespace-pre-wrap
              ${msg.remetente === 'bot' ? 'bg-blue-500' : 'bg-green-500'}`}>
                  {msg.texto}
                </div>
              </div>
          ))}
        </div>

        {/*Opções
        TODO: Corrigir bug quando é clicado em opção 4 é criado uma sugestão sem descrição
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
            <div className="flex items-center border border-black rounded px-2 py-1 bg-gray-100">
              <input
                  type="text"
                  value={respostaTexto}
                  onChange={(e) => setRespostaTexto(e.target.value)}
                  placeholder="Digite caso necessário"
                  className="flex-1 bg-transparent outline-none px-2"
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
