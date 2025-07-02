<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sobre Nosotros - CodeAcademy</title>
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
                <a href="about.jsp" class="nav-link active">Sobre Nosotros</a>
                <a href="contact.jsp" class="nav-link">Contacto</a>
            </div>

            <div class="nav-auth" id="navAuth">
                <a href="login.jsp" class="btn btn-ghost">Iniciar Sesión</a>
                <a href="register.jsp" class="btn btn-primary">Registrarse</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <!-- Hero Section -->
        <div class="about-hero">
            <h1 class="about-title">Sobre CodeAcademy</h1>
            <p class="about-description">
                Somos una plataforma educativa dedicada a formar a los próximos desarrolladores de software con cursos prácticos y actualizados.
            </p>
        </div>

        <!-- Mission & Vision -->
        <div class="mission-vision">
            <div class="card">
                <div class="card-content">
                    <div class="mission-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <circle cx="12" cy="12" r="10"/>
                            <path d="M12 6v6l4 2"/>
                        </svg>
                    </div>
                    <h2 class="mission-title">Nuestra Misión</h2>
                    <p class="mission-text">
                        Democratizar el acceso a la educación en programación, proporcionando cursos de alta calidad que preparen a nuestros estudiantes para los desafíos del mundo tecnológico actual.
                    </p>
                </div>
            </div>

            <div class="card">
                <div class="card-content">
                    <div class="vision-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                            <circle cx="12" cy="12" r="3"/>
                        </svg>
                    </div>
                    <h2 class="vision-title">Nuestra Visión</h2>
                    <p class="vision-text">
                        Ser la plataforma líder en educación tecnológica en América Latina, formando desarrolladores competentes que impulsen la innovación y el crecimiento digital en la región.
                    </p>
                </div>
            </div>
        </div>

        <!-- Values -->
        <div class="values-section">
            <h2 class="section-title">Nuestros Valores</h2>
            <div class="values-grid">
                <div class="value-card">
                    <div class="value-icon green">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/>
                            <circle cx="9" cy="7" r="4"/>
                            <path d="M23 21v-2a4 4 0 0 0-3-3.87"/>
                            <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                        </svg>
                    </div>
                    <h3 class="value-title">Comunidad</h3>
                    <p class="value-description">
                        Fomentamos un ambiente colaborativo donde todos pueden aprender y crecer juntos.
                    </p>
                </div>

                <div class="value-card">
                    <div class="value-icon purple">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <circle cx="12" cy="8" r="7"/>
                            <polyline points="8.21,13.89 7,23 12,20 17,23 15.79,13.88"/>
                        </svg>
                    </div>
                    <h3 class="value-title">Excelencia</h3>
                    <p class="value-description">
                        Nos comprometemos a ofrecer contenido de la más alta calidad y experiencias de aprendizaje excepcionales.
                    </p>
                </div>

                <div class="value-card">
                    <div class="value-icon orange">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                            <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>
                        </svg>
                    </div>
                    <h3 class="value-title">Innovación</h3>
                    <p class="value-description">
                        Constantemente actualizamos nuestros métodos y contenidos para mantenernos a la vanguardia tecnológica.
                    </p>
                </div>
            </div>
        </div>

        <!-- Stats -->
        <div class="stats-section">
            <h2 class="stats-title">CodeAcademy en Números</h2>
            <div class="stats-grid">
                <div class="stat-item">
                    <div class="stat-number">10,000+</div>
                    <div class="stat-label">Estudiantes Activos</div>
                </div>
                <div class="stat-item">
                    <div class="stat-number">50+</div>
                    <div class="stat-label">Cursos Disponibles</div>
                </div>
                <div class="stat-item">
                    <div class="stat-number">95%</div>
                    <div class="stat-label">Tasa de Satisfacción</div>
                </div>
                <div class="stat-item">
                    <div class="stat-number">15</div>
                    <div class="stat-label">Países</div>
                </div>
            </div>
        </div>

        <!-- Team -->
        <div class="team-section">
            <h2 class="section-title">Nuestro Equipo</h2>
            <p class="team-description">
                Contamos con un equipo de instructores expertos y profesionales de la industria comprometidos con tu éxito educativo.
            </p>

            <div class="team-grid">
                <div class="team-card">
                    <div class="team-avatar"></div>
                    <h3 class="team-name">María González</h3>
                    <p class="team-role">Directora Académica</p>
                    <div class="team-skills">
                        <span class="skill-tag">JavaScript</span>
                        <span class="skill-tag">React</span>
                    </div>
                    <p class="team-bio">
                        10+ años de experiencia en desarrollo web y educación tecnológica.
                    </p>
                </div>

                <div class="team-card">
                    <div class="team-avatar"></div>
                    <h3 class="team-name">Carlos Rodríguez</h3>
                    <p class="team-role">Instructor Senior</p>
                    <div class="team-skills">
                        <span class="skill-tag">Python</span>
                        <span class="skill-tag">Django</span>
                    </div>
                    <p class="team-bio">
                        Especialista en backend y arquitectura de software con 8 años de experiencia.
                    </p>
                </div>

                <div class="team-card">
                    <div class="team-avatar"></div>
                    <h3 class="team-name">Ana Martínez</h3>
                    <p class="team-role">Instructora de Frontend</p>
                    <div class="team-skills">
                        <span class="skill-tag">Vue.js</span>
                        <span class="skill-tag">CSS</span>
                    </div>
                    <p class="team-bio">
                        Experta en diseño UI/UX y desarrollo frontend moderno.
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
</body>
</html>
