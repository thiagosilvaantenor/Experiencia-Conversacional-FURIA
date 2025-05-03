import { extrairTextoELink } from "./utils"

export const handleOpcao1 = (dados, setMensagens, setCarregando) => {
  if (dados.emLive) {
    setTimeout(() => {
      setCarregando(false)
      dados.emLive.forEach(jogador => {
        const { link } = extrairTextoELink(jogador.twitch)
        setMensagens(prev => [...prev, {
          texto: `${jogador.jogador} está em live agora 🔥🔥 jogando ${jogador.jogando}.\nAssista em`,
          link,
          remetente: "bot",
        }])
      })
    }, 1000)
  } else {
    setMensagens(prev => [...prev, { texto: dados.mensagem, remetente: "bot" }])
    setTimeout(() => {
      setCarregando(false)
      dados.canais.forEach(canal => {
        const { link } = extrairTextoELink(canal.twitch)
        setMensagens(prev => [...prev, {
          texto: `Siga ${canal.jogador} na Twitch:`,
          link,
          remetente: "bot"
        }])
      })
    }, 500)
  }
}

export const handleOpcao2 = (dados, setMensagens, setCarregando) => {
  dados.canais.forEach(canal => {
    ["twitch", "youtube", "instagram"].forEach((rede, i) => {
      setTimeout(() => {
        setMensagens(prev => [...prev, {
          texto: `Jogador: ${canal.jogador}\nCanal ${rede.charAt(0).toUpperCase() + rede.slice(1)}:`,
          link: extrairTextoELink(canal[rede]).link,
          remetente: "bot"
        }])
        setCarregando(false)
      }, 500 * (i + 1))
    })
  })
}

export const handleOpcao3 = (dados, setMensagens, setCarregando) => {
  dados.skins_mapas.forEach((item, i) => {
    setTimeout(() => {
      setMensagens(prev => [...prev, {
        texto: `Jogador: ${item.jogador}\nSkinFavorita⭐: ${item.skinNome} da ${item.skinArma}🔫\nMapaFavorito🗺: ${item.mapaFavorito}`,
        remetente: "bot"
      }])
      setCarregando(false)
    }, 1000 * (i + 1))
  })
}
