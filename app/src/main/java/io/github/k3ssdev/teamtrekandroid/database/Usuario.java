package io.github.k3ssdev.teamtrekandroid.database;

public class Usuario {

    private int ID;
    private String nombreUsuario;
    private String contraseña;
    private int empleadoID;

    // Constructor, getters y setters

    public Usuario(int ID, String nombreUsuario, String contraseña, int empleadoID) {
        this.ID = ID;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.empleadoID = empleadoID;
    }

    public int getID() {
        return ID;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getEmpleadoID() {
        return empleadoID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "ID=" + ID +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", empleadoID=" + empleadoID +
                '}';
    }

}
