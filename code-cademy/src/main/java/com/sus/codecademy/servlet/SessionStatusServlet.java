package com.sus.codecademy.servlet;

import com.sus.codecademy.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/session-status")
public class SessionStatusServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            
            String jsonResponse = String.format(
                "{\"authenticated\": true, \"id\": %d, \"nombre\": \"%s\", \"email\": \"%s\", \"rol\": \"%s\", \"isAdmin\": %s}",
                usuario.getIdUsuario(),
                usuario.getNombre().replace("\"", "\\\""),
                usuario.getCorreo().replace("\"", "\\\""),
                usuario.getRol().replace("\"", "\\\""),
                "Administrador".equals(usuario.getRol())
            );
            out.println(jsonResponse);
        } else {
            out.println("{\"authenticated\": false}");
        }
    }
} 