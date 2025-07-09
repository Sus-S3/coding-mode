package com.sus.codecademy.model;

import java.util.Date;

/**
 * Modelo de datos para representar una inscripción de un usuario a un curso.
 * Relaciona usuarios con cursos y registra la fecha de inscripción.
 */
public class Inscripcion {
    private int idInscripcion;
    private int idUsuario;
    private int idCurso;
    private Date fechaInscripcion;
    private String estado;

    /**
     * Constructor por defecto.
     */
    public Inscripcion() {
    }

    /**
     * Constructor con todos los parámetros.
     * @param idInscripcion Identificador único de la inscripción
     * @param idUsuario ID del usuario inscrito
     * @param idCurso ID del curso al que se inscribe
     * @param fechaInscripcion Fecha de la inscripción
     * @param estado Estado de la inscripción
     */
    public Inscripcion(int idInscripcion, int idUsuario, int idCurso, Date fechaInscripcion, String estado) {
        this.idInscripcion = idInscripcion;
        this.idUsuario = idUsuario;
        this.idCurso = idCurso;
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

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "idInscripcion=" + idInscripcion +
                ", idUsuario=" + idUsuario +
                ", idCurso=" + idCurso +
                ", fechaInscripcion=" + fechaInscripcion +
                ", estado='" + estado + '\'' +
                '}';
    }
}
