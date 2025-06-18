// Global variables
let currentUser = null
let users = JSON.parse(localStorage.getItem("users")) || []
let courses = JSON.parse(localStorage.getItem("courses")) || []
let enrollments = JSON.parse(localStorage.getItem("enrollments")) || []

// Initialize app
document.addEventListener("DOMContentLoaded", () => {
  initializeApp()
  loadSampleData()
  checkAuthStatus()
  loadFeaturedCourses()
  setupEventListeners()
})

// Initialize application
function initializeApp() {
  // Check if user is logged in
  const savedUser = localStorage.getItem("currentUser")
  if (savedUser) {
    currentUser = JSON.parse(savedUser)
    updateUIForLoggedInUser()
  }
}

// Load sample data if none exists
function loadSampleData() {
  if (courses.length === 0) {
    courses = [
      {
        id_curso: 1,
        nombre: "JavaScript Fundamentals",
        descripcion: "Aprende los fundamentos de JavaScript desde cero",
        categoria: "Frontend",
        estado: "Activo",
        imagen: "/placeholder.svg?height=200&width=300",
        duracion: 40,
      },
      {
        id_curso: 2,
        nombre: "React Development",
        descripcion: "Construye aplicaciones web modernas con React",
        categoria: "Frontend",
        estado: "Activo",
        imagen: "/placeholder.svg?height=200&width=300",
        duracion: 60,
      },
      {
        id_curso: 3,
        nombre: "Node.js Backend",
        descripcion: "Desarrollo de APIs y servicios backend con Node.js",
        categoria: "Backend",
        estado: "Activo",
        imagen: "/placeholder.svg?height=200&width=300",
        duracion: 50,
      },
      {
        id_curso: 4,
        nombre: "Python Data Science",
        descripcion: "Análisis de datos y machine learning con Python",
        categoria: "Data Science",
        estado: "Activo",
        imagen: "/placeholder.svg?height=200&width=300",
        duracion: 80,
      },
    ]
    localStorage.setItem("courses", JSON.stringify(courses))
  }

  // Create admin user if none exists
  if (!users.find((user) => user.rol === "admin")) {
    const adminUser = {
      id_usuario: Date.now(),
      nombre: "Administrador",
      correo: "admin@codeacademy.com",
      telefono: "123456789",
      pais: "España",
      rol: "admin",
      password: "admin123",
    }
    users.push(adminUser)
    localStorage.setItem("users", JSON.stringify(users))
  }
}

// Setup event listeners
function setupEventListeners() {
  // Login form
  document.getElementById("loginForm").addEventListener("submit", handleLogin)

  // Register form
  document.getElementById("registerForm").addEventListener("submit", handleRegister)

  // Profile form
  document.getElementById("profileForm").addEventListener("submit", handleProfileUpdate)

  // Course form
  document.getElementById("courseForm").addEventListener("submit", handleCourseSubmit)
}

