// Import necessary modules (assuming these are defined elsewhere)
// For example:
// import { Auth, Data, showToast, initTabs, validateForm } from './utils.js';

// Main application logic

document.addEventListener("DOMContentLoaded", async () => {
  console.log("DOM loaded, starting initialization...")
  
  // Wait for authentication check to complete
  await Auth.checkSessionStatus()
  console.log("Session status check completed")
  
  // Initialize authentication check
  checkAuthState()

  // Initialize page-specific functionality
  initPageFunctionality()

  // Initialize common components
  initCommonComponents()
})

function checkAuthState() {
  const currentUser = Auth.getCurrentUser()
  console.log("checkAuthState - currentUser:", currentUser)
  updateNavbarAuth(currentUser)

  // Redirect if authentication required
  const protectedPages = ["dashboard", "profile.jsp"]
  const currentPage = window.location.pathname.split("/").pop()
  console.log("checkAuthState - currentPage:", currentPage)

  if (protectedPages.includes(currentPage) && !currentUser) {
    console.log("Usuario no autenticado, redirigiendo a login")
    window.location.href = "login.jsp"
    return
  }

  // Redirect authenticated users away from auth pages
  const authPages = ["login.jsp", "register.jsp"]
  if (authPages.includes(currentPage) && currentUser) {
    console.log("Usuario autenticado, redirigiendo según rol")
    if (currentUser.isAdmin) {
      window.location.href = "dashboard"
    } else {
      window.location.href = "index.jsp"
    }
    return
  }
}

function updateNavbarAuth(user) {
  const navAuth = document.getElementById("navAuth")
  if (!navAuth) return

  if (user) {
    navAuth.innerHTML = `
            <div class="user-menu">
                <span class="user-name">${user.nombre}</span>
                ${user.isAdmin ? '<a href="dashboard" class="btn btn-ghost">Dashboard</a>' : ''}
                <a href="profile.jsp" class="btn btn-ghost">Perfil</a>
                <button onclick="handleLogout()" class="btn btn-ghost">Cerrar Sesión</button>
            </div>
        `
  } else {
    navAuth.innerHTML = `
            <a href="login.jsp" class="btn btn-ghost">Iniciar Sesión</a>
            <a href="register.jsp" class="btn btn-primary">Registrarse</a>
        `
  }
}

function handleLogout() {
  Auth.logout()
  showToast("Sesión cerrada", "Has cerrado sesión correctamente.", "success")
  setTimeout(() => {
    window.location.href = "index.jsp"
  }, 1000)
}

function initPageFunctionality() {
  const currentPage = window.location.pathname.split("/").pop()

  switch (currentPage) {
    // case "dashboard":
    //   initDashboard()
    //   break
    case "profile.jsp":
      initProfile()
      break
    case "contact.jsp":
      initContact()
      break
  }
}

function initCommonComponents() {
  // Initialize tabs
  const tabsContainers = document.querySelectorAll(".tabs")
  tabsContainers.forEach(initTabs)

  // Initialize form validation
  const forms = document.querySelectorAll("form")
  forms.forEach((form) => {
    form.addEventListener("submit", (e) => {
      if (!validateForm(form)) {
        e.preventDefault()
        showToast("Error", "Por favor completa todos los campos requeridos.", "error")
      }
    })
  })
}



function loadCategoryFilter(courses) {
  const categories = [...new Set(courses.map((course) => course.categoria))]
  const categoryFilter = document.getElementById("categoryFilter")

  categories.forEach((category) => {
    const option = document.createElement("option")
    option.value = category
    option.textContent = category
    categoryFilter.appendChild(option)
  })
}

function displayCourses(courses, userEnrollments) {
  const coursesGrid = document.getElementById("coursesGrid")
  const noCourses = document.getElementById("noCourses")

  if (courses.length === 0) {
    coursesGrid.style.display = "none"
    noCourses.style.display = "block"
    return
  }

  coursesGrid.style.display = "grid"
  noCourses.style.display = "none"

  const enrolledCourseIds = userEnrollments.map((enrollment) => enrollment.id_curso)

  coursesGrid.innerHTML = courses
    .map(
      (course) => `
        <div class="course-card">
            <div class="course-image">
                <img src="${course.imagen}" alt="${course.nombre}">
            </div>
            <div class="course-content">
                <div class="course-meta">
                    <span class="course-category">${course.categoria}</span>
                    <span class="course-duration">
                        <svg class="duration-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <circle cx="12" cy="12" r="10"/>
                            <polyline points="12,6 12,12 16,14"/>
                        </svg>
                        ${course.duracion}
                    </span>
                </div>
                <h3 class="course-title">${course.nombre}</h3>
                <p class="course-description">${course.descripcion}</p>
                <div class="course-actions">
                    ${getEnrollButton(course.id_curso, enrolledCourseIds.includes(course.id_curso))}
                </div>
            </div>
        </div>
    `,
    )
    .join("")
}

