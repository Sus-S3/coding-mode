package com.sus.codecademy;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Servlet de ejemplo para pruebas básicas.
 * Muestra un mensaje "Hello World!" en HTML.
 */
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    /**
     * Inicializa el servlet con un mensaje de prueba.
     */
    public void init() {
        message = "Hello World!";
    }

    /**
     * Maneja las solicitudes GET mostrando una página HTML simple.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    /**
     * Método de limpieza al destruir el servlet.
     */
    public void destroy() {
    }
}