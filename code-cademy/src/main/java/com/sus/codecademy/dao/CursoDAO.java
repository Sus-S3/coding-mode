package com.sus.codecademy.dao;

import com.sus.codecademy.model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar operaciones de cursos en la base de datos.
 * Proporciona métodos para consultar, crear, actualizar y eliminar cursos.
 */
public class CursoDAO {

    /**
     * Obtiene todos los cursos disponibles en el sistema.
     * @return Lista de todos los cursos
     */
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

    /**
     * Obtiene cursos filtrados por categoría.
     * @param categoria Categoría de cursos a buscar
     * @return Lista de cursos de la categoría especificada
     */
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

    /**
     * Obtiene todas las categorías disponibles de cursos.
     * @return Lista de categorías únicas
     */
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

    /**
     * Elimina un curso por su ID.
     * @param idCurso ID del curso a eliminar
     */
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

    /**
     * Obtiene un curso específico por su ID.
     * @param idCurso ID del curso a buscar
     * @return Objeto Curso si existe, null en caso contrario
     */
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

    /**
     * Actualiza los datos de un curso existente.
     * @param curso Objeto Curso con los datos actualizados
     */
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

    /**
     * Agrega un nuevo curso a la base de datos.
     * @param curso Objeto Curso con los datos del nuevo curso
     */
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
