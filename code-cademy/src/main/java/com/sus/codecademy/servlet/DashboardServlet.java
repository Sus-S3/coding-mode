package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.CursoDAO;
import com.sus.codecademy.model.Curso;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CursoDAO dao = new CursoDAO();
        List<Curso> cursos = dao.obtenerTodos();
        req.setAttribute("cursos", cursos);
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}
