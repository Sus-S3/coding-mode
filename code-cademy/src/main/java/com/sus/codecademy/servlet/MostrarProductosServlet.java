package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.CursoDAO;
import com.sus.codecademy.model.Curso;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet(urlPatterns = {"/cursos", "/cursos/imagen"})
public class MostrarProductosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();

        // Imagen
        if ("/cursos/imagen".equals(servletPath + (pathInfo != null ? pathInfo : ""))) {
            String idStr = req.getParameter("id");
            if (idStr == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int idCurso = Integer.parseInt(idStr);
            CursoDAO dao = new CursoDAO();
            List<Curso> cursos = dao.obtenerTodos();
            Curso curso = cursos.stream().filter(c -> c.getIdCurso() == idCurso).findFirst().orElse(null);

            if (curso != null && curso.getImagen() != null) {
                resp.setContentType("image/jpeg");
                OutputStream out = resp.getOutputStream();
                out.write(curso.getImagen());
                out.close();
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            return;
        }

        // Filtrado por categoría
        String categoria = req.getParameter("categoria");
        CursoDAO dao = new CursoDAO();
        List<Curso> cursos;
        if (categoria != null && !categoria.equals("todas")) {
            cursos = dao.obtenerPorCategoria(categoria);
        } else {
            cursos = dao.obtenerTodos();
        }
        List<String> categorias = dao.obtenerCategorias();
        req.setAttribute("cursos", cursos);
        req.setAttribute("categorias", categorias);
        req.setAttribute("categoriaSeleccionada", categoria != null ? categoria : "todas");
        req.getRequestDispatcher("courses.jsp").forward(req, resp);
    }
}
