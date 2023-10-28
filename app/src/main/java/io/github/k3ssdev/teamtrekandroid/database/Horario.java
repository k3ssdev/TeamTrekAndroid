package io.github.k3ssdev.teamtrekandroid.database;

import java.sql.Time;

public class Horario {

    private int ID;
    private String nombre;
    private String diaLaborable;
    private Time horaInicio;
    private Time horaFin;

    // Constructor, getters y setters

    public Horario(int ID, String nombre, String diaLaborable, Time horaInicio, Time horaFin) {
        this.ID = ID;
        this.nombre = nombre;
        this.diaLaborable = diaLaborable;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDiaLaborable() {
        return diaLaborable;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDiaLaborable(String diaLaborable) {
        this.diaLaborable = diaLaborable;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", diaLaborable='" + diaLaborable + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}
