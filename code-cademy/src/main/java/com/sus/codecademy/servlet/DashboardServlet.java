package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.CursoDAO;
import com.sus.codecademy.dao.InscripcionDAO;
import com.sus.codecademy.model.Curso;
import com.sus.codecademy.model.InscripcionCompleta;
import com.sus.codecademy.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Servlet para el panel de administración.
 * Carga datos de cursos e inscripciones para el dashboard del administrador.
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    
    /**
     * Carga el dashboard de administración con datos de cursos e inscripciones.
     * Verifica autenticación y permisos de administrador.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Verificar si el usuario está autenticado
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            // Usuario no autenticado, redirigir al login
            resp.sendRedirect("login.jsp");
            return;
        }
        
        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        // Verificar si el usuario es administrador
        if (!"Administrador".equals(usuario.getRol())) {
            // Usuario no es administrador, redirigir al index
            resp.sendRedirect("index.jsp");
            return;
        }
        
        // Usuario autenticado y es administrador, mostrar dashboard
        try {
            CursoDAO cursoDAO = new CursoDAO();
            InscripcionDAO inscripcionDAO = new InscripcionDAO();
            
            List<Curso> cursos = cursoDAO.obtenerTodos();
            List<InscripcionCompleta> inscripciones = inscripcionDAO.obtenerInscripcionesCompletas();
            
            req.setAttribute("cursos", cursos);
            req.setAttribute("inscripciones", inscripciones);
            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            System.err.println("Error al cargar dashboard: " + e.getMessage());
            e.printStackTrace();
            // Si hay error, intentar cargar solo los cursos
            try {
                CursoDAO cursoDAO = new CursoDAO();
                List<Curso> cursos = cursoDAO.obtenerTodos();
                req.setAttribute("cursos", cursos);
                req.setAttribute("inscripciones", new ArrayList<>());
                req.setAttribute("error", "Error al cargar inscripciones: " + e.getMessage());
                req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
            } catch (Exception ex) {
                System.err.println("Error crítico al cargar dashboard: " + ex.getMessage());
                ex.printStackTrace();
                resp.sendRedirect("index.jsp?error=Error al cargar dashboard");
            }
        }
    }
}
