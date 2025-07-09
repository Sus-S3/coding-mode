package com.sus.codecademy.model;

import java.util.Date;

/**
 * Modelo de datos extendido para representar una inscripción con información completa.
 * Combina datos de inscripción, usuario y curso en una sola entidad para consultas JOIN.
 */
public class InscripcionCompleta {
    private int idInscripcion;
    private int idUsuario;
    private int idCurso;
    private String nombreUsuario;
    private String correoUsuario;
    private String nombreCurso;
    private String categoriaCurso;
    private Date fechaInscripcion;
    private String estado;

    /**
     * Constructor por defecto.
     */
    public InscripcionCompleta() {
    }

    /**
     * Constructor con todos los parámetros.
     * @param idInscripcion Identificador único de la inscripción
     * @param idUsuario ID del usuario inscrito
     * @param idCurso ID del curso al que se inscribe
     * @param nombreUsuario Nombre completo del usuario
     * @param correoUsuario Correo electrónico del usuario
     * @param nombreCurso Nombre del curso
     * @param categoriaCurso Categoría del curso
     * @param fechaInscripcion Fecha de la inscripción
     * @param estado Estado de la inscripción
     */
    public InscripcionCompleta(int idInscripcion, int idUsuario, int idCurso, 
                              String nombreUsuario, String correoUsuario, 
                              String nombreCurso, String categoriaCurso, 
                              Date fechaInscripcion, String estado) {
        this.idInscripcion = idInscripcion;
        this.idUsuario = idUsuario;
        this.idCurso = idCurso;
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.nombreCurso = nombreCurso;
        this.categoriaCurso = categoriaCurso;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }

    // Getters
    public int getIdInscripcion() {
        return idInscripcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public String getCategoriaCurso() {
        return categoriaCurso;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setCategoriaCurso(String categoriaCurso) {
        this.categoriaCurso = categoriaCurso;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "InscripcionCompleta{" +
                "idInscripcion=" + idInscripcion +
                ", idUsuario=" + idUsuario +
                ", idCurso=" + idCurso +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", correoUsuario='" + correoUsuario + '\'' +
                ", nombreCurso='" + nombreCurso + '\'' +
                ", categoriaCurso='" + categoriaCurso + '\'' +
                ", fechaInscripcion=" + fechaInscripcion +
                ", estado='" + estado + '\'' +
                '}';
    }
} 