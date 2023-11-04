package io.github.k3ssdev.teamtrekandroid.database;

import java.util.Date;

public class Empleado {
    private int ID;
    private String nombre;
    private Date fechaIngreso;
    private String nombreDepartamento;
    private String descripcionHorario;
    private Date horaInicio;
    private Date horaFin;
    private String usuario;
    private String telefono;
    private String direccion;
    private String email;
    private Date fechaNacimiento;
    private String nif;

    // Constructor con todos los campos
    public Empleado(int ID, String nombre, Date fechaIngreso, String nombreDepartamento,
                    String descripcionHorario, Date horaInicio, Date horaFin,
                    String usuario, String telefono, String direccion,
                    String email, Date fechaNacimiento, String nif) {
        this.ID = ID;
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.nombreDepartamento = nombreDepartamento;
        this.descripcionHorario = descripcionHorario;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.usuario = usuario;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.nif = nif;
    }

    // Getters y setters

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getDescripcionHorario() {
        return descripcionHorario;
    }

    public void setDescripcionHorario(String descripcionHorario) {
        this.descripcionHorario = descripcionHorario;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    // toString
    @Override
    public String toString() {
        // Implementación de toString que incluye todos los campos
        return "Empleado{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", nombreDepartamento='" + nombreDepartamento + '\'' +
                ", descripcionHorario='" + descripcionHorario + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", usuario='" + usuario + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", nif='" + nif + '\'' +
                '}';
    }

    // Métodos adicionales según sea necesario
    // ...
}