function getEnrollButton(courseId, isEnrolled) {
  const currentUser = Auth.getCurrentUser()

  if (!currentUser) {
    return `<a href="login.jsp" class="btn btn-primary btn-full">Iniciar Sesión para Inscribirse</a>`
  }

  if (isEnrolled) {
    return `<button class="btn btn-secondary btn-full" disabled>Ya Inscrito</button>`
  }

  return `<button onclick="enrollInCourse('${courseId}')" class="btn btn-primary btn-full">Inscribirse</button>`
}

// Dashboard functionality
function initDashboard() {
  const currentUser = Auth.getCurrentUser()
  if (!currentUser) return

  const dashboardContent = document.getElementById("dashboardContent")

  if (currentUser.rol === "administrador") {
    loadAdminDashboard(dashboardContent)
  } else {
    loadStudentDashboard(dashboardContent, currentUser)
  }
}

function loadStudentDashboard(container, user) {
  const userEnrollments = Data.getUserEnrollments(user.id)
  const enrolledCourses = userEnrollments
    .map((enrollment) => {
      const course = Data.getCourseById(enrollment.id_curso)
      return { ...enrollment, course }
    })
    .filter((item) => item.course)

  const allCourses = Data.getActiveCourses()

  container.innerHTML = `
        <div class="dashboard-header">
            <h1 class="dashboard-welcome">¡Hola, ${user.nombre}!</h1>
            <p class="dashboard-subtitle">Bienvenido a tu dashboard de estudiante</p>
        </div>

        <div class="dashboard-stats">
            <div class="stat-card">
                <div class="stat-header">
                    <span class="stat-title">Cursos Inscritos</span>
                    <svg class="stat-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/>
                        <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
                    </svg>
                </div>
                <div class="stat-value">${enrolledCourses.length}</div>
            </div>

            <div class="stat-card">
                <div class="stat-header">
                    <span class="stat-title">Cursos Disponibles</span>
                    <svg class="stat-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                    </svg>
                </div>
                <div class="stat-value">${allCourses.length}</div>
            </div>

            <div class="stat-card">
                <div class="stat-header">
                    <span class="stat-title">Última Inscripción</span>
                    <svg class="stat-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                        <line x1="16" y1="2" x2="16" y2="6"/>
                        <line x1="8" y1="2" x2="8" y2="6"/>
                        <line x1="3" y1="10" x2="21" y2="10"/>
                    </svg>
                </div>
                <div class="stat-value">
                    ${
                      userEnrollments.length > 0
                        ? new Date(userEnrollments[userEnrollments.length - 1].fecha_inscripcion).toLocaleDateString()
                        : "N/A"
                    }
                </div>
            </div>
        </div>

        <div class="dashboard-section">
            <div class="section-header">
                <h2>Mis Cursos</h2>
                <a href="cursos" class="btn btn-primary">Explorar Cursos</a>
            </div>

            ${
              enrolledCourses.length === 0
                ? `
                <div class="card">
                    <div class="card-content" style="text-align: center; padding: 3rem;">
                        <svg class="no-content-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" style="width: 3rem; height: 3rem; margin: 0 auto 1rem; color: #9ca3af;">
                            <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/>
                            <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
                        </svg>
                        <h3 style="font-size: 1.125rem; font-weight: 500; color: #1f2937; margin-bottom: 0.5rem;">No tienes cursos inscritos</h3>
                        <p style="color: #6b7280; margin-bottom: 1rem;">Explora nuestro catálogo y comienza tu aprendizaje</p>
                        <a href="cursos" class="btn btn-primary">Ver Cursos Disponibles</a>
                    </div>
                </div>
                `
                : `
                <div class="courses-grid">
                    ${enrolledCourses
                      .map(
                        (item) => `
                        <div class="course-card">
                            <div class="course-image">
                                <img src="${item.course.imagen}" alt="${item.course.nombre}">
                            </div>
                            <div class="course-content">
                                <div class="course-meta">
                                    <span class="course-category">${item.course.categoria}</span>
                                    <span class="course-duration">${item.course.duracion}</span>
                                </div>
                                <h3 class="course-title">${item.course.nombre}</h3>
                                <p class="course-description">
                                    Inscrito el ${formatDate(item.fecha_inscripcion)}
                                </p>
                                <div class="course-actions">
                                    <button class="btn btn-outline btn-full">Continuar Curso</button>
                                </div>
                            </div>
                        </div>
                    `,
                      )
                      .join("")}
                </div>
                `
            }
        </div>
    `
}

