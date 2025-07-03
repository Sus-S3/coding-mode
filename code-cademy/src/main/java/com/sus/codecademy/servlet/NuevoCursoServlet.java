package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.CursoDAO;
import com.sus.codecademy.model.Curso;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/nuevoCurso")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB
public class NuevoCursoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        String categoria = req.getParameter("categoria");
        String estado = req.getParameter("estado");
        String duracion = req.getParameter("duracion");

        Part imagenPart = req.getPart("imagen");
        byte[] imagenBytes = null;
        if (imagenPart != null && imagenPart.getSize() > 0) {
            try (InputStream is = imagenPart.getInputStream()) {
                imagenBytes = is.readAllBytes();
            }
        }

        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.setDescripcion(descripcion);
        curso.setCategoria(categoria);
        curso.setEstado(estado);
        curso.setDuracion(duracion);
        curso.setImagen(imagenBytes);

        CursoDAO dao = new CursoDAO();
        dao.agregar(curso);

        resp.sendRedirect("dashboard");
    }
}
