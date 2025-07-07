package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.InscripcionDAO;
import com.sus.codecademy.model.Inscripcion;
import com.sus.codecademy.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/inscribir-curso")
public class InscribirCursoServlet extends HttpServlet {
    
    private InscripcionDAO inscripcionDAO;
    
    @Override
    public void init() throws ServletException {
        inscripcionDAO = new InscripcionDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            System.out.println("=== INICIO INSCRIPCIÓN ===");
            // Verificar si el usuario está autenticado
            HttpSession session = request.getSession(false);
            System.out.println("Session: " + (session != null ? "EXISTS" : "NULL"));
            
            if (session == null || session.getAttribute("usuario") == null) {
                System.out.println("Usuario no autenticado");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.println("{\"success\": false, \"message\": \"Debes iniciar sesión para inscribirte a un curso\", \"redirect\": \"login.jsp\"}");
                return;
            }
            
            // Obtener el usuario de la sesión
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            System.out.println("Usuario autenticado: " + usuario.getNombre());
            
            // Obtener el ID del curso
            String cursoIdStr = request.getParameter("cursoId");
            System.out.println("cursoId recibido: '" + cursoIdStr + "'");
            
            if (cursoIdStr == null || cursoIdStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"success\": false, \"message\": \"ID del curso es obligatorio\"}");
                return;
            }
            
            int cursoId;
            try {
                cursoId = Integer.parseInt(cursoIdStr);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"success\": false, \"message\": \"ID del curso inválido\"}");
                return;
            }
            
            // Verificar si el usuario ya está inscrito en este curso
            if (inscripcionDAO.estaInscrito(usuario.getIdUsuario(), cursoId)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.println("{\"success\": false, \"message\": \"Ya estás inscrito en este curso\"}");
                return;
            }
            
            // Crear la inscripción
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setIdUsuario(usuario.getIdUsuario());
            inscripcion.setIdCurso(cursoId);
            inscripcion.setFechaInscripcion(new Date());
            
            // Guardar la inscripción en la base de datos
            boolean inscrito = inscripcionDAO.registrarInscripcion(inscripcion);
            
            if (inscrito) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"success\": true, \"message\": \"Te has inscrito exitosamente al curso\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("{\"success\": false, \"message\": \"Error al inscribirte al curso\"}");
            }
            
        } catch (Exception e) {
            System.err.println("Error en InscribirCursoServlet: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"success\": false, \"message\": \"Error interno del servidor\"}");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir a la página de cursos si alguien accede por GET
        response.sendRedirect("cursos");
    }
} 