function loadAdminDashboard(container) {
  const allCourses = Data.getCourses()
  const allEnrollments = Data.getEnrollments()
  const activeCourses = allCourses.filter((c) => c.estado === "activo")

  container.innerHTML = `
        <div class="dashboard-header">
            <h1 class="dashboard-welcome">Panel de Administración</h1>
            <p class="dashboard-subtitle">Gestiona cursos e inscripciones</p>
        </div>

        <div class="dashboard-stats">
            <div class="stat-card">
                <div class="stat-header">
                    <span class="stat-title">Total Cursos</span>
                    <svg class="stat-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/>
                        <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
                    </svg>
                </div>
                <div class="stat-value">${allCourses.length}</div>
            </div>

            <div class="stat-card">
                <div class="stat-header">
                    <span class="stat-title">Total Inscripciones</span>
                    <svg class="stat-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                    </svg>
                </div>
                <div class="stat-value">${allEnrollments.length}</div>
            </div>

            <div class="stat-card">
                <div class="stat-header">
                    <span class="stat-title">Cursos Activos</span>
                    <svg class="stat-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                        <line x1="16" y1="2" x2="16" y2="6"/>
                        <line x1="8" y1="2" x2="8" y2="6"/>
                        <line x1="3" y1="10" x2="21" y2="10"/>
                    </svg>
                </div>
                <div class="stat-value">${activeCourses.length}</div>
            </div>
        </div>

        <div class="tabs admin-tabs">
            <div class="tabs-list">
                <button class="tabs-trigger active" data-target="courses">Gestión de Cursos</button>
                <button class="tabs-trigger" data-target="enrollments">Ver Inscripciones</button>
            </div>

            <div class="tabs-content active" data-content="courses">
                <div class="course-management">
                    <h2>Cursos</h2>
                    <button onclick="openCourseModal()" class="btn btn-primary">Nuevo Curso</button>
                </div>

                <div class="course-grid">
                    ${allCourses
                      .map(
                        (course) => `
                        <div class="admin-course-card">
                            <div class="admin-course-image">
                                <img src="${course.imagen}" alt="${course.nombre}">
                            </div>
                            <div class="admin-course-content">
                                <div class="admin-course-meta">
                                    <span class="badge ${
                                      course.estado === "activo" ? "badge-success" : "badge-secondary"
                                    }">${course.estado}</span>
                                    <span class="badge badge-secondary">${course.categoria}</span>
                                </div>
                                <h3 class="course-title">${course.nombre}</h3>
                                <p class="course-description">${course.descripcion}</p>
                                <p style="color: #6b7280; font-size: 0.875rem;">Duración: ${course.duracion}</p>
                                <div class="admin-course-actions">
                                    <button onclick="editCourse('${course.id_curso}')" class="btn btn-outline">Editar</button>
                                    <button onclick="deleteCourse('${course.id_curso}')" class="btn btn-destructive">Eliminar</button>
                                </div>
                            </div>
                        </div>
                    `,
                      )
                      .join("")}
                </div>
            </div>

            <div class="tabs-content" data-content="enrollments">
                <h2>Inscripciones</h2>
                ${loadEnrollmentsList()}
            </div>
        </div>

        <!-- Course Modal -->
        <div id="courseModal" class="modal-overlay" style="display: none;">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title" id="modalTitle">Crear Nuevo Curso</h2>
                    <p class="modal-description">Completa la información para crear un nuevo curso</p>
                </div>
                <form id="courseForm">
                    <div class="form-group">
                        <label for="courseName" class="form-label">Nombre del Curso</label>
                        <input type="text" id="courseName" name="nombre" class="form-input" required>
                    </div>
                    <div class="form-group">
                        <label for="courseCategory" class="form-label">Categoría</label>
                        <input type="text" id="courseCategory" name="categoria" class="form-input" required>
                    </div>
                    <div class="form-group">
                        <label for="courseDescription" class="form-label">Descripción</label>
                        <textarea id="courseDescription" name="descripcion" class="form-textarea" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="courseDuration" class="form-label">Duración</label>
                        <input type="text" id="courseDuration" name="duracion" class="form-input" placeholder="ej: 8 semanas" required>
                    </div>
                    <div class="form-group">
                        <label for="courseStatus" class="form-label">Estado</label>
                        <select id="courseStatus" name="estado" class="form-select" required>
                            <option value="activo">Activo</option>
                            <option value="inactivo">Inactivo</option>
                        </select>
                    </div>
                    <div class="modal-actions">
                        <button type="button" onclick="closeCourseModal()" class="btn btn-ghost">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Crear Curso</button>
                    </div>
                </form>
            </div>
        </div>
    `

  // Initialize tabs
  initTabs(container.querySelector(".tabs"))

  // Initialize course form
  initCourseForm()
}

