package com.sus.codecademy.dao;

import com.sus.codecademy.model.Inscripcion;
import com.sus.codecademy.model.InscripcionCompleta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar operaciones de inscripciones en la base de datos.
 * Proporciona métodos para registrar, consultar y gestionar inscripciones de usuarios a cursos.
 */
public class InscripcionDAO {
    
    /**
     * Registra una nueva inscripción de usuario a curso.
     * @param inscripcion Objeto Inscripcion con los datos a registrar
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarInscripcion(Inscripcion inscripcion) {
        String sql = "INSERT INTO inscripcion (idUsuario, idCurso, fechaInscripcion) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, inscripcion.getIdUsuario());
            pstmt.setInt(2, inscripcion.getIdCurso());
            pstmt.setDate(3, new java.sql.Date(inscripcion.getFechaInscripcion().getTime()));
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar inscripción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Verifica si un usuario está inscrito en un curso específico.
     * @param idUsuario ID del usuario
     * @param idCurso ID del curso
     * @return true si está inscrito, false en caso contrario
     */
    public boolean estaInscrito(int idUsuario, int idCurso) {
        String sql = "SELECT COUNT(*) FROM inscripcion WHERE idUsuario = ? AND idCurso = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idCurso);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar inscripción: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Obtiene todas las inscripciones de un usuario específico.
     * @param idUsuario ID del usuario
     * @return Lista de inscripciones del usuario ordenadas por fecha descendente
     */
    public List<Inscripcion> obtenerInscripcionesPorUsuario(int idUsuario) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion WHERE idUsuario = ? ORDER BY fechaInscripcion DESC";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(rs.getInt("idInscripcion"));
                inscripcion.setIdUsuario(rs.getInt("idUsuario"));
                inscripcion.setIdCurso(rs.getInt("idCurso"));
                inscripcion.setFechaInscripcion(rs.getDate("fechaInscripcion"));
                inscripcion.setEstado("activa"); // Valor por defecto
                inscripciones.add(inscripcion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener inscripciones por usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return inscripciones;
    }
    
    /**
     * Obtiene todas las inscripciones de un curso específico.
     * @param idCurso ID del curso
     * @return Lista de inscripciones al curso ordenadas por fecha descendente
     */
    public List<Inscripcion> obtenerInscripcionesPorCurso(int idCurso) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion WHERE idCurso = ? ORDER BY fechaInscripcion DESC";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCurso);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(rs.getInt("idInscripcion"));
                inscripcion.setIdUsuario(rs.getInt("idUsuario"));
                inscripcion.setIdCurso(rs.getInt("idCurso"));
                inscripcion.setFechaInscripcion(rs.getDate("fechaInscripcion"));
                inscripcion.setEstado("activa"); // Valor por defecto
                inscripciones.add(inscripcion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener inscripciones por curso: " + e.getMessage());
            e.printStackTrace();
        }
        
        return inscripciones;
    }
    
    /**
     * Cancela una inscripción eliminándola de la base de datos.
     * @param idInscripcion ID de la inscripción a cancelar
     * @return true si se canceló exitosamente, false en caso contrario
     */
    public boolean cancelarInscripcion(int idInscripcion) {
        String sql = "DELETE FROM inscripcion WHERE idInscripcion = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idInscripcion);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al cancelar inscripción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene una inscripción específica por su ID.
     * @param idInscripcion ID de la inscripción
     * @return Objeto Inscripcion si existe, null en caso contrario
     */
    public Inscripcion obtenerInscripcion(int idInscripcion) {
        String sql = "SELECT * FROM inscripcion WHERE idInscripcion = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idInscripcion);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(rs.getInt("idInscripcion"));
                inscripcion.setIdUsuario(rs.getInt("idUsuario"));
                inscripcion.setIdCurso(rs.getInt("idCurso"));
                inscripcion.setFechaInscripcion(rs.getDate("fechaInscripcion"));
                inscripcion.setEstado("activa"); // Valor por defecto
                return inscripcion;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener inscripción: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Obtiene todas las inscripciones del sistema (solo para administradores).
     * @return Lista de todas las inscripciones ordenadas por fecha descendente
     */
    public List<Inscripcion> obtenerTodasLasInscripciones() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion ORDER BY fechaInscripcion DESC";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                inscripcion.setIdInscripcion(rs.getInt("idInscripcion"));
                inscripcion.setIdUsuario(rs.getInt("idUsuario"));
                inscripcion.setIdCurso(rs.getInt("idCurso"));
                inscripcion.setFechaInscripcion(rs.getDate("fechaInscripcion"));
                inscripcion.setEstado("activa"); // Valor por defecto
                inscripciones.add(inscripcion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las inscripciones: " + e.getMessage());
            e.printStackTrace();
        }
        
        return inscripciones;
    }
    
    /**
     * Obtiene los IDs de los cursos en los que está inscrito un usuario.
     * @param idUsuario ID del usuario
     * @return Lista de IDs de cursos inscritos
     */
    public List<Integer> obtenerCursosInscritos(int idUsuario) {
        List<Integer> cursosInscritos = new ArrayList<>();
        String sql = "SELECT idCurso FROM inscripcion WHERE idUsuario = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                cursosInscritos.add(rs.getInt("idCurso"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener cursos inscritos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return cursosInscritos;
    }
    
    /**
     * Obtiene todas las inscripciones con información completa (usuario y curso).
     * @return Lista de inscripciones completas para el dashboard de administración
     */
    public List<InscripcionCompleta> obtenerInscripcionesCompletas() {
        List<InscripcionCompleta> inscripcionesCompletas = new ArrayList<>();
        String sql = "SELECT i.idInscripcion, i.idUsuario, i.idCurso, " +
                    "u.nombre as nombreUsuario, u.correo as correoUsuario, " +
                    "c.nombre as nombreCurso, c.categoria as categoriaCurso, " +
                    "i.fechaInscripcion, 'activa' as estado " +
                    "FROM inscripcion i " +
                    "JOIN usuario u ON i.idUsuario = u.idUsuario " +
                    "JOIN curso c ON i.idCurso = c.idCurso " +
                    "ORDER BY i.fechaInscripcion ASC";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                InscripcionCompleta inscripcion = new InscripcionCompleta();
                inscripcion.setIdInscripcion(rs.getInt("idInscripcion"));
                inscripcion.setIdUsuario(rs.getInt("idUsuario"));
                inscripcion.setIdCurso(rs.getInt("idCurso"));
                inscripcion.setNombreUsuario(rs.getString("nombreUsuario"));
                inscripcion.setCorreoUsuario(rs.getString("correoUsuario"));
                inscripcion.setNombreCurso(rs.getString("nombreCurso"));
                inscripcion.setCategoriaCurso(rs.getString("categoriaCurso"));
                inscripcion.setFechaInscripcion(rs.getDate("fechaInscripcion"));
                inscripcion.setEstado(rs.getString("estado"));
                inscripcionesCompletas.add(inscripcion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener inscripciones completas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return inscripcionesCompletas;
    }
}
