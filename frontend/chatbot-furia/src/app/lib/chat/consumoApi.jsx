export const fetchMenu = async () => {
  const res = await fetch('http://localhost:8080/chat')
  return res.json()
}

export const postChat = async (payload) => {
  const res = await fetch('http://localhost:8080/chat', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  })
  return res.json()
}