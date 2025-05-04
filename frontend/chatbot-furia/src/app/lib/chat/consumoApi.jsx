//Consome api do backend para mandar um GET e buscar as opções do menu
export const fetchMenu = async () => {
  const res = await fetch('http://localhost:8080/chat')
  return res.json()
}

//Consome api do backend para mandar um POST e enviar a opção do menu selecionada
export const postChat = async (payload) => {
  const res = await fetch('http://localhost:8080/chat', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  })
  return res.json()
}