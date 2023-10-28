package io.github.k3ssdev.teamtrekandroid.database;

import java.util.Date;

public class VacacionesAusencias {

    private int ID;
    private int empleadoID;
    private String tipo;
    private Date fechaInicio;
    private Date fechaFin;

    // Constructor, getters y setters

    public VacacionesAusencias(int ID, int empleadoID, String tipo, Date fechaInicio, Date fechaFin) {
        this.ID = ID;
        this.empleadoID = empleadoID;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getID() {
        return ID;
    }

    public int getEmpleadoID() {
        return empleadoID;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "VacacionesAusencias{" +
                "ID=" + ID +
                ", empleadoID=" + empleadoID +
                ", tipo='" + tipo + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }

}
