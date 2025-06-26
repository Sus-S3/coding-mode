class AuthManager {
  constructor() {
    this.currentUser = null
    this.init()
  }

  init() {
    // Crear admin predeterminado si no existe
    this.createDefaultAdmin()

    // Cargar usuario actual
    const savedUser = localStorage.getItem("currentUser")
    if (savedUser) {
      this.currentUser = JSON.parse(savedUser)
    }
  }

  createDefaultAdmin() {
    const users = JSON.parse(localStorage.getItem("users") || "[]")
    const adminExists = users.find((u) => u.correo === "admin@codeacademy.com")

    if (!adminExists) {
      const adminUser = {
        id: "admin-001",
        nombre: "Administrador",
        correo: "admin@codeacademy.com",
        telefono: "+1234567890",
        pais: "México",
        rol: "administrador",
        password: "admin123",
      }
      users.push(adminUser)
      localStorage.setItem("users", JSON.stringify(users))
    }
  }

  async login(correo, password) {
    const users = JSON.parse(localStorage.getItem("users") || "[]")
    const foundUser = users.find((u) => u.correo === correo && u.password === password)

    if (foundUser) {
      const { password: _, ...userWithoutPassword } = foundUser
      this.currentUser = userWithoutPassword
      localStorage.setItem("currentUser", JSON.stringify(userWithoutPassword))
      return true
    }
    return false
  }

  async register(userData) {
    const users = JSON.parse(localStorage.getItem("users") || "[]")
    const existingUser = users.find((u) => u.correo === userData.email)

    if (existingUser) {
      return false // Usuario ya existe
    }

    const newUser = {
      id: Date.now().toString(),
      nombre: userData.nombre,
      correo: userData.email,
      telefono: userData.telefono,
      pais: userData.pais,
      rol: "estudiante",
      password: userData.password,
    }

    users.push(newUser)
    localStorage.setItem("users", JSON.stringify(users))

    const { password: _, ...userWithoutPassword } = newUser
    this.currentUser = userWithoutPassword
    localStorage.setItem("currentUser", JSON.stringify(userWithoutPassword))
    return true
  }

  logout() {
    this.currentUser = null
    localStorage.removeItem("currentUser")
  }

  getCurrentUser() {
    return this.currentUser
  }

  updateProfile(userData) {
    if (!this.currentUser) return false

    const updatedUser = { ...this.currentUser, ...userData }
    this.currentUser = updatedUser
    localStorage.setItem("currentUser", JSON.stringify(updatedUser))

    // Actualizar en la lista de usuarios
    const users = JSON.parse(localStorage.getItem("users") || "[]")
    const userIndex = users.findIndex((u) => u.id === this.currentUser.id)
    if (userIndex !== -1) {
      users[userIndex] = { ...users[userIndex], ...userData }
      localStorage.setItem("users", JSON.stringify(users))
    }
    return true
  }

  async changePassword(currentPassword, newPassword) {
    if (!this.currentUser) return false

    const users = JSON.parse(localStorage.getItem("users") || "[]")
    const userIndex = users.findIndex((u) => u.id === this.currentUser.id)

    if (userIndex === -1) return false

    // Verificar contraseña actual
    if (users[userIndex].password !== currentPassword) {
      return false
    }

    // Actualizar contraseña
    users[userIndex].password = newPassword
    localStorage.setItem("users", JSON.stringify(users))
    return true
  }

  deleteProfile() {
    if (!this.currentUser) return false

    // Eliminar de la lista de usuarios
    const users = JSON.parse(localStorage.getItem("users") || "[]")
    const filteredUsers = users.filter((u) => u.id !== this.currentUser.id)
    localStorage.setItem("users", JSON.stringify(filteredUsers))

    this.logout()
    return true
  }

  isAuthenticated() {
    return this.currentUser !== null
  }

  isAdmin() {
    return this.currentUser && this.currentUser.rol === "administrador"
  }
}

// Instancia global
const Auth = new AuthManager()
