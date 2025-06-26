<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cursos - CodeAcademy</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/components.css">
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar">
        <div class="nav-container">
            <div class="nav-brand">
                <a href="index.jsp" class="brand-link">
                    <svg class="brand-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/>
                        <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
                    </svg>
                    <span class="brand-text">CodeAcademy</span>
                </a>
            </div>

            <div class="nav-links">
                <a href="index.jsp" class="nav-link">Inicio</a>
                <a href="courses.jsp" class="nav-link active">Cursos</a>
                <a href="about.jsp" class="nav-link">Sobre Nosotros</a>
                <a href="contact.jsp" class="nav-link">Contacto</a>
            </div>

            <div class="nav-auth" id="navAuth">
                <a href="login.jsp" class="btn btn-ghost">Iniciar Sesión</a>
                <a href="register.jsp" class="btn btn-primary">Registrarse</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="page-header">
            <h1 class="page-title">Cursos Disponibles</h1>
            <p class="page-description">Explora nuestra amplia selección de cursos de programación</p>
            
            <div class="filter-section">
                <label for="categoryFilter" class="filter-label">Filtrar por categoría:</label>
                <select id="categoryFilter" class="form-select">
                    <option value="todas">Todas las categorías</option>
                </select>
            </div>
        </div>

        <div id="coursesGrid" class="courses-grid">
            <!-- Los cursos se cargarán dinámicamente -->
        </div>

        <div id="noCourses" class="no-content" style="display: none;">
            <svg class="no-content-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/>
                <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
            </svg>
            <h3>No hay cursos disponibles</h3>
            <p>No se encontraron cursos para la categoría seleccionada.</p>
        </div>
    </div>

    <!-- Toast Container -->
    <div id="toastContainer" class="toast-container"></div>

    <script src="js/auth.js"></script>
    <script src="js/data.js"></script>
    <script src="js/utils.js"></script>
    <script src="js/main.js"></script>
</body>
</html>
