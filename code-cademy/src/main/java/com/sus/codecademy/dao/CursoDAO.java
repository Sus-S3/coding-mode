package com.sus.codecademy.dao;

import com.sus.codecademy.model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public List<Curso> obtenerTodos() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM curso";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getInt("idCurso"));
                curso.setNombre(rs.getString("nombre"));
                curso.setDescripcion(rs.getString("descripcion"));
                curso.setCategoria(rs.getString("categoria"));
                curso.setEstado(rs.getString("estado"));
                curso.setDuracion(rs.getString("duracion"));
                curso.setImagen(rs.getBytes("imagen"));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    public List<Curso> obtenerPorCategoria(String categoria) {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM curso WHERE categoria = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setIdCurso(rs.getInt("idCurso"));
                    curso.setNombre(rs.getString("nombre"));
                    curso.setDescripcion(rs.getString("descripcion"));
                    curso.setCategoria(rs.getString("categoria"));
                    curso.setEstado(rs.getString("estado"));
                    curso.setDuracion(rs.getString("duracion"));
                    curso.setImagen(rs.getBytes("imagen"));
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        String sql = "SELECT DISTINCT categoria FROM curso";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categorias.add(rs.getString("categoria"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public void eliminar(int idCurso) {
        String sql = "DELETE FROM curso WHERE idCurso = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Curso obtenerPorId(int idCurso) {
        String sql = "SELECT * FROM curso WHERE idCurso = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Curso curso = new Curso();
                    curso.setIdCurso(rs.getInt("idCurso"));
                    curso.setNombre(rs.getString("nombre"));
                    curso.setDescripcion(rs.getString("descripcion"));
                    curso.setCategoria(rs.getString("categoria"));
                    curso.setEstado(rs.getString("estado"));
                    curso.setDuracion(rs.getString("duracion"));
                    curso.setImagen(rs.getBytes("imagen"));
                    return curso;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizar(Curso curso) {
        String sql = "UPDATE curso SET nombre=?, descripcion=?, categoria=?, estado=?, duracion=? WHERE idCurso=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNombre());
            stmt.setString(2, curso.getDescripcion());
            stmt.setString(3, curso.getCategoria());
            stmt.setString(4, curso.getEstado());
            stmt.setString(5, curso.getDuracion());
            stmt.setInt(6, curso.getIdCurso());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregar(Curso curso) {
        String sql = "INSERT INTO curso (nombre, descripcion, categoria, estado, duracion, imagen) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNombre());
            stmt.setString(2, curso.getDescripcion());
            stmt.setString(3, curso.getCategoria());
            stmt.setString(4, curso.getEstado());
            stmt.setString(5, curso.getDuracion());
            stmt.setBytes(6, curso.getImagen());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
