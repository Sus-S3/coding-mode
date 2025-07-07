<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                <a href="cursos" class="nav-link active">Cursos</a>
                <a href="about.jsp" class="nav-link">Sobre Nosotros</a>
                <a href="contact.jsp" class="nav-link">Contacto</a>
            </div>

            <div class="nav-auth" id="navAuth">
                <!-- Se carga dinámicamente -->
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="page-header">
            <h1 class="page-title">Cursos Disponibles</h1>
            <p class="page-description">Explora nuestra amplia selección de cursos de programación</p>
            

            
            <form method="get" action="cursos" class="filter-section">
                <label for="categoryFilter" class="filter-label">Filtrar por categoría:</label>
                <select id="categoryFilter" name="categoria" class="form-select" onchange="this.form.submit()">
                    <option value="todas" ${categoriaSeleccionada == 'todas' ? 'selected' : ''}>Todas las categorías</option>
                    <c:forEach var="cat" items="${categorias}">
                        <option value="${cat}" ${cat == categoriaSeleccionada ? 'selected' : ''}>${cat}</option>
                    </c:forEach>
                </select>
            </form>
        </div>

        <div id="coursesGrid" class="courses-grid">
            <c:choose>
                <c:when test="${not empty cursos}">
                    <c:forEach var="curso" items="${cursos}">
                        <div class="course-card">
                            <div class="course-image">
                                <img src="cursos/imagen?id=${curso.idCurso}" alt="${curso.nombre}" />
                            </div>
                            <div class="course-content">
                                <div class="course-meta">
                                    <span class="course-category">${curso.categoria}</span>
                                    <span class="course-duration">${curso.duracion}</span>
                                </div>
                                <h3 class="course-title">${curso.nombre}</h3>
                                <p class="course-description">${curso.descripcion}</p>
                                <button onclick="inscribirseEnCurso(${curso.idCurso})" class="btn btn-primary enroll-btn" data-curso-id="${curso.idCurso}">Inscribirme</button>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div id="noCourses" class="no-content">
                        <svg class="no-content-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/>
                            <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
                        </svg>
                        <h3>No hay cursos disponibles</h3>
                        <p>No se encontraron cursos para la categoría seleccionada.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Toast Container -->
    <div id="toastContainer" class="toast-container"></div>

    <script src="js/auth.js"></script>
    <script src="js/data.js"></script>
    <script src="js/utils.js"></script>
    <script src="js/main.js"></script>
    
    <script>
        async function inscribirseEnCurso(cursoId) {
            // Verificar si el usuario está autenticado
            if (!Auth.isAuthenticated()) {
                showToast('Autenticación requerida', 'Debes iniciar sesión para inscribirte en cursos', 'warning');
                setTimeout(() => {
                    window.location.href = 'login.jsp';
                }, 2000);
                return;
            }

            try {
                const formData = new URLSearchParams();
                formData.append('cursoId', cursoId);
                console.log('Enviando inscripción:', formData.toString());
                console.log('cursoId:', cursoId);
                
                const response = await fetch('inscribir-curso', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: formData.toString()
                });
                
                const data = await response.json();
                
                if (data.success) {
                    showToast('¡Éxito!', data.message, 'success');
                    // Cambiar el botón a "Inscrito" y deshabilitarlo
                    const button = document.querySelector(`button[data-curso-id="${cursoId}"]`);
                    if (button) {
                        button.textContent = 'Inscrito';
                        button.disabled = true;
                        button.classList.remove('btn-primary');
                        button.classList.add('btn-secondary');
                        button.onclick = null; // Remover el onclick
                    }
                } else {
                    showToast('Error', data.message, 'error');
                }
            } catch (error) {
                console.error('Error:', error);
                showToast('Error', 'Ocurrió un error inesperado', 'error');
            }
        }

        // Verificar estado de inscripción al cargar la página
        document.addEventListener('DOMContentLoaded', async () => {
            if (Auth.isAuthenticated()) {
                try {
                    const response = await fetch('verificar-inscripciones');
                    const data = await response.json();
                    
                    if (data.authenticated && data.cursosInscritos) {
                        // Actualizar botones de cursos ya inscritos
                        data.cursosInscritos.forEach(cursoId => {
                            const button = document.querySelector(`button[data-curso-id="${cursoId}"]`);
                            if (button) {
                                button.textContent = 'Inscrito';
                                button.disabled = true;
                                button.classList.remove('btn-primary');
                                button.classList.add('btn-secondary');
                                button.onclick = null; // Remover el onclick
                            }
                        });
                    }
                } catch (error) {
                    console.error('Error verificando inscripciones:', error);
                }
            }
        });

    </script>
</body>
</html>
