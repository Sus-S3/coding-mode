// Courses page specific functionality
document.addEventListener("DOMContentLoaded", () => {
  loadCourses()
  setupCourseFilters()
})

// Mock data for courses (replace with actual data fetching)
const courses = [
  { id: 1, nombre: "Curso 1", categoria: "Programacion", estado: "Activo" },
  { id: 2, nombre: "Curso 2", categoria: "Diseño", estado: "Activo" },
  { id: 3, nombre: "Curso 3", categoria: "Programacion", estado: "Inactivo" },
  { id: 4, nombre: "Curso 4", categoria: "Marketing", estado: "Activo" },
]

// Mock function to create a course card (replace with actual card creation logic)
function createCourseCard(course, showDetails) {
  const card = document.createElement("div")
  card.classList.add("course-card")
  card.innerHTML = `<h3>${course.nombre}</h3><p>Categoria: ${course.categoria}</p><p>Estado: ${course.estado}</p>`
  return card
}

function loadCourses() {
  const container = document.getElementById("coursesGrid")
  if (!container) return

  container.innerHTML = ""

  const activeCourses = courses.filter((course) => course.estado === "Activo")

  if (activeCourses.length === 0) {
    container.innerHTML = `
      <div class="no-courses">
        <i class="fas fa-graduation-cap"></i>
        <h3>No hay cursos disponibles</h3>
        <p>Pronto tendremos nuevos cursos para ti</p>
      </div>
    `
    return
  }

  activeCourses.forEach((course, index) => {
    const courseCard = createCourseCard(course, true)

    // Add animation delay
    courseCard.style.opacity = "0"
    courseCard.style.transform = "translateY(30px)"

    setTimeout(() => {
      courseCard.style.transition = "all 0.5s ease"
      courseCard.style.opacity = "1"
      courseCard.style.transform = "translateY(0)"
    }, index * 100)

    container.appendChild(courseCard)
  })
}

function filterCourses() {
  const category = document.getElementById("categoryFilter").value
  const level = document.getElementById("levelFilter").value
  const container = document.getElementById("coursesGrid")

  if (!container) return

  container.innerHTML = ""

  let filteredCourses = courses.filter((course) => course.estado === "Activo")

  if (category) {
    filteredCourses = filteredCourses.filter((course) => course.categoria === category)
  }

  if (level) {
    filteredCourses = filteredCourses.filter((course) => course.nivel === level)
  }

  if (filteredCourses.length === 0) {
    container.innerHTML = `
      <div class="no-courses">
        <i class="fas fa-search"></i>
        <h3>No se encontraron cursos</h3>
        <p>Intenta con otros filtros</p>
        <button class="btn btn-primary" onclick="clearFilters()">Limpiar Filtros</button>
      </div>
    `
    return
  }

  filteredCourses.forEach((course, index) => {
    const courseCard = createCourseCard(course, true)

    // Add animation delay
    courseCard.style.opacity = "0"
    courseCard.style.transform = "translateY(30px)"

    setTimeout(() => {
      courseCard.style.transition = "all 0.5s ease"
      courseCard.style.opacity = "1"
      courseCard.style.transform = "translateY(0)"
    }, index * 100)

    container.appendChild(courseCard)
  })
}

function clearFilters() {
  document.getElementById("categoryFilter").value = ""
  document.getElementById("levelFilter").value = ""
  loadCourses()
}

function setupCourseFilters() {
  // Add search functionality
  const searchInput = document.getElementById("searchInput")
  if (searchInput) {
    searchInput.addEventListener("input", searchCourses)
  }
}

function searchCourses() {
  const searchTerm = document.getElementById("searchInput").value.toLowerCase()
  const category = document.getElementById("categoryFilter").value
  const level = document.getElementById("levelFilter").value
  const container = document.getElementById("coursesGrid")

  if (!container) return

  container.innerHTML = ""

  let filteredCourses = courses.filter((course) => course.estado === "Activo")

  if (searchTerm) {
    filteredCourses = filteredCourses.filter(
      (course) =>
        course.nombre.toLowerCase().includes(searchTerm) ||
        course.descripcion.toLowerCase().includes(searchTerm) ||
        course.instructor.toLowerCase().includes(searchTerm),
    )
  }

  if (category) {
    filteredCourses = filteredCourses.filter((course) => course.categoria === category)
  }

  if (level) {
    filteredCourses = filteredCourses.filter((course) => course.nivel === level)
  }

  if (filteredCourses.length === 0) {
    container.innerHTML = `
      <div class="no-courses">
        <i class="fas fa-search"></i>
        <h3>No se encontraron cursos</h3>
        <p>Intenta con otros términos de búsqueda</p>
        <button class="btn btn-primary" onclick="clearSearch()">Limpiar Búsqueda</button>
      </div>
    `
    return
  }

  filteredCourses.forEach((course, index) => {
    const courseCard = createCourseCard(course, true)

    // Add animation delay
    courseCard.style.opacity = "0"
    courseCard.style.transform = "translateY(30px)"

    setTimeout(() => {
      courseCard.style.transition = "all 0.5s ease"
      courseCard.style.opacity = "1"
      courseCard.style.transform = "translateY(0)"
    }, index * 100)

    container.appendChild(courseCard)
  })
}

function clearSearch() {
  document.getElementById("searchInput").value = ""
  document.getElementById("categoryFilter").value = ""
  document.getElementById("levelFilter").value = ""
  loadCourses()
}
