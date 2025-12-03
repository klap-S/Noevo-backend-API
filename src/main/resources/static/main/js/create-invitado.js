document.addEventListener('DOMContentLoaded', () => {
  const guestBtn = document.getElementById('guestBtn2')
  const authMessage = document.getElementById('authMessage')

  guestBtn.addEventListener('click', async () => {
    try {
      const res = await fetch(
        'http://localhost:8080/backend/api/usuario/create/invitado',
        { method: 'POST', credentials: 'include' }
      )
      if (!res.ok) throw new Error('Error al crear invitado')

      const data = await res.json()

      sessionStorage.setItem('userName', data.userName)
      sessionStorage.setItem('rol', data.rol)

      authMessage.textContent = 'Entrando como invitado...'

      window.location.href = '../../workspace/chat-ia-mensajes.html'
    } catch (err) {
      console.error(err)
      authMessage.textContent = 'Error al crear invitado'
    }
  })
})
