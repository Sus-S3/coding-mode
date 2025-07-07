package com.sus.codecademy.dao;

import com.sus.codecademy.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    // Método para registrar un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, correo, telefono, pais, rol, password) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getTelefono());
            pstmt.setString(4, usuario.getPais());
            pstmt.setString(5, usuario.getRol());
            pstmt.setString(6, usuario.getPassword());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Método para autenticar un usuario
    public Usuario autenticarUsuario(String correo, String password) {
        String sql = "SELECT * FROM usuario WHERE correo = ? AND password = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, correo);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
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
            
        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Método para obtener un usuario por correo
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, correo);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
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
            
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por correo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Método para obtener un usuario por ID
    public Usuario obtenerUsuarioPorId(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
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
            
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Método para verificar si existe un usuario con el correo
    public boolean existeUsuario(String correo) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE correo = ?";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, correo);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Método para crear el usuario administrador por defecto (ya no se usa automáticamente)
    public boolean crearAdminPorDefecto() {
        // Verificar si ya existe el admin
        if (existeUsuario("admin@codeacademy.com")) {
            return true; // Ya existe
        }
        
        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setCorreo("admin@codeacademy.com");
        admin.setTelefono("+1234567890");
        admin.setPais("México");
        admin.setRol("Administrador");
        admin.setPassword("admin123");
        
        return registrarUsuario(admin);
    }
    
    // Método para obtener todos los usuarios (solo para admin)
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY nombre";
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setPais(rs.getString("pais"));
                usuario.setRol(rs.getString("rol"));
                usuario.setPassword(rs.getString("password"));
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        
        return usuarios;
    }
}