function loadEnrollmentsList() {
  const enrollmentsWithCourses = Data.getEnrollmentsWithCourses()

  if (enrollmentsWithCourses.length === 0) {
    return `
            <div class="card">
                <div class="card-content" style="text-align: center; padding: 3rem;">
                    <svg class="no-content-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" style="width: 3rem; height: 3rem; margin: 0 auto 1rem; color: #9ca3af;">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                        <circle cx="9" cy="7" r="4"/>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                    </svg>
                    <h3 style="font-size: 1.125rem; font-weight: 500; color: #1f2937; margin-bottom: 0.5rem;">No hay inscripciones</h3>
                    <p style="color: #6b7280;">Aún no hay estudiantes inscritos en los cursos</p>
                </div>
            </div>
        `
  }

  return `
        <div class="enrollment-list">
            ${enrollmentsWithCourses
              .map(
                (item) => `
                <div class="enrollment-item">
                    <div class="enrollment-header">
                        <div>
                            <h3 class="enrollment-course">${item.course.nombre}</h3>
                            <p class="enrollment-details">
                                Usuario ID: ${item.id_usuario} • Inscrito el ${formatDate(item.fecha_inscripcion)}
                            </p>
                        </div>
                        <span class="badge badge-secondary">${item.course.categoria}</span>
                    </div>
                </div>
            `,
              )
              .join("")}
        </div>
    `
}

// Course management functions
let editingCourseId = null

function openCourseModal(courseId = null) {
  const modal = document.getElementById("courseModal")
  const modalTitle = document.getElementById("modalTitle")
  const form = document.getElementById("courseForm")

  if (courseId) {
    // Edit mode
    editingCourseId = courseId
    const course = Data.getCourseById(courseId)
    modalTitle.textContent = "Editar Curso"

    // Populate form
    document.getElementById("courseName").value = course.nombre
    document.getElementById("courseCategory").value = course.categoria
    document.getElementById("courseDescription").value = course.descripcion
    document.getElementById("courseDuration").value = course.duracion
    document.getElementById("courseStatus").value = course.estado
  } else {
    // Create mode
    editingCourseId = null
    modalTitle.textContent = "Crear Nuevo Curso"
    form.reset()
  }

  modal.style.display = "flex"
}

function closeCourseModal() {
  const modal = document.getElementById("courseModal")
  modal.style.display = "none"
  editingCourseId = null
}

function initCourseForm() {
  const form = document.getElementById("courseForm")
  form.addEventListener("submit", (e) => {
    e.preventDefault()

    const formData = new FormData(e.target)
    const courseData = Object.fromEntries(formData)
    courseData.imagen = `https://via.placeholder.com/300x200/2563eb/ffffff?text=${encodeURIComponent(
      courseData.nombre,
    )}`

    if (editingCourseId) {
      // Update course
      Data.updateCourse(editingCourseId, courseData)
      showToast("Curso actualizado", "El curso ha sido actualizado correctamente.", "success")
    } else {
      // Create course
      Data.addCourse(courseData)
      showToast("Curso creado", "El nuevo curso ha sido creado correctamente.", "success")
    }

    closeCourseModal()
    // Reload dashboard
    initDashboard()
  })
}

// Global functions for admin
window.openCourseModal = openCourseModal
window.closeCourseModal = closeCourseModal

window.editCourse = (courseId) => {
  openCourseModal(courseId)
}

window.deleteCourse = (courseId) => {
  if (confirm("¿Estás seguro de que quieres eliminar este curso?")) {
    Data.deleteCourse(courseId)
    showToast("Curso eliminado", "El curso ha sido eliminado correctamente.", "success")
    initDashboard()
  }
}

