const form = document.getElementById('form')
const input = document.getElementById('input')
const chat = document.getElementById('chat')
const iaSelect = document.getElementById('iaSelect')

form.addEventListener('submit', async (e) => {
  e.preventDefault()
  const message = input.value.trim()
  if (!message) return

  appendMessage('Usuario', message)
  input.value = ''

  const iaId = iaSelect.value || 1

  try {
    const url = `http://localhost:8080/backend/api/gpt/chat/ia/${iaId}?conversacionId=${
      conversacionId || ''
    }`

    const res = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify({ contentText: message }),
    })

    if (!res.ok) throw new Error('Error al enviar mensaje')

    const data = await res.json()

    conversacionId = data.conversacionId

    appendMessage('IA', data.contentText)
  } catch (err) {
    console.error('Error al enviar mensaje:', err)
    appendMessage('Sistema', 'Error al enviar mensaje')
  }
})

function appendMessage(sender, text) {
  const div = document.createElement('div')

  div.className = `p-2 rounded mb-2 ${
    sender === 'Usuario'
      ? 'bg-green-500 text-white text-right'
      : 'bg-sky-300 text-black text-left'
  }`

  div.textContent = text
  chat.appendChild(div)
  chat.scrollTop = chat.scrollHeight
}

const logoutBtn = document.getElementById('logoutBtn')

logoutBtn.addEventListener('click', async () => {
  try {
    const res = await fetch('http://localhost:8080/backend/api/logout', {
      method: 'POST',
      credentials: 'include',
    })

    if (!res.ok) throw new Error('Error al cerrar sesión')

    window.location.href = '../index.html'
  } catch (err) {
    alert(err.message)
  }
})
