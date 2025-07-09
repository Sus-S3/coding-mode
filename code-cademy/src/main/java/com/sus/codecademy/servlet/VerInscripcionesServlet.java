package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.InscripcionDAO;
import com.sus.codecademy.model.InscripcionCompleta;
import com.sus.codecademy.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/verInscripciones")
public class VerInscripcionesServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Verificar si el usuario está autenticado y es administrador
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (!"Administrador".equals(usuario.getRol())) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        try {
            InscripcionDAO inscripcionDAO = new InscripcionDAO();
            List<InscripcionCompleta> inscripciones = inscripcionDAO.obtenerInscripcionesCompletas();
            
            // Guardar las inscripciones en el request para mostrarlas en el JSP
            request.setAttribute("inscripciones", inscripciones);
            
            // Redirigir al dashboard con las inscripciones
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error al obtener inscripciones: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("dashboard.jsp?error=Error al cargar inscripciones");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 