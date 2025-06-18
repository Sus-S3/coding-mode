// Admin page specific functionality
document.addEventListener("DOMContentLoaded", () => {
  if (!checkAdminAccess()) return

  loadAdminCourses()
  loadEnrollments()
  setupAdminEventListeners()
})

function setupAdminEventListeners() {
  const courseForm = document.getElementById("courseForm")
  if (courseForm) {
    courseForm.addEventListener("submit", handleCourseSubmit)
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
  if (!container) return

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
  const modal = document.getElementById("addCourseModal")
  const title = document.getElementById("courseModalTitle")
  const form = document.getElementById("courseForm")
  const courseId = document.getElementById("courseId")

  if (title) title.textContent = "Agregar Curso"
  if (form) form.reset()
  if (courseId) courseId.value = ""
  if (modal) modal.style.display = "block"
}

function editCourse(courseId) {
  const course = courses.find((c) => c.id_curso === courseId)
  if (!course) return

  const modal = document.getElementById("addCourseModal")
  const title = document.getElementById("courseModalTitle")

  if (title) title.textContent = "Editar Curso"

  document.getElementById("courseId").value = course.id_curso
  document.getElementById("courseName").value = course.nombre
  document.getElementById("courseDescription").value = course.descripcion
  document.getElementById("courseCategory").value = course.categoria
  document.getElementById("courseStatus").value = course.estado
  document.getElementById("courseImage").value = course.imagen
  document.getElementById("courseDuration").value = course.duracion

  if (modal) modal.style.display = "block"
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
  }
}

function loadEnrollments() {
  const container = document.getElementById("enrollmentsList")
  if (!container) return

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