// Profile functionality
function initProfile() {
  const currentUser = Auth.getCurrentUser()
  if (!currentUser) return

  // Populate profile form
  populateProfileForm(currentUser)

  // Handle profile form submission
  const profileForm = document.getElementById("profileForm")
  if (profileForm) {
    profileForm.addEventListener("submit", handleProfileUpdate)
  }

  // Handle password change form
  const passwordForm = document.getElementById("passwordForm")
  if (passwordForm) {
    passwordForm.addEventListener("submit", handlePasswordChange)
  }
}

function populateProfileForm(user) {
  const fields = ["nombre", "correo", "telefono", "pais"]
  fields.forEach((field) => {
    const input = document.getElementById(field)
    if (input && user[field]) {
      input.value = user[field]
    }
  })

  // Show role badge
  const roleDisplay = document.getElementById("roleDisplay")
  if (roleDisplay) {
    roleDisplay.innerHTML = `
            <span class="badge ${user.rol === "administrador" ? "badge-primary" : "badge-secondary"}">
                ${user.rol === "administrador" ? "Administrador" : "Estudiante"}
            </span>
            <span style="font-size: 0.875rem; color: #6b7280; margin-left: 0.5rem;">
                ${user.rol === "administrador" ? "Tienes acceso completo al sistema" : "Puedes inscribirte a cursos"}
            </span>
        `
  }
}

async function handleProfileUpdate(e) {
  e.preventDefault()

  const formData = new FormData(e.target)
  const userData = Object.fromEntries(formData)
  const submitBtn = e.target.querySelector('button[type="submit"]')

  setLoading(submitBtn, true)

  try {
    const success = Auth.updateProfile(userData)
    if (success) {
      showToast("Perfil actualizado", "Tu información ha sido actualizada correctamente.", "success")
      // Update navbar
      updateNavbarAuth(Auth.getCurrentUser())
    } else {
      showToast("Error", "No se pudo actualizar el perfil.", "error")
    }
  } catch (error) {
    showToast("Error", "Ocurrió un error inesperado.", "error")
  } finally {
    setLoading(submitBtn, false)
  }
}

async function handlePasswordChange(e) {
  e.preventDefault()

  const formData = new FormData(e.target)
  const passwordData = Object.fromEntries(formData)
  const submitBtn = e.target.querySelector('button[type="submit"]')

  if (passwordData.newPassword !== passwordData.confirmPassword) {
    showToast("Error", "Las nuevas contraseñas no coinciden.", "error")
    return
  }

  if (!validatePassword(passwordData.newPassword)) {
    showToast("Error", "La nueva contraseña debe tener al menos 6 caracteres.", "error")
    return
  }

  setLoading(submitBtn, true)

  try {
    const success = await Auth.changePassword(passwordData.currentPassword, passwordData.newPassword)
    if (success) {
      showToast("Contraseña actualizada", "Tu contraseña ha sido cambiada correctamente.", "success")
      e.target.reset()
    } else {
      showToast("Error", "La contraseña actual es incorrecta.", "error")
    }
  } catch (error) {
    showToast("Error", "Ocurrió un error al cambiar la contraseña.", "error")
  } finally {
    setLoading(submitBtn, false)
  }
}

// Global function for profile deletion
window.handleDeleteProfile = () => {
  if (confirm("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.")) {
    Auth.deleteProfile()
    showToast("Cuenta eliminada", "Tu cuenta ha sido eliminada correctamente.", "success")
    setTimeout(() => {
      window.location.href = "index.jsp"
    }, 1000)
  }
}

// Contact functionality
function initContact() {
  const contactForm = document.getElementById("contactForm")
  if (contactForm) {
    contactForm.addEventListener("submit", handleContactSubmit)
  }
}

async function handleContactSubmit(e) {
  e.preventDefault()

  const submitBtn = e.target.querySelector('button[type="submit"]')
  setLoading(submitBtn, true)

  // Simulate form submission
  setTimeout(() => {
    showToast("Mensaje enviado", "Gracias por contactarnos. Te responderemos pronto.", "success")
    e.target.reset()
    setLoading(submitBtn, false)
  }, 1000)
}

// Global functions
window.handleLogout = handleLogout

// Helper functions (These should ideally be in utils.js)
function formatDate(dateString) {
  const date = new Date(dateString)
  return date.toLocaleDateString()
}

function setLoading(button, isLoading) {
  if (isLoading) {
    button.disabled = true
    button.innerHTML = '<span class="loading loading-spinner"></span> Cargando...'
  } else {
    button.disabled = false
    button.textContent = button.textContent.replace("Cargando...", "").trim() // Remove loading text
  }
}

function validatePassword(password) {
  return password.length >= 6
}
