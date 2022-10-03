package com.activity.libreria.modelos;

public class UsuarioRsp {
    private int _id;
    private String Nombre_Usuario;
    private String Correo_Electronico;
    private String Telefono_Usuario;
    private String Direccion_Usuario;
    private String Contrasena_Usuario;
    private String Rol_Usuario;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNombre_Usuario() {
        return Nombre_Usuario;
    }

    public void setNombre_Usuario(String nombre_Usuario) {
        Nombre_Usuario = nombre_Usuario;
    }

    public String getCorreo_Electronico() {
        return Correo_Electronico;
    }

    public void setCorreo_Electronico(String correo_Electronico) {
        Correo_Electronico = correo_Electronico;
    }

    public String getTelefono_Usuario() {
        return Telefono_Usuario;
    }

    public void setTelefono_Usuario(String telefono_Usuario) {
        Telefono_Usuario = telefono_Usuario;
    }

    public String getDireccion_Usuario() {
        return Direccion_Usuario;
    }

    public void setDireccion_Usuario(String direccion_Usuario) {
        Direccion_Usuario = direccion_Usuario;
    }

    public String getContrasena_Usuario() {
        return Contrasena_Usuario;
    }

    public void setContrasena_Usuario(String contrasena_Usuario) {
        Contrasena_Usuario = contrasena_Usuario;
    }

    public String getRol_Usuario() {
        return Rol_Usuario;
    }

    public void setRol_Usuario(String rol_Usuario) {
        Rol_Usuario = rol_Usuario;
    }
}
