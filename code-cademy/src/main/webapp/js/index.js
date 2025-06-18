// Import necessary modules (assuming these are defined elsewhere)
import { courses } from "./data.js" // Assuming courses data is in data.js
import { createCourseCard } from "./components.js" // Assuming createCourseCard is in components.js

// Index page specific functionality
document.addEventListener("DOMContentLoaded", () => {
  loadFeaturedCourses()
  setupHeroAnimations()
})

function loadFeaturedCourses() {
  const featuredCourses = courses.filter((course) => course.estado === "Activo").slice(0, 6)
  const container = document.getElementById("featuredCourses")

  if (!container) return

  container.innerHTML = ""

  if (featuredCourses.length === 0) {
    container.innerHTML = '<p class="text-center">No hay cursos disponibles en este momento.</p>'
    return
  }

  featuredCourses.forEach((course) => {
    const courseCard = createCourseCard(course, true)
    container.appendChild(courseCard)
  })
}

function setupHeroAnimations() {
  // Add some animation to hero elements
  const heroTitle = document.querySelector(".hero h2")
  const heroText = document.querySelector(".hero p")
  const heroButton = document.querySelector(".hero .btn")

  if (heroTitle) {
    heroTitle.style.opacity = "0"
    heroTitle.style.transform = "translateY(30px)"

    setTimeout(() => {
      heroTitle.style.transition = "all 0.8s ease"
      heroTitle.style.opacity = "1"
      heroTitle.style.transform = "translateY(0)"
    }, 200)
  }

  if (heroText) {
    heroText.style.opacity = "0"
    heroText.style.transform = "translateY(30px)"

    setTimeout(() => {
      heroText.style.transition = "all 0.8s ease"
      heroText.style.opacity = "1"
      heroText.style.transform = "translateY(0)"
    }, 400)
  }

  if (heroButton) {
    heroButton.style.opacity = "0"
    heroButton.style.transform = "translateY(30px)"

    setTimeout(() => {
      heroButton.style.transition = "all 0.8s ease"
      heroButton.style.opacity = "1"
      heroButton.style.transform = "translateY(0)"
    }, 600)
  }
}
