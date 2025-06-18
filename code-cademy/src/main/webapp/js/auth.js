// Authentication module

// Declare variables
const users = JSON.parse(localStorage.getItem("users")) || []
let currentUser = JSON.parse(localStorage.getItem("currentUser")) || null

document.addEventListener("DOMContentLoaded", () => {
  setupAuthEventListeners()
})

function setupAuthEventListeners() {
  // Login form
  const loginForm = document.getElementById("loginForm")
  if (loginForm) {
    loginForm.addEventListener("submit", handleLogin)
  }

  // Register form
  const registerForm = document.getElementById("registerForm")
  if (registerForm) {
    registerForm.addEventListener("submit", handleRegister)
  }
}

function handleLogin(e) {
  e.preventDefault()

  const email = document.getElementById("loginEmail").value
  const password = document.getElementById("loginPassword").value

  // Basic validation
  if (!email || !password) {
    showAlert("Por favor completa todos los campos", "error")
    return
  }

  const user = users.find((u) => u.correo === email && u.password === password)

  if (user) {
    currentUser = user
    localStorage.setItem("currentUser", JSON.stringify(user))
    updateUIForLoggedInUser()
    showAlert(`¡Bienvenido ${user.nombre}!`, "success")
    closeModal("loginModal")

    // Clear form
    document.getElementById("loginForm").reset()

    // Redirect based on user role
    setTimeout(() => {
      if (user.rol === "admin") {
        window.location.href = "admin.html"
      } else {
        window.location.href = "courses.html"
      }
    }, 1500)
  } else {
    showAlert("Credenciales incorrectas. Verifica tu email y contraseña.", "error")
  }
}

function handleRegister(e) {
  e.preventDefault()

  const name = document.getElementById("registerName").value
  const email = document.getElementById("registerEmail").value
  const phone = document.getElementById("registerPhone").value
  const country = document.getElementById("registerCountry").value
  const password = document.getElementById("registerPassword").value
  const confirmPassword = document.getElementById("confirmPassword").value

  // Validation
  if (!name || !email || !phone || !country || !password || !confirmPassword) {
    showAlert("Por favor completa todos los campos", "error")
    return
  }

  if (password.length < 6) {
    showAlert("La contraseña debe tener al menos 6 caracteres", "error")
    return
  }

  if (password !== confirmPassword) {
    showAlert("Las contraseñas no coinciden", "error")
    return
  }

  // Email validation
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(email)) {
    showAlert("Por favor ingresa un email válido", "error")
    return
  }

  if (users.find((u) => u.correo === email)) {
    showAlert("El correo ya está registrado. Intenta iniciar sesión.", "error")
    return
  }

  // Create new user
  const newUser = {
    id_usuario: Date.now(),
    nombre: name,
    correo: email,
    telefono: phone,
    pais: country,
    rol: "usuario",
    password: password,
  }

  users.push(newUser)
  localStorage.setItem("users", JSON.stringify(users))

  showAlert("¡Registro exitoso! Ahora puedes iniciar sesión.", "success")
  closeModal("registerModal")

  // Clear form
  document.getElementById("registerForm").reset()

  // Show login modal after successful registration
  setTimeout(() => {
    showLogin()
    // Pre-fill email in login form
    document.getElementById("loginEmail").value = email
  }, 1500)
}
