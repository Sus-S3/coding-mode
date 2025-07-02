<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contacto - CodeAcademy</title>
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
                <a href="contact.jsp" class="nav-link active">Contacto</a>
            </div>

            <div class="nav-auth" id="navAuth">
                <a href="login.jsp" class="btn btn-ghost">Iniciar Sesión</a>
                <a href="register.jsp" class="btn btn-primary">Registrarse</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <!-- Hero Section -->
        <div class="contact-hero">
            <h1 class="contact-title">Contáctanos</h1>
            <p class="contact-description">
                ¿Tienes preguntas sobre nuestros cursos? ¿Necesitas ayuda técnica? Estamos aquí para ayudarte.
            </p>
        </div>

        <div class="contact-content">
            <!-- Contact Form -->
            <div class="card contact-form-card">
                <div class="card-header">
                    <h2 class="card-title">Envíanos un Mensaje</h2>
                    <p class="card-description">
                        Completa el formulario y nos pondremos en contacto contigo lo antes posible.
                    </p>
                </div>
                <div class="card-content">
                    <form id="contactForm" class="contact-form">
                        <div class="form-row">
                            <div class="form-group">
                                <label for="nombre" class="form-label">Nombre Completo</label>
                                <input type="text" id="nombre" name="nombre" class="form-input" required placeholder="Tu nombre">
                            </div>
                            <div class="form-group">
                                <label for="correo" class="form-label">Correo Electrónico</label>
                                <input type="email" id="correo" name="correo" class="form-input" required placeholder="tu@email.com">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="asunto" class="form-label">Asunto</label>
                            <input type="text" id="asunto" name="asunto" class="form-input" required placeholder="¿En qué podemos ayudarte?">
                        </div>

                        <div class="form-group">
                            <label for="mensaje" class="form-label">Mensaje</label>
                            <textarea id="mensaje" name="mensaje" class="form-textarea" required placeholder="Describe tu consulta o comentario..." rows="6"></textarea>
                        </div>

                        <button type="submit" class="btn btn-primary btn-full">
                            Enviar Mensaje
                        </button>
                    </form>
                </div>
            </div>

            <!-- Contact Information -->
            <div class="contact-info">
                <div class="card">
                    <div class="card-header">
                        <h2 class="card-title">Información de Contacto</h2>
                        <p class="card-description">Otras formas de ponerte en contacto con nosotros</p>
                    </div>
                    <div class="card-content">
                        <div class="contact-item">
                            <div class="contact-icon blue">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                    <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
                                    <polyline points="22,6 12,13 2,6"/>
                                </svg>
                            </div>
                            <div class="contact-details">
                                <h3 class="contact-title">Email</h3>
                                <p class="contact-text">info@codeacademy.com</p>
                                <p class="contact-text">soporte@codeacademy.com</p>
                            </div>
                        </div>

                        <div class="contact-item">
                            <div class="contact-icon green">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                    <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
                                </svg>
                            </div>
                            <div class="contact-details">
                                <h3 class="contact-title">Teléfono</h3>
                                <p class="contact-text">+1 (555) 123-4567</p>
                                <p class="contact-text">+1 (555) 987-6543</p>
                            </div>
                        </div>

                        <div class="contact-item">
                            <div class="contact-icon red">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                    <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"/>
                                    <circle cx="12" cy="10" r="3"/>
                                </svg>
                            </div>
                            <div class="contact-details">
                                <h3 class="contact-title">Dirección</h3>
                                <p class="contact-text">
                                    123 Tech Street<br>
                                    Silicon Valley, CA 94000<br>
                                    Estados Unidos
                                </p>
                            </div>
                        </div>

                        <div class="contact-item">
                            <div class="contact-icon purple">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                                    <circle cx="12" cy="12" r="10"/>
                                    <polyline points="12,6 12,12 16,14"/>
                                </svg>
                            </div>
                            <div class="contact-details">
                                <h3 class="contact-title">Horario de Atención</h3>
                                <p class="contact-text">
                                    Lunes - Viernes: 9:00 AM - 6:00 PM<br>
                                    Sábados: 10:00 AM - 4:00 PM<br>
                                    Domingos: Cerrado
                                </p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h2 class="card-title">Preguntas Frecuentes</h2>
                    </div>
                    <div class="card-content">
                        <div class="faq-item">
                            <h4 class="faq-question">¿Cómo puedo inscribirme a un curso?</h4>
                            <p class="faq-answer">
                                Simplemente crea una cuenta, navega por nuestros cursos y haz clic en "Inscribirse".
                            </p>
                        </div>

                        <div class="faq-item">
                            <h4 class="faq-question">¿Ofrecen certificados?</h4>
                            <p class="faq-answer">
                                Sí, al completar exitosamente un curso recibirás un certificado digital.
                            </p>
                        </div>

                        <div class="faq-item">
                            <h4 class="faq-question">¿Hay soporte técnico disponible?</h4>
                            <p class="faq-answer">
                                Por supuesto, nuestro equipo de soporte está disponible para ayudarte.
                            </p>
                        </div>
                    </div>
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
