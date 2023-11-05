package io.github.k3ssdev.teamtrekandroid.database;

import java.util.Date;

public class Avisos {

    private String idAviso;
    private String titulo;
    private String descripcion;
    private String fechaPublicacion;
    private String fechaExpiracion;
    private String autorId;
    private String activo;

    public Avisos(String idAviso, String titulo, String descripcion, String fechaPublicacion, String fechaExpiracion, String autorId, String activo) {
        this.idAviso = idAviso;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaExpiracion = fechaExpiracion;
        this.autorId = autorId;
        this.activo = activo;
    }

    public String getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(String idAviso) {
        this.idAviso = idAviso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getAutorId() {
        return autorId;
    }

    public void setAutorId(String autorId) {
        this.autorId = autorId;
    }

    public String isActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Avisos{" +
                "idAviso=" + idAviso +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", fechaExpiracion='" + fechaExpiracion + '\'' +
                ", autorId=" + autorId +
                ", activo=" + activo +
                '}';
    }

}

