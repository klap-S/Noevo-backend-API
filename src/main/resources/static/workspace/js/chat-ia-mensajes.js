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

  const iaId = iaSelect.value || 1 // IA seleccionada

  try {
    const url = `http://localhost:8080/backend/api/gpt/chat/ia/${iaId}?conversacionId=${
      conversacionId || ''
    }`

    const res = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include', // enviar cookies de sesión para mantener login
      body: JSON.stringify({ contentText: message }),
    })

    if (!res.ok) throw new Error('Error al enviar mensaje')

    const data = await res.json()

    // Guardar id de la conversación si es nueva
    conversacionId = data.conversacionId

    // Mostrar respuesta de la IA
    appendMessage('IA', data.contentText)
  } catch (err) {
    console.error('Error al enviar mensaje:', err)
    appendMessage('Sistema', 'Error al enviar mensaje')
  }
})

// Función para agregar mensajes al chat
function appendMessage(sender, text) {
  const div = document.createElement('div')
  div.className = sender === 'Usuario' ? 'text-right' : 'text-left'
  div.innerHTML = `<span class="inline-block px-3 py-2 rounded-lg bg-${
    sender === 'Usuario' ? 'blue-500' : 'gray-300'
  } text-${sender === 'Usuario' ? 'white' : 'black'}">${text}</span>`
  chat.appendChild(div)
  chat.scrollTop = chat.scrollHeight
}

const logoutBtn = document.getElementById('logoutBtn')

logoutBtn.addEventListener('click', async () => {
  try {
    const res = await fetch('http://localhost:8080/backend/api/logout', {
      method: 'POST',
      credentials: 'include', // importante para enviar cookies de sesión
    })

    if (!res.ok) throw new Error('Error al cerrar sesión')

    // Redirigir a login
    window.location.href = '../index.html'
  } catch (err) {
    alert(err.message)
  }
})
