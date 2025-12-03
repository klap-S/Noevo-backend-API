document.addEventListener('DOMContentLoaded', async () => {
  const listContainer = document.getElementById('conversationList')
  const chatContainer = document.getElementById('chat')

  try {
    const response = await fetch(
      'http://localhost:8080/backend/api/conversacion/ordenada',
      {
        method: 'GET',
        credentials: 'include', // ⚡ importante para cookies
      }
    )

    if (!response.ok)
      throw new Error(`Error en la petición: ${response.status}`)

    const conversaciones = await response.json()
    listContainer.innerHTML = ''

    conversaciones.forEach((conv) => {
      const li = document.createElement('li')
      li.textContent = conv.title || `Conversación ${conv.id}`
      li.className = 'p-2 bg-gray-100 rounded cursor-pointer hover:bg-gray-200'

      li.addEventListener('click', async () => {
        // Al hacer click, se cargan los mensajes de esa conversación
        try {
          const mensajesResp = await fetch(
            `http://localhost:8080/backend/api/mensaje/showMessage/usuario/${conv.usuarioId}/ia/${conv.iaId}/conversacion/${conv.id}`,
            {
              method: 'GET',
              credentials: 'include',
            }
          )

          if (!mensajesResp.ok)
            throw new Error(`Error al cargar mensajes: ${mensajesResp.status}`)

          const mensajes = await mensajesResp.json()

          // Limpiar chat
          chatContainer.innerHTML = ''

          // Mostrar mensajes
          mensajes.forEach((msg) => {
            const div = document.createElement('div')
            div.textContent = msg.text // Ajusta según tu DTO
            div.className = 'p-2 bg-gray-200 rounded mb-2'
            chatContainer.appendChild(div)
          })
        } catch (error) {
          console.error(error)
          chatContainer.innerHTML =
            "<div class='text-red-500'>Error cargando mensajes</div>"
        }
      })

      listContainer.appendChild(li)
    })
  } catch (error) {
    console.error('Error cargando conversaciones:', error)
    listContainer.innerHTML =
      "<li class='text-red-500'>Error cargando conversaciones</li>"
  }
})
