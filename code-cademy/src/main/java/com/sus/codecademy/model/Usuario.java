package com.sus.codecademy.model;

/**
 * Modelo de datos para representar un usuario del sistema.
 * Contiene información personal y de autenticación del usuario.
 */
public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String pais;
    private String rol;
    private String password;

    /**
     * Constructor por defecto.
     */
    public Usuario() {
    }

    /**
     * Constructor con todos los parámetros.
     * @param idUsuario Identificador único del usuario
     * @param nombre Nombre completo del usuario
     * @param correo Correo electrónico del usuario
     * @param telefono Número de teléfono del usuario
     * @param pais País de residencia del usuario
     * @param rol Rol del usuario (admin, estudiante)
     * @param password Contraseña encriptada del usuario
     */
    public Usuario(int idUsuario, String nombre, String correo, String telefono, String pais, String rol, String password) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.pais = pais;
        this.rol = rol;
        this.password = password;
    }

    // Getters y setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPais() {
        return pais;
    }

    public String getRol() {
        return rol;
    }

    public String getPassword() {
        return password;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", pais='" + pais + '\'' +
                ", rol='" + rol + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
