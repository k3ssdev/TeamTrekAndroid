package io.github.k3ssdev.teamtrekandroid.database;

import android.icu.math.BigDecimal;

public class PuestoTrabajo {
    private int ID;
    private String nombre;
    private String descripcion;
    private BigDecimal salarioBase;
    private int horasLaborables;

    // Constructor, getters y setters

    public PuestoTrabajo(int ID, String nombre, String descripcion, BigDecimal salarioBase, int horasLaborables) {
        this.ID = ID;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.salarioBase = salarioBase;
        this.horasLaborables = horasLaborables;
    }

    public int getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public int getHorasLaborables() {
        return horasLaborables;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }

    public void setHorasLaborables(int horasLaborables) {
        this.horasLaborables = horasLaborables;
    }

    @Override
    public String toString() {
        return "PuestoTrabajo{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", salarioBase=" + salarioBase +
                ", horasLaborables=" + horasLaborables +
                '}';
    }
}
