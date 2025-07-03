package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.CursoDAO;
import com.sus.codecademy.model.Curso;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/editarCurso")
public class EditarCursoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idCursoStr = req.getParameter("id");
        if (idCursoStr != null) {
            int idCurso = Integer.parseInt(idCursoStr);
            CursoDAO dao = new CursoDAO();
            Curso curso = dao.obtenerPorId(idCurso);
            req.setAttribute("curso", curso);
            req.getRequestDispatcher("editarCurso.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idCurso = Integer.parseInt(req.getParameter("idCurso"));
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        String categoria = req.getParameter("categoria");
        String estado = req.getParameter("estado");
        String duracion = req.getParameter("duracion");

        Curso curso = new Curso();
        curso.setIdCurso(idCurso);
        curso.setNombre(nombre);
        curso.setDescripcion(descripcion);
        curso.setCategoria(categoria);
        curso.setEstado(estado);
        curso.setDuracion(duracion);

        CursoDAO dao = new CursoDAO();
        dao.actualizar(curso);

        resp.sendRedirect("dashboard");
    }
}
