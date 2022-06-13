package com.activity.libreria.modelos;

public class Administrador {
    int id;
    String nombreAdministrador;
    String correoAdministrador;
    String contraseñaAdministrador;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }

    public String getCorreoAdministrador() {
        return correoAdministrador;
    }

    public void setCorreoAdministrador(String correoAdministrador) {
        this.correoAdministrador = correoAdministrador;
    }

    public String getContraseñaAdministrador() {
        return contraseñaAdministrador;
    }

    public void setContraseñaAdministrador(String contraseñaAdministrador) {
        this.contraseñaAdministrador = contraseñaAdministrador;
    }
}
