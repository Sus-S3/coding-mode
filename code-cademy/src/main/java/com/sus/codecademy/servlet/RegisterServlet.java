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
 * Servlet para manejar el registro de nuevos usuarios.
 * Procesa el formulario de registro y crea cuentas de estudiantes.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
        // No crear admin automáticamente, ya existe en la BD
    }
    
    /**
     * Procesa el formulario de registro enviado por POST.
     * Valida datos, verifica duplicados y crea la cuenta de usuario.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Obtener parámetros del formulario
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String telefono = request.getParameter("telefono");
            String pais = request.getParameter("pais");
            String password = request.getParameter("password");
            
            // Validar que todos los campos estén presentes
            if (nombre == null || nombre.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                pais == null || pais.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"success\": false, \"message\": \"Todos los campos son obligatorios\"}");
                return;
            }
            
            // Verificar si el usuario ya existe
            if (usuarioDAO.existeUsuario(email)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.println("{\"success\": false, \"message\": \"El correo ya está registrado\"}");
                return;
            }
            
            // Crear el objeto usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre.trim());
            nuevoUsuario.setCorreo(email.trim().toLowerCase());
            nuevoUsuario.setTelefono(telefono.trim());
            nuevoUsuario.setPais(pais.trim());
            nuevoUsuario.setRol("estudiante"); // Por defecto todos son estudiantes
            nuevoUsuario.setPassword(password);
            
            // Registrar el usuario en la base de datos
            boolean registrado = usuarioDAO.registrarUsuario(nuevoUsuario);
            
            if (registrado) {
                // Obtener el usuario recién registrado para guardarlo en sesión
                Usuario usuarioRegistrado = usuarioDAO.obtenerUsuarioPorCorreo(email);
                
                if (usuarioRegistrado != null) {
                    // Guardar usuario en sesión
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuarioRegistrado);
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println("{\"success\": true, \"message\": \"Usuario registrado exitosamente\", \"redirect\": \"index.jsp\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.println("{\"success\": false, \"message\": \"Error al obtener datos del usuario registrado\"}");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("{\"success\": false, \"message\": \"Error al registrar el usuario\"}");
            }
            
        } catch (Exception e) {
            System.err.println("Error en RegisterServlet: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"success\": false, \"message\": \"Error interno del servidor\"}");
        }
    }
    
    /**
     * Redirige a la página de registro si se accede por GET.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir a la página de registro si alguien accede por GET
        response.sendRedirect("register.jsp");
    }
} 