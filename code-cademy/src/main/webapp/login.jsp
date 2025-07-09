<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - CodeAcademy</title>
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

    <div class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <h1 class="auth-title">Iniciar Sesión</h1>
                <p class="auth-description">Ingresa tus credenciales para acceder a tu cuenta</p>
            </div>

            <form id="loginForm" class="auth-form">
                <div class="form-group">
                    <label for="email" class="form-label">Correo Electrónico</label>
                    <input type="email" id="email" name="email" class="form-input" required placeholder="tu@email.com">
                </div>

                <div class="form-group">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" id="password" name="password" class="form-input" required placeholder="••••••••">
                </div>

                <button type="submit" class="btn btn-primary btn-full" id="loginBtn">
                    Iniciar Sesión
                </button>
            </form>

            <div class="auth-footer">
                <p class="auth-link-text">
                    ¿No tienes una cuenta? 
                    <a href="register.jsp" class="auth-link">Regístrate aquí</a>
                </p>
                <!-- 
                <div class="admin-credentials">
                    <p><strong>Credenciales de Admin:</strong></p>
                    <p>Email: admin@codeacademy.com</p>
                    <p>Contraseña: admin123</p>
                </div>
                -->
            </div>
        </div>
    </div>

    <!-- Toast Container -->
    <div id="toastContainer" class="toast-container"></div>

    <script src="js/auth.js"></script>
    <script src="js/utils.js"></script>
    <script src="js/main.js"></script>
    <script>
        document.getElementById('loginForm').addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const loginBtn = document.getElementById('loginBtn');
            
            loginBtn.textContent = 'Iniciando sesión...';
            loginBtn.disabled = true;
            
            try {
                const formData = new URLSearchParams();
                formData.append('email', email);
                formData.append('password', password);
                
                const response = await fetch('login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: formData.toString()
                });
                
                const data = await response.json();
                
                if (data.success) {
                    showToast('¡Bienvenido!', data.message, 'success');
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
                loginBtn.textContent = 'Iniciar Sesión';
                loginBtn.disabled = false;
            }
        });
    </script>
</body>
</html>
