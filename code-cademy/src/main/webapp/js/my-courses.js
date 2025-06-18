// My courses page specific functionality
document.addEventListener("DOMContentLoaded", () => {
  if (!checkAuthForProtectedPage()) return

  loadMyCourses()
})

function loadMyCourses() {
  const container = document.getElementById("myCoursesGrid")
  if (!container) return

  container.innerHTML = ""

  const userEnrollments = enrollments.filter((e) => e.id_usuario === currentUser.id_usuario)
  const userCourses = userEnrollments
    .map((enrollment) => {
      return courses.find((course) => course.id_curso === enrollment.id_curso)
    })
    .filter((course) => course) // Filter out undefined courses

  if (userCourses.length === 0) {
    container.innerHTML = `
      <div class="text-center" style="grid-column: 1 / -1;">
        <h3>No tienes cursos inscritos aún</h3>
        <p>¡Explora nuestros cursos y comienza tu aprendizaje!</p>
        <a href="courses.html" class="btn btn-primary">Ver Cursos</a>
      </div>
    `
    return
  }

  userCourses.forEach((course) => {
    const courseCard = createCourseCard(course, false)
    container.appendChild(courseCard)
  })
}
