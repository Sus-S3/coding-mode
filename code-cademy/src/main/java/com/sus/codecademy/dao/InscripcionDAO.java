package com.sus.codecademy.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionBD {
    private Connection connection;

    public InscripcionBD(Connection connection) {
        this.connection = connection;
    }

    // Insertar una nueva inscripción
    public boolean insertarInscripcion(Inscripcion inscripcion) throws SQLException {
        String sql = "INSERT INTO inscripcion (id_usuario, id_curso, fecha_inscripcion) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, inscripcion.getIdUsuario());
            stmt.setInt(2, inscripcion.getIdCurso());
            stmt.setDate(3, new java.sql.Date(inscripcion.getFechaInscripcion().getTime()));
            return stmt.executeUpdate() > 0;
        }
    }

    // Obtener inscripción por ID
    public Inscripcion obtenerInscripcionPorId(int idInscripcion) throws SQLException {
        String sql = "SELECT * FROM inscripcion WHERE id_inscripcion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idInscripcion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Inscripcion(
                        rs.getInt("id_inscripcion"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_curso"),
                        rs.getDate("fecha_inscripcion")
                    );
                }
            }
        }
        return null;
    }

    // Obtener todas las inscripciones
    public List<Inscripcion> obtenerTodasLasInscripciones() throws SQLException {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                inscripciones.add(new Inscripcion(
                    rs.getInt("id_inscripcion"),
                    rs.getInt("id_usuario"),
                    rs.getInt("id_curso"),
                    rs.getDate("fecha_inscripcion")
                ));
            }
        }
        return inscripciones;
    }

    // Actualizar inscripción
    public boolean actualizarInscripcion(Inscripcion inscripcion) throws SQLException {
        String sql = "UPDATE inscripcion SET id_usuario=?, id_curso=?, fecha_inscripcion=? WHERE id_inscripcion=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, inscripcion.getIdUsuario());
            stmt.setInt(2, inscripcion.getIdCurso());
            stmt.setDate(3, new java.sql.Date(inscripcion.getFechaInscripcion().getTime()));
            stmt.setInt(4, inscripcion.getIdInscripcion());
            return stmt.executeUpdate() > 0;
        }
    }

    // Eliminar inscripción
    public boolean eliminarInscripcion(int idInscripcion) throws SQLException {
        String sql = "DELETE FROM inscripcion WHERE id_inscripcion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idInscripcion);
            return stmt.executeUpdate() > 0;
        }
    }
}
