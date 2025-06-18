package com.sus.codecademy.model;

public class Curso {
    private int idCurso;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String estado;
    private String duracion;
    private byte[] imagen;

    // Constructor vacío
    public Curso() {
    }

    // Constructor con parámetros
    public Curso(int idCurso, String nombre, String descripcion, String categoria, String estado, String duracion, byte[] imagen) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estado = estado;
        this.duracion = duracion;
        this.imagen = imagen;
    }

    // Getters
    public int getIdCurso() {
        return idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEstado() {
        return estado;
    }

    public String getDuracion() {
        return duracion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    // Setters
    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    // toString
    @Override
    public String toString() {
        return "Curso{" +
                "idCurso=" + idCurso +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado='" + estado + '\'' +
                ", duracion='" + duracion + '\'' +
                ", imagen=" + (imagen != null ? "[bytes: " + imagen.length + "]" : "null") +
                '}';
    }
}
