package io.github.k3ssdev.teamtrekandroid.database;

// Clase POJO para el usuario
public class User {
    private final String nombreUsuario_apr;
    private final String contrasena_apr;
    private final String fechaNacimiento_apr;

    // Constructor de la clase User
    public User(String nombreUsuario, String contrasena, String fechaNacimiento) {
        this.nombreUsuario_apr = nombreUsuario;
        this.contrasena_apr = contrasena;
        this.fechaNacimiento_apr = fechaNacimiento;
    }

    // Método para obtener el nombre de usuario
    public String getNombreUsuario() {
        return nombreUsuario_apr;
    }

    // Método para obtener la contraseña
    public String getContrasena() {
        return contrasena_apr;
    }

    // Método para obtener la fecha de nacimiento
    public String getFechaNacimiento() {
        return fechaNacimiento_apr;
    }
}