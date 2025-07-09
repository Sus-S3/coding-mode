package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.UsuarioDAO;
import com.sus.codecademy.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet para manejar la autenticación de usuarios.
 * Procesa el formulario de login y establece la sesión del usuario.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
        // No crear admin automáticamente, ya existe en la BD
    }
    
    /**
     * Procesa el formulario de login enviado por POST.
     * Autentica al usuario y establece la sesión correspondiente.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Obtener parámetros del formulario
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            

            
            // Validar que ambos campos estén presentes
            if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"success\": false, \"message\": \"Email y contraseña son obligatorios\"}");
                return;
            }
            
            // Autenticar el usuario
            String emailClean = email.trim().toLowerCase();
            Usuario usuario = usuarioDAO.autenticarUsuario(emailClean, password);
            
            if (usuario != null) {
                // Guardar usuario en sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                
                // Determinar a dónde redirigir según el rol
                String redirectUrl;
                if ("Administrador".equals(usuario.getRol())) {
                    redirectUrl = "dashboard";
                } else {
                    redirectUrl = "index.jsp";
                }
                
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"success\": true, \"message\": \"Inicio de sesión exitoso\", \"redirect\": \"" + redirectUrl + "\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.println("{\"success\": false, \"message\": \"Credenciales incorrectas\"}");
            }
            
        } catch (Exception e) {
            System.err.println("Error en LoginServlet: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"success\": false, \"message\": \"Error interno del servidor\"}");
        }
    }
    
    /**
     * Redirige a la página de login si se accede por GET.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir a la página de login si alguien accede por GET
        response.sendRedirect("login.jsp");
    }
} 