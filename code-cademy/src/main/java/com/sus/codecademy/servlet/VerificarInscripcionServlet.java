package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.InscripcionDAO;
import com.sus.codecademy.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/verificar-inscripciones")
public class VerificarInscripcionServlet extends HttpServlet {
    
    private InscripcionDAO inscripcionDAO;
    
    @Override
    public void init() throws ServletException {
        inscripcionDAO = new InscripcionDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Verificar si el usuario está autenticado
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                out.println("{\"authenticated\": false}");
                return;
            }
            
            // Obtener el usuario de la sesión
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            
            // Obtener las inscripciones del usuario
            List<Integer> cursosInscritos = inscripcionDAO.obtenerCursosInscritos(usuario.getIdUsuario());
            
            // Construir respuesta JSON
            StringBuilder jsonResponse = new StringBuilder();
            jsonResponse.append("{\"authenticated\": true, \"cursosInscritos\": [");
            
            for (int i = 0; i < cursosInscritos.size(); i++) {
                if (i > 0) jsonResponse.append(",");
                jsonResponse.append(cursosInscritos.get(i));
            }
            
            jsonResponse.append("]}");
            out.println(jsonResponse.toString());
            
        } catch (Exception e) {
            System.err.println("Error en VerificarInscripcionServlet: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Error interno del servidor\"}");
        }
    }
} 