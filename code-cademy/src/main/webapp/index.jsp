<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CodeAcademy - Aprende Programación</title>
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
                <a href="index.jsp" class="nav-link active">Inicio</a>
                <a href="cursos" class="nav-link">Cursos</a>
                <a href="about.jsp" class="nav-link">Sobre Nosotros</a>
                <a href="contact.jsp" class="nav-link">Contacto</a>
            </div>

            <div class="nav-auth" id="navAuth">
                <a href="login.jsp" class="btn btn-ghost">Iniciar Sesión</a>
                <a href="register.jsp" class="btn btn-primary">Registrarse</a>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <section class="hero">
        <div class="hero-container">
            <div class="hero-content">
                <h1 class="hero-title">
                    Aprende Programación
                    <br>
                    <span class="hero-highlight">desde Cero</span>
                </h1>
                <p class="hero-description">
                    Únete a miles de estudiantes que han transformado sus carreras con nuestros cursos de programación
                </p>
                <div class="hero-buttons">
                    <a href="courses.jsp" class="btn btn-yellow btn-lg">Ver Cursos</a>
                    <a href="register.jsp" class="btn btn-outline btn-lg">Registrarse Gratis</a>
                </div>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section class="features">
        <div class="container">
            <div class="features-header">
                <h2 class="section-title">¿Por qué elegir CodeAcademy?</h2>
                <p class="section-description">
                    Ofrecemos la mejor experiencia de aprendizaje con instructores expertos y contenido actualizado
                </p>
            </div>

            <div class="features-grid">
                <div class="feature-card">
                    <div class="feature-icon blue">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/>
                            <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/>
                        </svg>
                    </div>
                    <h3 class="feature-title">Cursos Actualizados</h3>
                    <p class="feature-description">Contenido siempre actualizado con las últimas tecnologías del mercado</p>
                </div>

                <div class="feature-card">
                    <div class="feature-icon green">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                            <circle cx="9" cy="7" r="4"/>
                            <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                            <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                        </svg>
                    </div>
                    <h3 class="feature-title">Comunidad Activa</h3>
                    <p class="feature-description">Únete a una comunidad de más de 10,000 desarrolladores activos</p>
                </div>

                <div class="feature-card">
                    <div class="feature-icon purple">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <circle cx="12" cy="8" r="7"/>
                            <polyline points="8.21,13.89 7,23 12,20 17,23 15.79,13.88"/>
                        </svg>
                    </div>
                    <h3 class="feature-title">Certificaciones</h3>
                    <p class="feature-description">Obtén certificados reconocidos por la industria al completar los cursos</p>
                </div>

                <div class="feature-card">
                    <div class="feature-icon orange">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <circle cx="12" cy="12" r="10"/>
                            <polyline points="12,6 12,12 16,14"/>
                        </svg>
                    </div>
                    <h3 class="feature-title">A tu Ritmo</h3>
                    <p class="feature-description">Aprende cuando quieras, donde quieras, a tu propio ritmo</p>
                </div>
            </div>
        </div>
    </section>

    <!-- CTA Section -->
    <section class="cta">
        <div class="container">
            <div class="cta-content">
                <h2 class="cta-title">¿Listo para comenzar tu carrera en programación?</h2>
                <p class="cta-description">
                    Únete a CodeAcademy hoy y comienza tu transformación profesional
                </p>
                <a href="register.jsp" class="btn btn-primary btn-lg">Comenzar Ahora</a>
            </div>
        </div>
    </section>

    <!-- Toast Container -->
    <div id="toastContainer" class="toast-container"></div>

    <script src="js/auth.js"></script>
    <script src="js/data.js"></script>
    <script src="js/utils.js"></script>
    <script src="js/main.js"></script>
</body>
</html>
