// Contact page specific functionality
document.addEventListener("DOMContentLoaded", () => {
  setupContactEventListeners()
})

function setupContactEventListeners() {
  const contactForm = document.getElementById("contactForm")
  if (contactForm) {
    contactForm.addEventListener("submit", handleContactSubmit)
  }
}

function handleContactSubmit(e) {
  e.preventDefault()

  const name = document.getElementById("contactName").value
  const email = document.getElementById("contactEmail").value
  const phone = document.getElementById("contactPhone").value
  const subject = document.getElementById("contactSubject").value
  const message = document.getElementById("contactMessage").value

  // Simulate sending message (in a real app, this would send to a server)
  console.log("Contact form submitted:", {
    name,
    email,
    phone,
    subject,
    message,
    timestamp: new Date().toISOString(),
  })

  // Assuming showAlert is defined elsewhere or imported
  // For example: import { showAlert } from './utils'; or defined in a global scope
  function showAlert(message, type) {
    // Basic implementation for demonstration purposes
    alert(message)
  }

  showAlert("¡Mensaje enviado exitosamente! Te contactaremos pronto.", "success")

  // Clear form
  document.getElementById("contactForm").reset()
}
