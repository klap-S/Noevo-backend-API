// create.js - Lógica de registro concisa y robusta
// CORREGIDO: IDs de elementos y nombre de función (handleRegister) ajustados al HTML.

// [CONFIGURACIÓN DE ENDPOINTS]
const REGISTER_API_URL = 'http://localhost:8080/backend/api/usuario/createUser'
const LOGIN_PAGE_PATH = '../../index.html'

// Función auxiliar para obtener elementos del DOM por ID
const $ = (id) => document.getElementById(id)

// ----------------------------------------------------
// [UTILIDADES DE INTERFAZ]
// ----------------------------------------------------

/** Muestra o actualiza el estado de la interfaz. */
function displayStatus(msg, isErr = false) {
  // CORREGIDO: Usando 'register-status'
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

/** Habilita/Deshabilita el botón de registro. */
function setButtonLoading(loading) {
  const registerBtn = $('register-btn')
  if (registerBtn) {
    registerBtn.disabled = loading
    registerBtn.classList.toggle('opacity-50', loading)
    registerBtn.classList.toggle('cursor-not-allowed', loading)
    registerBtn.textContent = loading ? 'Registrando...' : 'Registrarse'
  }
}

// ----------------------------------------------------
// [LÓGICA DE REGISTRO PRINCIPAL]
// ----------------------------------------------------

/** Maneja el envío del formulario de registro. (Nombre de función ajustado) */
async function handleRegister(event) {
  event.preventDefault()

  // 1. Recolección de datos utilizando los IDs largos del HTML proporcionado
  const userData = {
    name: $('register-name')?.value,
    lastNames: $('register-lastname')?.value, // Nota: Backend espera 'lastNames' (plural), HTML usa 'register-lastname' (singular)
    userName: $('register-username')?.value,
    email: $('register-email')?.value,
    password: $('register-password')?.value,
    language: $('register-language')?.value,
  }

  // 2. Verificación básica
  if (!userData.password || userData.password.length < 6) {
    return displayStatus(
      'La contraseña debe tener al menos 6 caracteres.',
      true
    )
  }

  // Verificación si alguno de los campos clave es undefined (Error de DOM/ID)
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
      // Éxito: Registro completado
      await res.json()
      displayStatus(`¡Cuenta creada exitosamente! Redirigiendo...`)
      setTimeout(() => {
        window.location.href = LOGIN_PAGE_PATH
      }, 1500)
    } else if (res.status >= 400) {
      // Manejo de errores de validación y de servidor
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
    // Error de red (CORS, servidor no disponible)
    displayStatus(
      'Error de conexión con el backend (Servidor no disponible o CORS).',
      true
    )
    console.error('Error de red/fetch:', e)
  } finally {
    setButtonLoading(false)
  }
}
