// Utilidades generales

function showToast(title, message, type = "success") {
  const toastContainer = document.getElementById("toastContainer")
  if (!toastContainer) return

  const toast = document.createElement("div")
  toast.className = `toast ${type}`

  toast.innerHTML = `
        <div class="toast-header">
            <span class="toast-title">${title}</span>
            <button class="toast-close" onclick="this.parentElement.parentElement.remove()">×</button>
        </div>
        <div class="toast-message">${message}</div>
    `

  toastContainer.appendChild(toast)

  // Auto remove after 5 seconds
  setTimeout(() => {
    if (toast.parentElement) {
      toast.remove()
    }
  }, 5000)
}

function formatDate(dateString) {
  const date = new Date(dateString)
  return date.toLocaleDateString("es-ES", {
    year: "numeric",
    month: "long",
    day: "numeric",
  })
}

function formatDateTime(dateString) {
  const date = new Date(dateString)
  return date.toLocaleString("es-ES", {
    year: "numeric",
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  })
}

function debounce(func, wait) {
  let timeout
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout)
      func(...args)
    }
    clearTimeout(timeout)
    timeout = setTimeout(later, wait)
  }
}

function validateEmail(email) {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return re.test(email)
}

function validatePassword(password) {
  return password.length >= 6
}

function sanitizeHtml(str) {
  const temp = document.createElement("div")
  temp.textContent = str
  return temp.innerHTML
}

function generateId() {
  return Date.now().toString() + Math.random().toString(36).substr(2, 9)
}

// Tabs functionality
function initTabs(tabsContainer) {
  const triggers = tabsContainer.querySelectorAll(".tabs-trigger")
  const contents = tabsContainer.querySelectorAll(".tabs-content")

  triggers.forEach((trigger) => {
    trigger.addEventListener("click", () => {
      const target = trigger.dataset.target

      // Remove active class from all triggers and contents
      triggers.forEach((t) => t.classList.remove("active"))
      contents.forEach((c) => c.classList.remove("active"))

      // Add active class to clicked trigger and corresponding content
      trigger.classList.add("active")
      const targetContent = tabsContainer.querySelector(`[data-content="${target}"]`)
      if (targetContent) {
        targetContent.classList.add("active")
      }
    })
  })
}

// Form validation
function validateForm(form) {
  const inputs = form.querySelectorAll("input[required], select[required], textarea[required]")
  let isValid = true

  inputs.forEach((input) => {
    if (!input.value.trim()) {
      isValid = false
      input.classList.add("error")
    } else {
      input.classList.remove("error")
    }

    // Email validation
    if (input.type === "email" && input.value && !validateEmail(input.value)) {
      isValid = false
      input.classList.add("error")
    }

    // Password validation
    if (input.type === "password" && input.value && !validatePassword(input.value)) {
      isValid = false
      input.classList.add("error")
    }
  })

  return isValid
}

// Loading state management
function setLoading(element, isLoading, originalText = "") {
  if (isLoading) {
    element.dataset.originalText = element.textContent
    element.textContent = "Cargando..."
    element.disabled = true
  } else {
    element.textContent = element.dataset.originalText || originalText
    element.disabled = false
  }
}

// Local storage helpers
function getFromStorage(key, defaultValue = null) {
  try {
    const item = localStorage.getItem(key)
    return item ? JSON.parse(item) : defaultValue
  } catch (error) {
    console.error("Error reading from localStorage:", error)
    return defaultValue
  }
}

function setToStorage(key, value) {
  try {
    localStorage.setItem(key, JSON.stringify(value))
    return true
  } catch (error) {
    console.error("Error writing to localStorage:", error)
    return false
  }
}
