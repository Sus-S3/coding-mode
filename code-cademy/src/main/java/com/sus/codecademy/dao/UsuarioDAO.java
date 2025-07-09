package com.sus.codecademy.dao;

import com.sus.codecademy.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para gestionar operaciones de usuarios en la base de datos.
 * Proporciona métodos para registro, autenticación, consulta y gestión de usuarios.
 */
public class UsuarioDAO {
    
    /**
     * Registra un nuevo usuario en la base de datos.
     * @param usuario Objeto Usuario con los datos a registrar
     * @return true si el registro fue exitoso, false en caso contrario
     */
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
    
    /**
     * Autentica un usuario verificando correo y contraseña.
     * @param correo Correo electrónico del usuario
     * @param password Contraseña del usuario
     * @return Objeto Usuario si la autenticación es exitosa, null en caso contrario
     */
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
    
    /**
     * Obtiene un usuario por su correo electrónico.
     * @param correo Correo electrónico del usuario
     * @return Objeto Usuario si existe, null en caso contrario
     */
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
    
    /**
     * Obtiene un usuario por su ID.
     * @param idUsuario ID del usuario
     * @return Objeto Usuario si existe, null en caso contrario
     */
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
    
    /**
     * Verifica si existe un usuario con el correo especificado.
     * @param correo Correo electrónico a verificar
     * @return true si existe el usuario, false en caso contrario
     */
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
    
    /**
     * Crea el usuario administrador por defecto si no existe.
     * @return true si se creó exitosamente o ya existía, false en caso contrario
     */
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
    
    /**
     * Obtiene todos los usuarios registrados en el sistema.
     * @return Lista de todos los usuarios ordenados por nombre
     */
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
