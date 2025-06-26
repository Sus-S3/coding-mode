class DataManager {
  constructor() {
    this.init()
  }

  init() {
    // Inicializar cursos si no existen
    if (!localStorage.getItem("cursos")) {
      const initialCourses = [
        {
          id_curso: "1",
          nombre: "JavaScript Fundamentals",
          descripcion: "Aprende los fundamentos de JavaScript desde cero",
          categoria: "Frontend",
          estado: "activo",
          imagen: "https://via.placeholder.com/300x200/2563eb/ffffff?text=JavaScript",
          duracion: "8 semanas",
        },
        {
          id_curso: "2",
          nombre: "React.js Avanzado",
          descripcion: "Domina React.js y sus conceptos avanzados",
          categoria: "Frontend",
          estado: "activo",
          imagen: "https://via.placeholder.com/300x200/61dafb/000000?text=React",
          duracion: "12 semanas",
        },
        {
          id_curso: "3",
          nombre: "Node.js Backend",
          descripcion: "Desarrollo de APIs con Node.js y Express",
          categoria: "Backend",
          estado: "activo",
          imagen: "https://via.placeholder.com/300x200/339933/ffffff?text=Node.js",
          duracion: "10 semanas",
        },
        {
          id_curso: "4",
          nombre: "Python para Principiantes",
          descripcion: "Introducción completa al lenguaje Python",
          categoria: "Backend",
          estado: "activo",
          imagen: "https://via.placeholder.com/300x200/3776ab/ffffff?text=Python",
          duracion: "6 semanas",
        },
      ]
      localStorage.setItem("cursos", JSON.stringify(initialCourses))
    }

    // Inicializar inscripciones si no existen
    if (!localStorage.getItem("inscripciones")) {
      localStorage.setItem("inscripciones", JSON.stringify([]))
    }
  }

  getCourses() {
    return JSON.parse(localStorage.getItem("cursos") || "[]")
  }

  getActiveCourses() {
    return this.getCourses().filter((course) => course.estado === "activo")
  }

  getCourseById(id) {
    const courses = this.getCourses()
    return courses.find((course) => course.id_curso === id)
  }

  addCourse(courseData) {
    const courses = this.getCourses()
    const newCourse = {
      ...courseData,
      id_curso: Date.now().toString(),
    }
    courses.push(newCourse)
    localStorage.setItem("cursos", JSON.stringify(courses))
    return newCourse
  }

  updateCourse(id, courseData) {
    const courses = this.getCourses()
    const courseIndex = courses.findIndex((course) => course.id_curso === id)
    if (courseIndex !== -1) {
      courses[courseIndex] = { ...courses[courseIndex], ...courseData }
      localStorage.setItem("cursos", JSON.stringify(courses))
      return courses[courseIndex]
    }
    return null
  }

  deleteCourse(id) {
    const courses = this.getCourses()
    const filteredCourses = courses.filter((course) => course.id_curso !== id)
    localStorage.setItem("cursos", JSON.stringify(filteredCourses))
    return true
  }

  getEnrollments() {
    return JSON.parse(localStorage.getItem("inscripciones") || "[]")
  }

  getUserEnrollments(userId) {
    const enrollments = this.getEnrollments()
    return enrollments.filter((enrollment) => enrollment.id_usuario === userId)
  }

  enrollInCourse(courseId, userId) {
    const enrollments = this.getEnrollments()
    const newEnrollment = {
      id_inscripcion: Date.now().toString(),
      id_usuario: userId,
      id_curso: courseId,
      fecha_inscripcion: new Date().toISOString(),
    }
    enrollments.push(newEnrollment)
    localStorage.setItem("inscripciones", JSON.stringify(enrollments))
    return newEnrollment
  }

  isUserEnrolled(courseId, userId) {
    const enrollments = this.getUserEnrollments(userId)
    return enrollments.some((enrollment) => enrollment.id_curso === courseId)
  }

  getEnrollmentsWithCourses() {
    const enrollments = this.getEnrollments()
    const courses = this.getCourses()

    return enrollments
      .map((enrollment) => {
        const course = courses.find((c) => c.id_curso === enrollment.id_curso)
        return { ...enrollment, course }
      })
      .filter((item) => item.course)
  }

  getCategories() {
    const courses = this.getActiveCourses()
    return [...new Set(courses.map((course) => course.categoria))]
  }
}

// Instancia global
const Data = new DataManager()
