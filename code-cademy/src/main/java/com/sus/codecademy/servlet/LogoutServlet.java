package com.sus.codecademy.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet para manejar el cierre de sesión de usuarios.
 * Invalida la sesión actual y limpia los datos de autenticación.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    /**
     * Procesa la solicitud de cierre de sesión.
     * Invalida la sesión actual y remueve todos los atributos.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        PrintWriter out = response.getWriter();
        
        try {
            // Invalidar la sesión
            HttpSession session = request.getSession(false);
            if (session != null) {
                // Remover todos los atributos de la sesión
                session.removeAttribute("usuario");
                // Invalidar la sesión completamente
                session.invalidate();
                System.out.println("Sesión invalidada exitosamente");
            }
            
            response.setStatus(HttpServletResponse.SC_OK);
            out.println("{\"success\": true, \"message\": \"Sesión cerrada exitosamente\"}");
            
        } catch (Exception e) {
            System.err.println("Error en LogoutServlet: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"success\": false, \"message\": \"Error al cerrar sesión\"}");
        }
    }
    
    /**
     * Redirige las solicitudes GET a POST para procesar el logout.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir a POST
        doPost(request, response);
    }
} 