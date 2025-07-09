package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.UsuarioDAO;
import com.sus.codecademy.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/test-auth")
public class TestAuthServlet extends HttpServlet {
    
    private UsuarioDAO usuarioDAO;
    
    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Test Auth</title></head><body>");
        out.println("<h1>Test de Autenticación</h1>");
        
        try {
            // Verificar si existe el usuario admin
            out.println("<h2>Verificando usuario administrador...</h2>");
            boolean existeAdmin = usuarioDAO.existeUsuario("admin@codeacademy.com");
            out.println("<p>¿Existe admin@codeacademy.com? " + existeAdmin + "</p>");
            
            if (existeAdmin) {
                // Intentar autenticar
                out.println("<h2>Probando autenticación...</h2>");
                Usuario usuario = usuarioDAO.autenticarUsuario("admin@codeacademy.com", "admin123");
                
                if (usuario != null) {
                    out.println("<p style='color: green;'>✓ Autenticación exitosa!</p>");
                    out.println("<p>ID: " + usuario.getIdUsuario() + "</p>");
                    out.println("<p>Nombre: " + usuario.getNombre() + "</p>");
                    out.println("<p>Email: " + usuario.getCorreo() + "</p>");
                    out.println("<p>Rol: " + usuario.getRol() + "</p>");
                } else {
                    out.println("<p style='color: red;'>✗ Autenticación fallida</p>");
                }
            } else {
                out.println("<h2>Creando usuario administrador...</h2>");
                boolean creado = usuarioDAO.crearAdminPorDefecto();
                out.println("<p>¿Se creó el admin? " + creado + "</p>");
                
                if (creado) {
                    // Intentar autenticar después de crear
                    Usuario usuario = usuarioDAO.autenticarUsuario("admin@codeacademy.com", "admin123");
                    if (usuario != null) {
                        out.println("<p style='color: green;'>✓ Admin creado y autenticación exitosa!</p>");
                    }
                }
            }
            
            // Mostrar todos los usuarios
            out.println("<h2>Usuarios en la base de datos:</h2>");
            List<Usuario> usuarios = usuarioDAO.obtenerTodosLosUsuarios();
            if (usuarios.isEmpty()) {
                out.println("<p>No hay usuarios en la base de datos</p>");
            } else {
                out.println("<ul>");
                for (Usuario u : usuarios) {
                    out.println("<li>" + u.getNombre() + " (" + u.getCorreo() + ") - " + u.getRol() + "</li>");
                }
                out.println("</ul>");
            }
            
        } catch (Exception e) {
            out.println("<p style='color: red;'>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
        
        out.println("</body></html>");
    }
} 