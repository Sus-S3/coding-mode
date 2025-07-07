package com.sus.codecademy.dao;

import com.sus.codecademy.model.Inscripcion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO {
    
    // Método para registrar una nueva inscripción
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
    
    // Método para verificar si un usuario está inscrito en un curso
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
    
    // Método para obtener todas las inscripciones de un usuario
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
    
    // Método para obtener todas las inscripciones de un curso
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
    
    // Método para cancelar una inscripción (eliminar el registro)
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
    
    // Método para obtener una inscripción específica
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
    
    // Método para obtener todas las inscripciones (solo para admin)
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
    
    // Método para obtener los IDs de los cursos en los que está inscrito un usuario
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
}
