// auth.js

// [DECLARACIÓN DE ELEMENTOS DEL DOM]
const emailInput = document.getElementById('login-email')
const passwordInput = document.getElementById('login-password')
const statusDiv = document.getElementById('auth-status')
const loginBtn = document.getElementById('login-btn')
const guestBtn = document.querySelector('button[onclick="handleGuestLogin()"]')

// [CONFIGURACIÓN DE ENDPOINTS Y RUTAS]
const LOGIN_API_URL = 'http://localhost:8080/backend/api/login'
const GUEST_API_URL =
  'http://localhost:8080/backend/api/usuario/create/invitado'
const WORKSPACE_PATH = 'workspace/chat-ia-mensajes.html'

// ----------------------------------------------------
// [UTILIDADES DE INTERFAZ]
// ----------------------------------------------------

// Muestra mensajes de estado.
function displayStatus(message, isError = false) {
  statusDiv.textContent = message
  statusDiv.className = `text-center text-sm font-medium mt-4 h-5 ${
    isError ? 'text-red-600' : 'text-primary-accent'
  }`
}

// Controla el estado de carga y texto del botón.
function setButtonLoading(btn, isLoading, defaultText) {
  btn.disabled = isLoading
  btn.classList.toggle('opacity-50', isLoading)
  btn.classList.toggle('cursor-not-allowed', isLoading)
  btn.textContent = isLoading ? 'Cargando...' : defaultText
}

// ----------------------------------------------------
// [LÓGICA DE AUTENTICACIÓN ESTÁNDAR]
// ----------------------------------------------------

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
      // Login exitoso. El navegador gestiona la cookie de sesión.
      await response.json() // Consumir la respuesta DTO (opcional, pero mantiene el flujo)
      displayStatus(`Login exitoso. Redirigiendo...`)
      window.location.href = WORKSPACE_PATH
    } else {
      // Error de credenciales o servidor.
      displayStatus('Credenciales inválidas o fallo en el servidor.', true)
    }
  } catch (error) {
    // Error de red.
    displayStatus('Error de conexión con el backend.', true)
    console.error('Error de red:', error)
  } finally {
    setButtonLoading(loginBtn, false, 'Iniciar Sesión')
  }
}

// ----------------------------------------------------
// [LÓGICA DE ACCESO COMO INVITADO]
// ----------------------------------------------------

async function handleGuestLogin() {
  displayStatus('Generando sesión de invitado...')
  setButtonLoading(guestBtn, true, 'Entrar como Invitado (Anónimo)')

  try {
    const response = await fetch(GUEST_API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
    })

    if (response.ok) {
      // ÉXITO: El backend ya ha creado el usuario y enviado la cookie de sesión.
      // No necesitamos procesar el body (el DTO), solo redirigir.
      displayStatus(`Sesión de invitado establecida. Redirigiendo...`)
      window.location.href = WORKSPACE_PATH
    } else {
      // Error en la creación del invitado (ej: 500 del servidor).
      displayStatus('Fallo al generar sesión de invitado.', true)
      console.error('Error al generar invitado (Código:', response.status, ')')
    }
  } catch (error) {
    // Error de red.
    displayStatus('Error de conexión con el backend.', true)
    console.error('Error de red:', error)
  } finally {
    setButtonLoading(guestBtn, false, 'Entrar como Invitado (Anónimo)')
  }
}
