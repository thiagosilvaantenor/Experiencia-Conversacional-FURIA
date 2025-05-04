import { extrairTextoELink } from "./utils"

// Lida com as opções, nesse caso se escolhida opção 1
export const handleOpcao1 = (dados, setMensagens, setCarregando) => {
  //Recebe os dados do json, e as funções para mudar estado de mensagem e carregamento
  if (dados.emLive) {
    //caso tenha alguem emLive
    setTimeout(() => {
      //Tira o carregando do forms
      setCarregando(false)
      //itera sobre os jogadores emlive, busca os links dos canais da twitch, exibe nome, canal e jogo que esta jogando
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
    //Caso não tenha ninguem emLive, avisa que nenhum jogador está online
    setMensagens(prev => [...prev, { texto: dados.mensagem, remetente: "bot" }])
    setTimeout(() => {
      //tira o carregamento do forms
      setCarregando(false)
      //itera sobre os canais e exibe nome e canal da twitch de cada jogador
      dados.canais.forEach(canal => {
        const link = extrairTextoELink(canal.twitch).link;
        if (link) {
          setMensagens(prev => [...prev, {
            texto: `Siga ${canal.jogador} na Twitch:`,
            link,
            remetente: "bot"
          }])
        } else {
          //Caso não tenha twitch, pula o jogador
        }
      })
    }, 500)
  }
}

// Lida com as opções, nesse caso se escolhida opção 2
export const handleOpcao2 = (dados, setMensagens, setCarregando) => {
  //Recebe os dados do json, e as funções para mudar estado de mensagem e carregamento
  dados.canais.forEach(canal => {
    //Link twitch, itera sobre a lista de json
    //utiliza da função de extração de texto e link para ter um link funcional
    const twitch = extrairTextoELink(canal.twitch).link;
    setTimeout(() => {
      if (twitch) {
        setMensagens(prev => [...prev, {
          texto: `Jogador: ${canal.jogador}\nCanal da Twitch 🎮 :`,
          link: twitch,
          remetente: "bot"
        }])
      } else {
        setMensagens(prev => [...prev, {
          texto: `Jogador: ${canal.jogador}\nNão possui Canal na Twitch 😭`,
          remetente: "bot"
        }])
      }
      setCarregando(false)
    }, 500)
    //Link youtube, itera sobre a lista de json
    const yt = extrairTextoELink(canal.youtube).link;
    setTimeout(() => {
      if (yt) {
        setMensagens(prev => [...prev, {
          texto: `Jogador: ${canal.jogador}\nCanal no Youtube ▶ :`,
          //utiliza da função de extração de texto e link para ter um link funcional
          link: yt,
          remetente: "bot"
        }])
      } else {
        setMensagens(prev => [...prev, {
          texto: `Jogador: ${canal.jogador}\nNão possui canal no Youtube 😭`,
          remetente: "bot"
        }])
      }
      setCarregando(false)
    }, 500)
    //Link instagram, itera sobre a lista de json
    //utiliza da função de extração de texto e link para ter um link funcional
    const instagram = extrairTextoELink(canal.instagram).link;
    setTimeout(() => {
      if (instagram) {
        setMensagens(prev => [...prev, {
          texto: `Jogador: ${canal.jogador}\nInstagram 📸:`,
          link: instagram,
          remetente: "bot"
        }])
      } else {
        setMensagens(prev => [...prev, {
          texto: `Jogador: ${canal.jogador}\nNão possui Instagram 😭`,
          remetente: "bot"
        }])
      }
      setCarregando(false)
    }, 500)
  })
}
//Lida com a opção 3
export const handleOpcao3 = (dados, setMensagens, setCarregando) => {
  //itera sobre os dados do json, recebe as funções de mudança de estado de mensagem e carregando
  dados.skins_mapas.forEach((item, i) => {
    //Envia, com um delay para imitar uma pessoa digitando, as skins e mapas favoritos dos jogadores
    setTimeout(() => {
      setMensagens(prev => [...prev, {
        texto: `Jogador: ${item.jogador}\nSkinFavorita⭐: ${item.skinNome} da ${item.skinArma}🔫\nMapaFavorito🗺: ${item.mapaFavorito}`,
        remetente: "bot"
      }])
      setCarregando(false)
    }, 1000 * (i + 1))
  })
}
