package com.sus.codecademy.dao;

import com.sus.codecademy.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Crear usuario
    public boolean crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, correo, telefono, pais, rol, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getPais());
            stmt.setString(5, usuario.getRol());
            stmt.setString(6, usuario.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    // Leer usuario por ID
    public Usuario obtenerUsuarioPorId(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuario: " + e.getMessage());
        }
        return null;
    }

    // Leer usuario por correo
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuario por correo: " + e.getMessage());
        }
        return null;
    }

    // Leer todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre=?, correo=?, telefono=?, pais=?, rol=?, password=? WHERE idUsuario=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getTelefono());
            stmt.setString(4, usuario.getPais());
            stmt.setString(5, usuario.getRol());
            stmt.setString(6, usuario.getPassword());
            stmt.setInt(7, usuario.getIdUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // Eliminar usuario
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet a Usuario
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("idUsuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setTelefono(rs.getString("telefono"));
        usuario.setPais(rs.getString("pais"));
        usuario.setRol(rs.getString("rol"));
        usuario.setPassword(rs.getString("password"));
        return usuario;
    }
}
