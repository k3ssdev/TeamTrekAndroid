package io.github.k3ssdev.teamtrekandroid.database;

import java.sql.Time;
import java.util.Date;

public class Asistencia {

    private int ID;
    private int empleadoID;
    private Date fecha;
    private Time horaEntrada;
    private Time horaSalida;

    // Constructor, getters y setters

    public Asistencia(int ID, int empleadoID, Date fecha, Time horaEntrada, Time horaSalida) {
        this.ID = ID;
        this.empleadoID = empleadoID;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public int getID() {
        return ID;
    }

    public int getEmpleadoID() {
        return empleadoID;
    }

    public Date getFecha() {
        return fecha;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    @Override
    public String toString() {
        return "Asistencia{" +
                "ID=" + ID +
                ", empleadoID=" + empleadoID +
                ", fecha=" + fecha +
                ", horaEntrada=" + horaEntrada +
                ", horaSalida=" + horaSalida +
                '}';
    }
}
