<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrarse - CodeAcademy</title>
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
        </div>
    </nav>

    <div class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <h1 class="auth-title">Crear Cuenta</h1>
                <p class="auth-description">Completa el formulario para registrarte en CodeAcademy</p>
            </div>

            <form id="registerForm" class="auth-form">
                <div class="form-group">
                    <label for="nombre" class="form-label">Nombre Completo</label>
                    <input type="text" id="nombre" name="nombre" class="form-input" required placeholder="Tu nombre completo">
                </div>

                <div class="form-group">
                    <label for="email" class="form-label">Correo Electrónico</label>
                    <input type="email" id="email" name="email" class="form-input" required placeholder="tu@email.com">
                </div>

                <div class="form-group">
                    <label for="telefono" class="form-label">Teléfono</label>
                    <input type="tel" id="telefono" name="telefono" class="form-input" required placeholder="+1234567890">
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

                <div class="form-group">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" id="password" name="password" class="form-input" required placeholder="••••••••">
                </div>

                <div class="form-group">
                    <label for="confirmPassword" class="form-label">Confirmar Contraseña</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" required placeholder="••••••••">
                </div>

                <button type="submit" class="btn btn-primary btn-full" id="registerBtn">
                    Crear Cuenta
                </button>
            </form>

            <div class="auth-footer">
                <p class="auth-link-text">
                    ¿Ya tienes una cuenta? 
                    <a href="login.jsp" class="auth-link">Inicia sesión aquí</a>
                </p>
            </div>
        </div>
    </div>

    <!-- Toast Container -->
    <div id="toastContainer" class="toast-container"></div>

    <script src="js/auth.js"></script>
    <script src="js/utils.js"></script>
    <script>
        document.getElementById('registerForm').addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const formData = new FormData(e.target);
            const userData = Object.fromEntries(formData);
            const registerBtn = document.getElementById('registerBtn');
            
            if (userData.password !== userData.confirmPassword) {
                showToast('Error', 'Las contraseñas no coinciden.', 'error');
                return;
            }
            
            registerBtn.textContent = 'Creando cuenta...';
            registerBtn.disabled = true;
            
            try {
                const formDataEncoded = new URLSearchParams(formData);
                const response = await fetch('register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: formDataEncoded.toString()
                });
                
                const data = await response.json();
                
                if (data.success) {
                    showToast('¡Registro exitoso!', data.message, 'success');
                    setTimeout(() => {
                        window.location.href = data.redirect;
                    }, 1000);
                } else {
                    showToast('Error', data.message, 'error');
                }
            } catch (error) {
                console.error('Error:', error);
                showToast('Error', 'Ocurrió un error inesperado.', 'error');
            } finally {
                registerBtn.textContent = 'Crear Cuenta';
                registerBtn.disabled = false;
            }
        });
    </script>
</body>
</html>
