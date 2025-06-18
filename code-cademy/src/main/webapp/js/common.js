// Global variables
let currentUser = null
const users = JSON.parse(localStorage.getItem("users")) || []
let courses = JSON.parse(localStorage.getItem("courses")) || []
const enrollments = JSON.parse(localStorage.getItem("enrollments")) || []

// Initialize app
document.addEventListener("DOMContentLoaded", () => {
  initializeApp()
  loadSampleData()
  checkAuthStatus()
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
        descripcion: "Aprende los fundamentos de JavaScript desde cero. Domina variables, funciones, objetos y más.",
        categoria: "Frontend",
        estado: "Activo",
        imagen: "https://images.unsplash.com/photo-1627398242454-45a1465c2479?w=400&h=250&fit=crop",
        duracion: 40,
        nivel: "Principiante",
        precio: 99,
        instructor: "Carlos Mendez",
      },
      {
        id_curso: 2,
        nombre: "React Development",
        descripcion: "Construye aplicaciones web modernas con React. Aprende hooks, componentes y estado global.",
        categoria: "Frontend",
        estado: "Activo",
        imagen: "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=400&h=250&fit=crop",
        duracion: 60,
        nivel: "Intermedio",
        precio: 149,
        instructor: "Ana García",
      },
      {
        id_curso: 3,
        nombre: "Node.js Backend",
        descripcion: "Desarrollo de APIs y servicios backend con Node.js. Express, MongoDB y autenticación.",
        categoria: "Backend",
        estado: "Activo",
        imagen: "https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=400&h=250&fit=crop",
        duracion: 50,
        nivel: "Intermedio",
        precio: 129,
        instructor: "Miguel Torres",
      },
      {
        id_curso: 4,
        nombre: "Python Data Science",
        descripcion: "Análisis de datos y machine learning con Python. Pandas, NumPy, Matplotlib y Scikit-learn.",
        categoria: "Data Science",
        estado: "Activo",
        imagen: "https://images.unsplash.com/photo-1526379879527-8559ecfcaec0?w=400&h=250&fit=crop",
        duracion: 80,
        nivel: "Avanzado",
        precio: 199,
        instructor: "Laura Rodríguez",
      },
      {
        id_curso: 5,
        nombre: "React Native Mobile",
        descripcion: "Desarrollo de aplicaciones móviles con React Native. iOS y Android desde una base de código.",
        categoria: "Mobile",
        estado: "Activo",
        imagen: "https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?w=400&h=250&fit=crop",
        duracion: 70,
        nivel: "Intermedio",
        precio: 169,
        instructor: "David López",
      },
      {
        id_curso: 6,
        nombre: "Vue.js Framework",
        descripcion: "Aprende Vue.js para crear interfaces de usuario reactivas. Vuex, Vue Router y Composition API.",
        categoria: "Frontend",
        estado: "Activo",
        imagen: "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?w=400&h=250&fit=crop",
        duracion: 45,
        nivel: "Intermedio",
        precio: 119,
        instructor: "Sofia Martín",
      },
      {
        id_curso: 7,
        nombre: "Machine Learning con TensorFlow",
        descripcion: "Aprende machine learning y deep learning con TensorFlow. Redes neuronales y modelos predictivos.",
        categoria: "Data Science",
        estado: "Activo",
        imagen: "https://images.unsplash.com/photo-1555949963-aa79dcee981c?w=400&h=250&fit=crop",
        duracion: 90,
        nivel: "Avanzado",
        precio: 249,
        instructor: "Roberto Silva",
      },
      {
        id_curso: 8,
        nombre: "Flutter Development",
        descripcion: "Desarrollo de apps móviles multiplataforma con Flutter. Dart, widgets y arquitectura limpia.",
        categoria: "Mobile",
        estado: "Activo",
        imagen: "https://images.unsplash.com/photo-1551650975-87deedd944c3?w=400&h=250&fit=crop",
        duracion: 65,
        nivel: "Intermedio",
        precio: 159,
        instructor: "Carmen Ruiz",
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

// UI Update functions
function updateUIForLoggedInUser() {
  const navGuest = document.querySelector(".nav-guest")
  const navUser = document.querySelector(".nav-user")
  const userName = document.getElementById("userName")

  if (navGuest) navGuest.style.display = "none"
  if (navUser) navUser.style.display = "flex"
  if (userName) userName.textContent = currentUser.nombre

  const adminOnly = document.querySelector(".admin-only")
  if (adminOnly) {
    if (currentUser.rol === "admin") {
      adminOnly.style.display = "block"
    } else {
      adminOnly.style.display = "none"
    }
  }
}

function updateUIForGuest() {
  const navGuest = document.querySelector(".nav-guest")
  const navUser = document.querySelector(".nav-user")

  if (navGuest) navGuest.style.display = "flex"
  if (navUser) navUser.style.display = "none"
}

function checkAuthStatus() {
  const savedUser = localStorage.getItem("currentUser")
  if (savedUser) {
    currentUser = JSON.parse(savedUser)
    updateUIForLoggedInUser()
  }
}

// Authentication functions
function logout() {
  currentUser = null
  localStorage.removeItem("currentUser")
  updateUIForGuest()
  showAlert("Sesión cerrada exitosamente", "info")
  // Redirect to home page
  window.location.href = "index.html"
}

// Modal functions
function showLogin() {
  closeModal("registerModal")
  document.getElementById("loginModal").style.display = "block"
}

function showRegister() {
  closeModal("loginModal")
  document.getElementById("registerModal").style.display = "block"
}

function closeModal(modalId) {
  const modal = document.getElementById(modalId)
  if (modal) {
    modal.style.display = "none"
  }
}

// Course functions
function createCourseCard(course, showActions = true) {
  const card = document.createElement("div")
  card.className = "course-card"

  const isEnrolled =
    currentUser && enrollments.some((e) => e.id_usuario === currentUser.id_usuario && e.id_curso === course.id_curso)

  const levelColors = {
    Principiante: "level-beginner",
    Intermedio: "level-intermediate",
    Avanzado: "level-advanced",
  }

  card.innerHTML = `
        <div class="course-image-container">
            <img src="${course.imagen}" alt="${course.nombre}" class="course-image" onerror="this.src='/placeholder.svg?height=250&width=400'">
            <div class="course-level ${levelColors[course.nivel] || "level-beginner"}">${course.nivel}</div>
        </div>
        <div class="course-content">
            <div class="course-header">
                <h3 class="course-title">${course.nombre}</h3>
                <div class="course-price">$${course.precio}</div>
            </div>
            <p class="course-description">${course.descripcion}</p>
            <div class="course-meta">
                <div class="course-meta-item">
                    <i class="fas fa-tag"></i>
                    <span class="course-category">${course.categoria}</span>
                </div>
                <div class="course-meta-item">
                    <i class="fas fa-clock"></i>
                    <span class="course-duration">${course.duracion}h</span>
                </div>
                <div class="course-meta-item">
                    <i class="fas fa-user"></i>
                    <span class="course-instructor">${course.instructor}</span>
                </div>
            </div>
            ${
              showActions
                ? `
                <div class="course-actions">
                    ${
                      currentUser
                        ? isEnrolled
                          ? '<button class="btn btn-success btn-full" disabled><i class="fas fa-check"></i> Ya inscrito</button>'
                          : `<button class="btn btn-primary btn-full" onclick="enrollInCourse(${course.id_curso})"><i class="fas fa-plus"></i> Inscribirse</button>`
                        : '<button class="btn btn-primary btn-full" onclick="showLogin()"><i class="fas fa-sign-in-alt"></i> Iniciar Sesión</button>'
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

  showAlert("¡Inscripción exitosa! Ahora puedes acceder al curso.", "success")

  // Reload courses if we're on the courses page
  if (typeof loadCourses === "function") {
    try {
      loadCourses()
    } catch (e) {
      console.warn("loadCourses function not defined on this page.")
    }
  }

  // Reload featured courses if we're on the home page
  if (typeof loadFeaturedCourses === "function") {
    try {
      loadFeaturedCourses()
    } catch (e) {
      console.warn("loadFeaturedCourses function not defined on this page.")
    }
  }
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
  alert.innerHTML = `
    <div class="alert-content">
      <i class="fas fa-${type === "success" ? "check-circle" : type === "error" ? "exclamation-circle" : "info-circle"}"></i>
      <span>${message}</span>
    </div>
    <button class="alert-close" onclick="this.parentElement.remove()">
      <i class="fas fa-times"></i>
    </button>
  `

  // Insert at the top of the main content
  const main = document.querySelector(".main")
  if (main) {
    main.insertBefore(alert, main.firstChild)
  }

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

// Check if user needs to be redirected for protected pages
function checkAuthForProtectedPage() {
  if (!currentUser) {
    showAlert("Debes iniciar sesión para acceder a esta página", "error")
    setTimeout(() => {
      window.location.href = "index.html"
    }, 2000)
    return false
  }
  return true
}

// Check if user is admin for admin pages
function checkAdminAccess() {
  if (!currentUser || currentUser.rol !== "admin") {
    showAlert("Acceso denegado. Solo administradores pueden acceder a esta página.", "error")
    setTimeout(() => {
      window.location.href = "index.html"
    }, 2000)
    return false
  }
  return true
}
