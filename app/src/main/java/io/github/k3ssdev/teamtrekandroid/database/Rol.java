package io.github.k3ssdev.teamtrekandroid.database;

public class Rol {

    private int ID;
    private String nombre;

    // Constructor, getters y setters

    public Rol(int ID, String nombre) {
        this.ID = ID;
        this.nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
