// auth.js

// [DECLARACIÓN DE ELEMENTOS DEL DOM]
const emailInput = document.getElementById('login-email')
const passwordInput = document.getElementById('login-password')
const statusDiv = document.getElementById('auth-status')
const loginBtn = document.getElementById('login-btn')
const guestBtn = document.querySelector('button[onclick="handleGuestLogin()"]')

const LOGIN_API_URL = 'http://localhost:8080/backend/api/login'
const GUEST_API_URL =
  'http://localhost:8080/backend/api/usuario/create/invitado'
const WORKSPACE_PATH = 'workspace/chat-ia-mensajes.html'

function displayStatus(message, isError = false) {
  statusDiv.textContent = message
  statusDiv.className = `text-center text-sm font-medium mt-4 h-5 ${
    isError ? 'text-red-600' : 'text-primary-accent'
  }`
}

function setButtonLoading(btn, isLoading, defaultText) {
  btn.disabled = isLoading
  btn.classList.toggle('opacity-50', isLoading)
  btn.classList.toggle('cursor-not-allowed', isLoading)
  btn.textContent = isLoading ? 'Cargando...' : defaultText
}

async function handleLogin(event) {
  event.preventDefault()

  const email = emailInput.value
  const password = passwordInput.value

  if (!email || !password) return displayStatus('Faltan datos de acceso.', true)

  displayStatus('Procesando login...')
  setButtonLoading(loginBtn, true, 'Iniciar Sesión')

  try {
    const response = await fetch(LOGIN_API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password }),
    })

    if (response.ok) {
      await response.json()
      displayStatus(`Login exitoso. Redirigiendo...`)
      window.location.href = WORKSPACE_PATH
    } else {
      displayStatus('Credenciales inválidas o fallo en el servidor.', true)
    }
  } catch (error) {
    displayStatus('Error de conexión con el backend.', true)
    console.error('Error de red:', error)
  } finally {
    setButtonLoading(loginBtn, false, 'Iniciar Sesión')
  }
}

async function handleGuestLogin() {
  displayStatus('Generando sesión de invitado...')
  setButtonLoading(guestBtn, true, 'Entrar como Invitado (Anónimo)')

  try {
    const response = await fetch(GUEST_API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
    })

    if (response.ok) {
      displayStatus(`Sesión de invitado establecida. Redirigiendo...`)
      window.location.href = WORKSPACE_PATH
    } else {
      displayStatus('Fallo al generar sesión de invitado.', true)
      console.error('Error al generar invitado (Código:', response.status, ')')
    }
  } catch (error) {
    displayStatus('Error de conexión con el backend.', true)
    console.error('Error de red:', error)
  } finally {
    setButtonLoading(guestBtn, false, 'Entrar como Invitado (Anónimo)')
  }
}
