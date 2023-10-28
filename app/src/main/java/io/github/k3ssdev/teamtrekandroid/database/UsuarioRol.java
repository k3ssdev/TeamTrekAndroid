package io.github.k3ssdev.teamtrekandroid.database;

public class UsuarioRol {

    private int usuarioID;
    private int rolID;

    // Constructor, getters y setters

    public UsuarioRol(int usuarioID, int rolID) {
        this.usuarioID = usuarioID;
        this.rolID = rolID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public int getRolID() {
        return rolID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public void setRolID(int rolID) {
        this.rolID = rolID;
    }

    @Override
    public String toString() {
        return "UsuarioRol{" +
                "usuarioID=" + usuarioID +
                ", rolID=" + rolID +
                '}';
    }

}
