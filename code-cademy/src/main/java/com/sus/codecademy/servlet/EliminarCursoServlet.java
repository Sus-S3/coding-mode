package com.sus.codecademy.servlet;

import com.sus.codecademy.dao.CursoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/eliminarCurso")
public class EliminarCursoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idCursoStr = req.getParameter("idCurso");
        if (idCursoStr != null) {
            int idCurso = Integer.parseInt(idCursoStr);
            CursoDAO dao = new CursoDAO();
            dao.eliminar(idCurso);
        }
        resp.sendRedirect("dashboard");
    }
}
