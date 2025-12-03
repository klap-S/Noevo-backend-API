const REGISTER_API_URL = 'http://localhost:8080/backend/api/usuario/createUser'
const LOGIN_PAGE_PATH = '../../index.html'

const $ = (id) => document.getElementById(id)

function displayStatus(msg, isErr = false) {
  const statusDiv = $('register-status')
  if (statusDiv) {
    statusDiv.textContent = msg
    statusDiv.className = `text-center text-sm font-medium mt-4 h-5 ${
      isErr ? 'text-red-600' : 'text-primary-accent'
    }`
  } else {
    console.error(
      `Error de DOM: El elemento 'register-status' no existe. Mensaje: ${msg}`
    )
  }
}

function setButtonLoading(loading) {
  const registerBtn = $('register-btn')
  if (registerBtn) {
    registerBtn.disabled = loading
    registerBtn.classList.toggle('opacity-50', loading)
    registerBtn.classList.toggle('cursor-not-allowed', loading)
    registerBtn.textContent = loading ? 'Registrando...' : 'Registrarse'
  }
}

async function handleRegister(event) {
  event.preventDefault()

  const userData = {
    name: $('register-name')?.value,
    lastNames: $('register-lastname')?.value,
    userName: $('register-username')?.value,
    email: $('register-email')?.value,
    password: $('register-password')?.value,
    language: $('register-language')?.value,
  }

  if (!userData.password || userData.password.length < 6) {
    return displayStatus(
      'La contraseña debe tener al menos 6 caracteres.',
      true
    )
  }

  if (Object.values(userData).some((val) => val === undefined)) {
    console.error(
      'Error crítico: Uno o más IDs de campos del formulario no coinciden con el script. Revise la consola.'
    )
    return displayStatus(
      'Error de configuración del formulario (revisa los IDs).',
      true
    )
  }

  displayStatus('Enviando registro...')
  setButtonLoading(true)

  try {
    const res = await fetch(REGISTER_API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userData),
    })

    if (res.ok) {
      await res.json()
      displayStatus(`¡Cuenta creada exitosamente! Redirigiendo...`)
      setTimeout(() => {
        window.location.href = LOGIN_PAGE_PATH
      }, 1500)
    } else if (res.status >= 400) {
      const errText = await res.text()

      if (errText.includes('El nombre de usuario esta registrado')) {
        displayStatus('❌ Nombre de usuario ya registrado.', true)
      } else if (errText.includes('El email ya está registrado')) {
        displayStatus('❌ El email ya está registrado.', true)
      } else {
        displayStatus(`Fallo al registrar (Código ${res.status}).`, true)
        console.error(`Error ${res.status}:`, errText)
      }
    } else {
      displayStatus(`Fallo al registrar (Código ${res.status}).`, true)
    }
  } catch (e) {
    displayStatus(
      'Error de conexión con el backend (Servidor no disponible o CORS).',
      true
    )
    console.error('Error de red/fetch:', e)
  } finally {
    setButtonLoading(false)
  }
}
