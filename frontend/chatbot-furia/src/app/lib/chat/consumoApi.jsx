//Consome api do backend para mandar um GET e buscar as opções do menu
///////////////
//Caso esteja rodando localmente o backend, tire o comentario da linha abaixo e comente a linha 7
// const URL = "http://localhost:8080/chat"
//Caso esteja rodando apenas o frotend localmente, tire o comentario da linha 6
//const URL = "https://single-dixie-thiagoantenor-ce6e3e58.koyeb.app/chat"
//////////////
//Abaixo é pego a variavel de ambiente do vercel para pegar o URL da API do backend
const URL = process.env.NEXT_PUBLIC_CHAT_API_URL;
export const fetchMenu = async () => {
  const res = await fetch(URL)
  if (!res.ok) {
    throw new Error(`HTTP error! status: ${res.status}`);
  }
  return res.json()
}

//Consome api do backend para mandar um POST e enviar a opção do menu selecionada
export const postChat = async (payload) => {
  const res = await fetch(URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  })
  if (!res.ok) {
    throw new Error(`HTTP error! status: ${res.status}`);
  }
  return res.json()
}