document.addEventListener('DOMContentLoaded', async () => {
  const iaSelect = document.getElementById('iaSelect')

  try {
    const response = await fetch('http://localhost:8080/backend/api/ia')
    const data = await response.json()

    // Limpiar select
    iaSelect.innerHTML = ''

    data.forEach((ia) => {
      const option = document.createElement('option')

      option.value = ia.id
      option.dataset.model = ia.rol
      option.dataset.language = ia.language

      option.textContent = `${ia.name} - ${ia.rol} (${ia.language})`

      iaSelect.appendChild(option)
    })
  } catch (error) {
    console.error('Error cargando IA:', error)
  }
})
