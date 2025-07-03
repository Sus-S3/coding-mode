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
            <!-- Cursos Activos -->
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
                    <c:set var="activos" value="0" scope="page"/>
                    <c:forEach var="curso" items="${cursos}">
                        <c:if test="${fn:toLowerCase(fn:trim(curso.estado)) == 'activo'}">
                            <c:set var="activos" value="${activos + 1}" scope="page"/>
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
                                            <button type="button" class="btn btn-outline" onclick="openEditModal('${curso.idCurso}', '${curso.nombre}', '${curso.descripcion}', '${curso.categoria}', '${curso.estado}', '${curso.duracion}')">Editar</button>
                                            <button type="button" class="btn btn-destructive" onclick="openDeleteModal('${curso.idCurso}')">Eliminar</button>
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

    <!-- Modal Editar Curso -->
    <div id="editCourseModal" class="modal-overlay" style="display:none;">
        <div class="modal-content" style="max-width: 400px; margin: 0 auto; border-radius: 12px;">
            <div class="auth-card">
                <div class="auth-header">
                    <h2 class="auth-title" style="margin-bottom: 0.5rem;">Editar Curso</h2>
                    <p class="auth-description" style="margin-bottom: 1.5rem;">Modifica los datos del curso y guarda los cambios</p>
                </div>
                <form id="editCourseForm" method="post" action="editarCurso" class="auth-form">
                    <input type="hidden" name="idCurso" id="editIdCurso">
                    <div class="form-group">
                        <label for="editNombre" class="form-label">Nombre</label>
                        <input type="text" name="nombre" id="editNombre" class="form-input" required>
                    </div>
                    <div class="form-group">
                        <label for="editDescripcion" class="form-label">Descripción</label>
                        <textarea name="descripcion" id="editDescripcion" class="form-textarea" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="editCategoria" class="form-label">Categoría</label>
                        <input type="text" name="categoria" id="editCategoria" class="form-input" required>
                    </div>
                    <div class="form-group">
                        <label for="editEstado" class="form-label">Estado</label>
                        <select name="estado" id="editEstado" class="form-select" required>
                            <option value="activo">Activo</option>
                            <option value="inactivo">Inactivo</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editDuracion" class="form-label">Duración</label>
                        <input type="text" name="duracion" id="editDuracion" class="form-input" required>
                    </div>
                    <div class="modal-actions" style="display: flex; gap: 1rem; margin-top: 1.5rem;">
                        <button type="button" class="btn btn-ghost" onclick="closeEditModal()">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal Confirmar Eliminación -->
    <div id="deleteCourseModal" class="modal-overlay" style="display:none;">
        <div class="modal-content">
            <h2>Eliminar Curso</h2>
            <p>¿Estás seguro de que deseas eliminar este curso?</p>
            <form id="deleteCourseForm" method="post" action="eliminarCurso">
                <input type="hidden" name="idCurso" id="deleteIdCurso">
                <div class="modal-actions">
                    <button type="button" class="btn btn-ghost" onclick="closeDeleteModal()">Cancelar</button>
                    <button type="submit" class="btn btn-destructive">Eliminar</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Modal Nuevo Curso -->
    <div id="newCourseModal" class="modal-overlay" style="display:none;">
        <div class="modal-content" style="max-width: 400px; margin: 0 auto; border-radius: 12px;">
            <div class="auth-card">
                <div class="auth-header">
                    <h2 class="auth-title" style="margin-bottom: 0.5rem;">Nuevo Curso</h2>
                    <p class="auth-description" style="margin-bottom: 1.5rem;">Completa los datos para crear un nuevo curso</p>
                </div>
                <form id="newCourseForm" method="post" action="nuevoCurso" class="auth-form" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="newNombre" class="form-label">Nombre</label>
                        <input type="text" name="nombre" id="newNombre" class="form-input" required>
                    </div>
                    <div class="form-group">
                        <label for="newDescripcion" class="form-label">Descripción</label>
                        <textarea name="descripcion" id="newDescripcion" class="form-textarea" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="newCategoria" class="form-label">Categoría</label>
                        <input type="text" name="categoria" id="newCategoria" class="form-input" required>
                    </div>
                    <div class="form-group">
                        <label for="newEstado" class="form-label">Estado</label>
                        <select name="estado" id="newEstado" class="form-select" required>
                            <option value="activo">Activo</option>
                            <option value="inactivo">Inactivo</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="newDuracion" class="form-label">Duración</label>
                        <input type="text" name="duracion" id="newDuracion" class="form-input" required>
                    </div>
                    <div class="form-group">
                        <label for="newImagen" class="form-label">Imagen</label>
                        <input type="file" name="imagen" id="newImagen" class="form-input" accept="image/*" required>
                    </div>
                    <div class="modal-actions" style="display: flex; gap: 1rem; margin-top: 1.5rem;">
                        <button type="button" class="btn btn-ghost" onclick="closeNewCourseModal()">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Crear Curso</button>
                    </div>
                </form>
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
function openEditModal(id, nombre, descripcion, categoria, estado, duracion) {
    document.getElementById('editIdCurso').value = id;
    document.getElementById('editNombre').value = nombre;
    document.getElementById('editDescripcion').value = descripcion;
    document.getElementById('editCategoria').value = categoria;
    document.getElementById('editEstado').value = estado;
    document.getElementById('editDuracion').value = duracion;
    document.getElementById('editCourseModal').style.display = 'flex';
}
function closeEditModal() {
    document.getElementById('editCourseModal').style.display = 'none';
}

function openDeleteModal(id) {
    document.getElementById('deleteIdCurso').value = id;
    document.getElementById('deleteCourseModal').style.display = 'flex';
}
function closeDeleteModal() {
    document.getElementById('deleteCourseModal').style.display = 'none';
}

function openCourseModal() {
    document.getElementById('newCourseModal').style.display = 'flex';
}
function closeNewCourseModal() {
    document.getElementById('newCourseModal').style.display = 'none';
}

// Cierra el modal si se hace click fuera del contenido
window.onclick = function(event) {
    if (event.target.classList.contains('modal-overlay')) {
        event.target.style.display = 'none';
    }
}
</script>
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