// Authentication functions
function handleLogin(e) {
  e.preventDefault()

  const email = document.getElementById("loginEmail").value
  const password = document.getElementById("loginPassword").value

  const user = users.find((u) => u.correo === email && u.password === password)

  if (user) {
    currentUser = user
    localStorage.setItem("currentUser", JSON.stringify(user))
    updateUIForLoggedInUser()
    showAlert("Inicio de sesión exitoso", "success")
    showHome()
  } else {
    showAlert("Credenciales incorrectas", "error")
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
  if (password !== confirmPassword) {
    showAlert("Las contraseñas no coinciden", "error")
    return
  }

  if (users.find((u) => u.correo === email)) {
    showAlert("El correo ya está registrado", "error")
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

  showAlert("Registro exitoso. Puedes iniciar sesión ahora.", "success")
  showLogin()
}

function logout() {
  currentUser = null
  localStorage.removeItem("currentUser")
  updateUIForGuest()
  showHome()
  showAlert("Sesión cerrada exitosamente", "info")
}

// UI Update functions
function updateUIForLoggedInUser() {
  document.querySelector(".nav-guest").style.display = "none"
  document.querySelector(".nav-user").style.display = "flex"
  document.getElementById("userName").textContent = currentUser.nombre

  if (currentUser.rol === "admin") {
    document.querySelector(".admin-only").style.display = "block"
  } else {
    document.querySelector(".admin-only").style.display = "none"
  }
}

function updateUIForGuest() {
  document.querySelector(".nav-guest").style.display = "flex"
  document.querySelector(".nav-user").style.display = "none"
}

function checkAuthStatus() {
  const savedUser = localStorage.getItem("currentUser")
  if (savedUser) {
    currentUser = JSON.parse(savedUser)
    updateUIForLoggedInUser()
  }
}

// Navigation functions
function showSection(sectionId) {
  // Hide all sections
  document.querySelectorAll(".section").forEach((section) => {
    section.classList.remove("active")
  })

  // Show target section
  document.getElementById(sectionId).classList.add("active")
}

function showHome() {
  showSection("home")
  loadFeaturedCourses()
}

function showLogin() {
  showSection("login")
}

function showRegister() {
  showSection("register")
}

function showCourses() {
  showSection("courses")
  loadCourses()
}

function showProfile() {
  if (!currentUser) {
    showAlert("Debes iniciar sesión primero", "error")
    showLogin()
    return
  }

  showSection("profile")
  loadProfileData()
}

function showMyCourses() {
  if (!currentUser) {
    showAlert("Debes iniciar sesión primero", "error")
    showLogin()
    return
  }

  showSection("myCourses")
  loadMyCourses()
}

function showAdminPanel() {
  if (!currentUser || currentUser.rol !== "admin") {
    showAlert("Acceso denegado", "error")
    return
  }

  showSection("adminPanel")
  loadAdminCourses()
  loadEnrollments()
}

// Course functions
function loadFeaturedCourses() {
  const featuredCourses = courses.filter((course) => course.estado === "Activo").slice(0, 3)
  const container = document.getElementById("featuredCourses")
  container.innerHTML = ""

  featuredCourses.forEach((course) => {
    const courseCard = createCourseCard(course, false)
    container.appendChild(courseCard)
  })
}

function loadCourses() {
  const container = document.getElementById("coursesGrid")
  container.innerHTML = ""

  const activeCourses = courses.filter((course) => course.estado === "Activo")

  activeCourses.forEach((course) => {
    const courseCard = createCourseCard(course, true)
    container.appendChild(courseCard)
  })
}

function createCourseCard(course, showActions = true) {
  const card = document.createElement("div")
  card.className = "course-card"

  const isEnrolled =
    currentUser && enrollments.some((e) => e.id_usuario === currentUser.id_usuario && e.id_curso === course.id_curso)

  card.innerHTML = `
        <img src="${course.imagen}" alt="${course.nombre}" class="course-image">
        <div class="course-content">
            <h3 class="course-title">${course.nombre}</h3>
            <p class="course-description">${course.descripcion}</p>
            <div class="course-meta">
                <span class="course-category">${course.categoria}</span>
                <span class="course-duration">${course.duracion} horas</span>
            </div>
            ${
              showActions
                ? `
                <div class="course-actions">
                    ${
                      currentUser
                        ? isEnrolled
                          ? '<button class="btn btn-secondary" disabled>Ya inscrito</button>'
                          : `<button class="btn btn-primary" onclick="enrollInCourse(${course.id_curso})">Inscribirse</button>`
                        : '<button class="btn btn-primary" onclick="showLogin()">Iniciar Sesión</button>'
                    }
                </div>
            `
                : ""
            }
        </div>
    `

  return card
}

function enrollInCourse(courseId) {
  if (!currentUser) {
    showAlert("Debes iniciar sesión primero", "error")
    showLogin()
    return
  }

  const isAlreadyEnrolled = enrollments.some((e) => e.id_usuario === currentUser.id_usuario && e.id_curso === courseId)

  if (isAlreadyEnrolled) {
    showAlert("Ya estás inscrito en este curso", "info")
    return
  }

  const newEnrollment = {
    id_inscripcion: Date.now(),
    id_usuario: currentUser.id_usuario,
    id_curso: courseId,
    fecha_inscripcion: new Date().toISOString().split("T")[0],
  }

  enrollments.push(newEnrollment)
  localStorage.setItem("enrollments", JSON.stringify(enrollments))

  showAlert("Inscripción exitosa", "success")
  loadCourses() // Reload to update button states
}

function loadMyCourses() {
  const container = document.getElementById("myCoursesGrid")
  container.innerHTML = ""

  const userEnrollments = enrollments.filter((e) => e.id_usuario === currentUser.id_usuario)
  const userCourses = userEnrollments
    .map((enrollment) => {
      return courses.find((course) => course.id_curso === enrollment.id_curso)
    })
    .filter((course) => course) // Filter out undefined courses

  if (userCourses.length === 0) {
    container.innerHTML = '<p class="text-center">No tienes cursos inscritos aún.</p>'
    return
  }

  userCourses.forEach((course) => {
    const courseCard = createCourseCard(course, false)
    container.appendChild(courseCard)
  })
}

function filterCourses() {
  const category = document.getElementById("categoryFilter").value
  const container = document.getElementById("coursesGrid")
  container.innerHTML = ""

  let filteredCourses = courses.filter((course) => course.estado === "Activo")

  if (category) {
    filteredCourses = filteredCourses.filter((course) => course.categoria === category)
  }

  filteredCourses.forEach((course) => {
    const courseCard = createCourseCard(course, true)
    container.appendChild(courseCard)
  })
}

// Profile functions
function loadProfileData() {
  document.getElementById("profileName").value = currentUser.nombre
  document.getElementById("profileEmail").value = currentUser.correo
  document.getElementById("profilePhone").value = currentUser.telefono
  document.getElementById("profileCountry").value = currentUser.pais
  document.getElementById("profileRole").value = currentUser.rol
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

// Admin functions
function showAdminTab(tabName) {
  // Update tab buttons
  document.querySelectorAll(".tab-btn").forEach((btn) => btn.classList.remove("active"))
  event.target.classList.add("active")

  // Update tab content
  document.querySelectorAll(".admin-tab").forEach((tab) => tab.classList.remove("active"))
  document.getElementById(`admin${tabName.charAt(0).toUpperCase() + tabName.slice(1)}`).classList.add("active")

  if (tabName === "courses") {
    loadAdminCourses()
  } else if (tabName === "enrollments") {
    loadEnrollments()
  }
}

function loadAdminCourses() {
  const container = document.getElementById("adminCoursesList")
  container.innerHTML = ""

  courses.forEach((course) => {
    const courseItem = document.createElement("div")
    courseItem.className = "admin-course-item"

    courseItem.innerHTML = `
            <div class="admin-course-info">
                <h4>${course.nombre}</h4>
                <p>${course.descripcion}</p>
                <p><strong>Categoría:</strong> ${course.categoria} | <strong>Duración:</strong> ${course.duracion}h | 
                <span class="status-${course.estado.toLowerCase()}">${course.estado}</span></p>
            </div>
            <div class="admin-course-actions">
                <button class="btn btn-primary" onclick="editCourse(${course.id_curso})">Editar</button>
                <button class="btn btn-danger" onclick="deleteCourse(${course.id_curso})">Eliminar</button>
            </div>
        `

    container.appendChild(courseItem)
  })
}

function showAddCourse() {
  document.getElementById("courseModalTitle").textContent = "Agregar Curso"
  document.getElementById("courseForm").reset()
  document.getElementById("courseId").value = ""
  document.getElementById("addCourseModal").style.display = "block"
}

function editCourse(courseId) {
  const course = courses.find((c) => c.id_curso === courseId)
  if (!course) return

  document.getElementById("courseModalTitle").textContent = "Editar Curso"
  document.getElementById("courseId").value = course.id_curso
  document.getElementById("courseName").value = course.nombre
  document.getElementById("courseDescription").value = course.descripcion
  document.getElementById("courseCategory").value = course.categoria
  document.getElementById("courseStatus").value = course.estado
  document.getElementById("courseImage").value = course.imagen
  document.getElementById("courseDuration").value = course.duracion

  document.getElementById("addCourseModal").style.display = "block"
}

function handleCourseSubmit(e) {
  e.preventDefault()

  const courseId = document.getElementById("courseId").value
  const courseData = {
    nombre: document.getElementById("courseName").value,
    descripcion: document.getElementById("courseDescription").value,
    categoria: document.getElementById("courseCategory").value,
    estado: document.getElementById("courseStatus").value,
    imagen: document.getElementById("courseImage").value,
    duracion: Number.parseInt(document.getElementById("courseDuration").value),
  }

  if (courseId) {
    // Edit existing course
    const courseIndex = courses.findIndex((c) => c.id_curso === Number.parseInt(courseId))
    if (courseIndex !== -1) {
      courses[courseIndex] = { ...courses[courseIndex], ...courseData }
      showAlert("Curso actualizado exitosamente", "success")
    }
  } else {
    // Add new course
    const newCourse = {
      id_curso: Date.now(),
      ...courseData,
    }
    courses.push(newCourse)
    showAlert("Curso agregado exitosamente", "success")
  }

  localStorage.setItem("courses", JSON.stringify(courses))
  closeModal("addCourseModal")
  loadAdminCourses()
  loadCourses() // Refresh public courses view
}

function deleteCourse(courseId) {
  if (confirm("¿Estás seguro de que quieres eliminar este curso?")) {
    courses = courses.filter((c) => c.id_curso !== courseId)
    localStorage.setItem("courses", JSON.stringify(courses))

    // Remove related enrollments
    enrollments = enrollments.filter((e) => e.id_curso !== courseId)
    localStorage.setItem("enrollments", JSON.stringify(enrollments))

    showAlert("Curso eliminado exitosamente", "success")
    loadAdminCourses()
    loadCourses() // Refresh public courses view
  }
}

function loadEnrollments() {
  const container = document.getElementById("enrollmentsList")
  container.innerHTML = ""

  if (enrollments.length === 0) {
    container.innerHTML = '<p class="text-center">No hay inscripciones registradas.</p>'
    return
  }

  enrollments.forEach((enrollment) => {
    const user = users.find((u) => u.id_usuario === enrollment.id_usuario)
    const course = courses.find((c) => c.id_curso === enrollment.id_curso)

    if (user && course) {
      const enrollmentItem = document.createElement("div")
      enrollmentItem.className = "enrollment-item"

      enrollmentItem.innerHTML = `
                <div class="enrollment-info">
                    <h4>${user.nombre}</h4>
                    <p><strong>Curso:</strong> ${course.nombre}</p>
                    <p><strong>Email:</strong> ${user.correo} | <strong>País:</strong> ${user.pais}</p>
                </div>
                <div class="enrollment-date">
                    ${new Date(enrollment.fecha_inscripcion).toLocaleDateString()}
                </div>
            `

      container.appendChild(enrollmentItem)
    }
  })
}

// Modal functions
function closeModal(modalId) {
  document.getElementById(modalId).style.display = "none"
}

// Utility functions
function showAlert(message, type = "info") {
  // Remove existing alerts
  const existingAlert = document.querySelector(".alert")
  if (existingAlert) {
    existingAlert.remove()
  }

  const alert = document.createElement("div")
  alert.className = `alert alert-${type}`
  alert.textContent = message

  // Insert at the top of the main content
  const main = document.querySelector(".main")
  main.insertBefore(alert, main.firstChild)

  // Auto remove after 5 seconds
  setTimeout(() => {
    if (alert.parentNode) {
      alert.remove()
    }
  }, 5000)
}

// Close modals when clicking outside
window.onclick = (event) => {
  const modals = document.querySelectorAll(".modal")
  modals.forEach((modal) => {
    if (event.target === modal) {
      modal.style.display = "none"
    }
  })
}
