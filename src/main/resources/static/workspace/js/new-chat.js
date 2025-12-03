document.addEventListener('DOMContentLoaded', () => {
  const newChatBtn = document.getElementById('newChatBtn')
  const chatContainer = document.getElementById('chat')

  newChatBtn.addEventListener('click', () => {
    // Limpiar el chat
    chatContainer.innerHTML = ''

    // Reiniciar conversacionId para empezar una conversación nueva
    if (typeof conversacionId !== 'undefined') {
      conversacionId = null
    }
  })
})
