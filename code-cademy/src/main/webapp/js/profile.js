// Profile page specific functionality
document.addEventListener("DOMContentLoaded", () => {
  if (!checkAuthForProtectedPage()) return

  loadProfileData()
  setupProfileEventListeners()
})

function setupProfileEventListeners() {
  const profileForm = document.getElementById("profileForm")
  if (profileForm) {
    profileForm.addEventListener("submit", handleProfileUpdate)
  }
}

function loadProfileData() {
  if (!currentUser) return

  const profileName = document.getElementById("profileName")
  const profileEmail = document.getElementById("profileEmail")
  const profilePhone = document.getElementById("profilePhone")
  const profileCountry = document.getElementById("profileCountry")
  const profileRole = document.getElementById("profileRole")

  if (profileName) profileName.value = currentUser.nombre
  if (profileEmail) profileEmail.value = currentUser.correo
  if (profilePhone) profilePhone.value = currentUser.telefono
  if (profileCountry) profileCountry.value = currentUser.pais
  if (profileRole) profileRole.value = currentUser.rol
}

function handleProfileUpdate(e) {
  e.preventDefault()

  const updatedUser = {
    ...currentUser,
    nombre: document.getElementById("profileName").value,
    correo: document.getElementById("profileEmail").value,
    telefono: document.getElementById("profilePhone").value,
    pais: document.getElementById("profileCountry").value,
  }

  // Update in users array
  const userIndex = users.findIndex((u) => u.id_usuario === currentUser.id_usuario)
  if (userIndex !== -1) {
    users[userIndex] = updatedUser
    localStorage.setItem("users", JSON.stringify(users))

    // Update current user
    currentUser = updatedUser
    localStorage.setItem("currentUser", JSON.stringify(currentUser))

    updateUIForLoggedInUser()
    showAlert("Perfil actualizado exitosamente", "success")
  }
}

function deleteProfile() {
  if (confirm("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.")) {
    // Remove user from users array
    users = users.filter((u) => u.id_usuario !== currentUser.id_usuario)
    localStorage.setItem("users", JSON.stringify(users))

    // Remove user's enrollments
    enrollments = enrollments.filter((e) => e.id_usuario !== currentUser.id_usuario)
    localStorage.setItem("enrollments", JSON.stringify(enrollments))

    // Logout
    logout()
    showAlert("Cuenta eliminada exitosamente", "info")
  }
}
