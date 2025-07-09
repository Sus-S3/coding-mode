<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mi Perfil - CodeAcademy</title>
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

            <div class="nav-auth" id="navAuth">
                <!-- Se carga dinámicamente -->
            </div>
        </div>
    </nav>

    <div class="container profile-container">
        <div class="page-header">
            <h1 class="page-title">Mi Perfil</h1>
            <p class="page-description">Gestiona tu información personal y configuración de cuenta</p>
        </div>

        <div class="profile-content">
            <!-- Información Personal -->
            <div class="card">
                <div class="card-header">
                    <h2 class="card-title">Información Personal</h2>
                    <p class="card-description">Actualiza tus datos personales</p>
                </div>
                <div class="card-content">
                    <form id="profileForm" class="profile-form">
                        <!-- Mostrar rol como información no editable -->
                        <div class="form-group">
                            <label class="form-label">Rol de Usuario</label>
                            <div id="roleDisplay" class="role-display">
                                <!-- Se carga dinámicamente -->
                            </div>
                        </div>

                        <div class="separator"></div>

                        <div class="form-group">
                            <label for="nombre" class="form-label">Nombre Completo</label>
                            <input type="text" id="nombre" name="nombre" class="form-input" required>
                        </div>

                        <div class="form-group">
                            <label for="correo" class="form-label">Correo Electrónico</label>
                            <input type="email" id="correo" name="correo" class="form-input" required>
                        </div>

                        <div class="form-group">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="tel" id="telefono" name="telefono" class="form-input" required>
                        </div>

                        <div class="form-group">
                            <label for="pais" class="form-label">País</label>
                            <select id="pais" name="pais" class="form-select" required>
                                <option value="">Selecciona tu país</option>
                                <option value="Argentina">Argentina</option>
                                <option value="Bolivia">Bolivia</option>
                                <option value="Chile">Chile</option>
                                <option value="Colombia">Colombia</option>
                                <option value="Costa Rica">Costa Rica</option>
                                <option value="Cuba">Cuba</option>
                                <option value="Ecuador">Ecuador</option>
                                <option value="El Salvador">El Salvador</option>
                                <option value="España">España</option>
                                <option value="Guatemala">Guatemala</option>
                                <option value="Honduras">Honduras</option>
                                <option value="México">México</option>
                                <option value="Nicaragua">Nicaragua</option>
                                <option value="Panamá">Panamá</option>
                                <option value="Paraguay">Paraguay</option>
                                <option value="Perú">Perú</option>
                                <option value="República Dominicana">República Dominicana</option>
                                <option value="Uruguay">Uruguay</option>
                                <option value="Venezuela">Venezuela</option>
                            </select>
                        </div>

                        <button type="submit" class="btn btn-primary">
                            Guardar Cambios
                        </button>
                    </form>
                </div>
            </div>

            <!-- Cambiar Contraseña -->
            <div class="card">
                <div class="card-header">
                    <h2 class="card-title">Cambiar Contraseña</h2>
                    <p class="card-description">Actualiza tu contraseña para mantener tu cuenta segura</p>
                </div>
                <div class="card-content">
                    <form id="passwordForm" class="password-form">
                        <div class="form-group">
                            <label for="currentPassword" class="form-label">Contraseña Actual</label>
                            <input type="password" id="currentPassword" name="currentPassword" class="form-input" required placeholder="••••••••">
                        </div>

                        <div class="form-group">
                            <label for="newPassword" class="form-label">Nueva Contraseña</label>
                            <input type="password" id="newPassword" name="newPassword" class="form-input" required placeholder="••••••••" minlength="6">
                            <p class="form-help">La contraseña debe tener al menos 6 caracteres</p>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword" class="form-label">Confirmar Nueva Contraseña</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" required placeholder="••••••••">
                        </div>

                        <button type="submit" class="btn btn-primary">
                            Cambiar Contraseña
                        </button>
                    </form>
                </div>
            </div>

            <!-- Zona Peligrosa -->
            <div class="card danger-zone">
                <div class="card-header">
                    <h2 class="card-title danger-title">Zona Peligrosa</h2>
                    <p class="card-description">Acciones irreversibles en tu cuenta</p>
                </div>
                <div class="card-content">
                    <button type="button" class="btn btn-destructive" onclick="handleDeleteProfile()">
                        Eliminar Cuenta Permanentemente
                    </button>
                    <p class="danger-warning">
                        Esta acción no se puede deshacer. Se eliminarán todos tus datos y inscripciones.
                    </p>
                </div>
            </div>
        </div>
    </div>

    <!-- Toast Container -->
    <div id="toastContainer" class="toast-container"></div>

    <script src="js/auth.js"></script>
    <script src="js/utils.js"></script>
    <script src="js/main.js"></script>
    <script>
        // Verificación adicional para debug
        document.addEventListener('DOMContentLoaded', () => {
            console.log('Profile page loaded');
            setTimeout(() => {
                console.log('Current user after timeout:', Auth.getCurrentUser());
            }, 1000);
        });
    </script>
</body>
</html>
