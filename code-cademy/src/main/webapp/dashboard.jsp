<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - CodeAcademy</title>
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
                <a href="cursos" class="nav-link">Cursos</a>
                <a href="about.jsp" class="nav-link">Sobre Nosotros</a>
                <a href="contact.jsp" class="nav-link">Contacto</a>
            </div>

            <div class="nav-auth" id="navAuth">
                <!-- Se carga dinámicamente -->
            </div>
        </div>
    </nav>

    <div class="container">
        <!-- Panel de Administración -->
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
                <div class="stat-value">${fn:length(cursos)}</div>
            </div>
            <div class="stat-card">
                <div class="stat-header">
                    <span class="stat-title">Total Inscripciones</span>
                    <svg class="stat-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <circle cx="12" cy="12" r="10"/>
                        <polyline points="12,6 12,12 16,14"/>
                    </svg>
                </div>
                <div class="stat-value">0</div> <!-- Puedes reemplazar por el valor real si lo tienes -->
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
                <div class="stat-value">
                    <c:set var="activos" value="0" />
                    <c:forEach var="curso" items="${cursos}">
                        <c:if test="${curso.estado == 'activo'}">
                            <c:set var="activos" value="${activos + 1}" />
                        </c:if>
                    </c:forEach>
                    ${activos}
                </div>
            </div>
        </div>

        <!-- Tabs -->
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

                <div class="courses-grid">
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
                                        <div class="course-actions">
                                            <a href="editarCurso?id=${curso.idCurso}" class="btn btn-outline">Editar</a>
                                            <form action="eliminarCurso" method="post" style="display:inline;">
                                                <input type="hidden" name="idCurso" value="${curso.idCurso}" />
                                                <button type="submit" class="btn btn-destructive" onclick="return confirm('¿Seguro que deseas eliminar este curso?')">Eliminar</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="no-content">
                                <h3>No hay cursos disponibles</h3>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="tabs-content" data-content="enrollments" style="display:none;">
                <!-- Contenido para Ver Inscripciones -->
                <h2>Inscripciones</h2>
                <p>Aquí se mostrarán las inscripciones de los estudiantes.</p>
            </div>
        </div>
    </div>

    <!-- Toast Container -->
    <div id="toastContainer" class="toast-container"></div>

    <script src="js/auth.js"></script>
    <script src="js/data.js"></script>
    <script src="js/utils.js"></script>
    <script src="js/main.js"></script>
    <script>
document.querySelectorAll('.tabs-trigger').forEach(btn => {
    btn.addEventListener('click', function() {
        document.querySelectorAll('.tabs-trigger').forEach(b => b.classList.remove('active'));
        document.querySelectorAll('.tabs-content').forEach(tc => tc.classList.remove('active'));
        this.classList.add('active');
        document.querySelector('.tabs-content[data-content="' + this.dataset.target + '"]').classList.add('active');
    });
});
</script>
</body>
</html>
