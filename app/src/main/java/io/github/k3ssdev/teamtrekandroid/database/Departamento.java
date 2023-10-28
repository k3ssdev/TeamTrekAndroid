package io.github.k3ssdev.teamtrekandroid.database;

public class Departamento {

    private int ID;
    private String nombre;
    private String supervisor;

    // Constructor, getters y setters

    public Departamento(int ID, String nombre, String supervisor) {
        this.ID = ID;
        this.nombre = nombre;
        this.supervisor = supervisor;
    }

    public int getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", supervisor='" + supervisor + '\'' +
                '}';
    }
}
