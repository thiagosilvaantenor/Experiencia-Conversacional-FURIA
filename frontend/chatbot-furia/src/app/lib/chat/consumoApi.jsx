//Consome api do backend para mandar um GET e buscar as opções do menu
//Caso esteja rodando localmente, descomente a linha abaixo
// const URL = "http://localhost:8080/chat"
//Abaixo é usado o URL do backend que esta rodando no KOYEB
const URL = "https://single-dixie-thiagoantenor-ce6e3e58.koyeb.app/chat"
export const fetchMenu = async () => {
  const res = await fetch(URL)
  return res.json()
}

//Consome api do backend para mandar um POST e enviar a opção do menu selecionada
export const postChat = async (payload) => {
  const res = await fetch(URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  })
  return res.json()
}