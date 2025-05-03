import { extrairTextoELink } from "./utils"

export const handleOpcao1 = (dados, setMensagens, setCarregando) => {
  if (dados.emLive) {
    setTimeout(() => {
      setCarregando(false)
      dados.emLive.forEach(jogador => {
        const { link } = extrairTextoELink(jogador.canal)
        setMensagens(prev => [...prev, {
          texto: `${jogador.jogador} está em live agora 🔥🔥 jogando:\n${jogador.jogando}.\nAssista em:`,
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
    //Link twitch
      setTimeout(() => {
        setMensagens(prev => [...prev, {
          texto: `Jogador: ${canal.jogador}\nCanal da Twitch 🎮 :`,
          link: extrairTextoELink(canal.twitch).link,
          remetente: "bot"
        }])
        setCarregando(false)
      }, 500)
    //Link youtube
    setTimeout(() => {
      setMensagens(prev => [...prev, {
        texto: `Jogador: ${canal.jogador}\nCanal da Youtube ▶ :`,
        link: extrairTextoELink(canal.youtube).link,
        remetente: "bot"
      }])
      setCarregando(false)
    }, 500)
    //Link instagram
    setTimeout(() => {
      setMensagens(prev => [...prev, {
        texto: `Jogador: ${canal.jogador}\nCanal da Instagram 📸 :`,
        link: extrairTextoELink(canal.instagram).link,
        remetente: "bot"
      }])
      setCarregando(false)
    }, 500)
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
