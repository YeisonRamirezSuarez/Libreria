package com.activity.libreria.modelos;

public class LibrosPrestadosRsp {
    private int _id;
    private int _id_Libro;
    private String Titulo_libro_Prestado;
    private String Autor_libro_Prestado;
    private String Url_libro_Prestado;
    private String Imagen_libro_Prestado;
    private String Descripcion_libro_Prestado;
    private String Fecha_Prestamo_libro;
    private String Correo_Prestamo_libro;
    private String Nombre_Usuario_Prestamo_libro;
    private String Telefono_Usuario_Prestamo_libro;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id_Libro() {
        return _id_Libro;
    }

    public void set_id_Libro(int _id_Libro) {
        this._id_Libro = _id_Libro;
    }

    public String getTitulo_libro_Prestado() {
        return Titulo_libro_Prestado;
    }

    public void setTitulo_libro_Prestado(String titulo_libro_Prestado) {
        Titulo_libro_Prestado = titulo_libro_Prestado;
    }

    public String getAutor_libro_Prestado() {
        return Autor_libro_Prestado;
    }

    public void setAutor_libro_Prestado(String autor_libro_Prestado) {
        Autor_libro_Prestado = autor_libro_Prestado;
    }

    public String getUrl_libro_Prestado() {
        return Url_libro_Prestado;
    }

    public void setUrl_libro_Prestado(String url_libro_Prestado) {
        Url_libro_Prestado = url_libro_Prestado;
    }

    public String getImagen_libro_Prestado() {
        return Imagen_libro_Prestado;
    }

    public void setImagen_libro_Prestado(String imagen_libro_Prestado) {
        Imagen_libro_Prestado = imagen_libro_Prestado;
    }

    public String getDescripcion_libro_Prestado() {
        return Descripcion_libro_Prestado;
    }

    public void setDescripcion_libro_Prestado(String descripcion_libro_Prestado) {
        Descripcion_libro_Prestado = descripcion_libro_Prestado;
    }

    public String getFecha_Prestamo_libro() {
        return Fecha_Prestamo_libro;
    }

    public void setFecha_Prestamo_libro(String fecha_Prestamo_libro) {
        Fecha_Prestamo_libro = fecha_Prestamo_libro;
    }

    public String getCorreo_Prestamo_libro() {
        return Correo_Prestamo_libro;
    }

    public void setCorreo_Prestamo_libro(String correo_Prestamo_libro) {
        Correo_Prestamo_libro = correo_Prestamo_libro;
    }

    public String getNombre_Usuario_Prestamo_libro() {
        return Nombre_Usuario_Prestamo_libro;
    }

    public void setNombre_Usuario_Prestamo_libro(String nombre_Usuario_Prestamo_libro) {
        Nombre_Usuario_Prestamo_libro = nombre_Usuario_Prestamo_libro;
    }

    public String getTelefono_Usuario_Prestamo_libro() {
        return Telefono_Usuario_Prestamo_libro;
    }

    public void setTelefono_Usuario_Prestamo_libro(String telefono_Usuario_Prestamo_libro) {
        Telefono_Usuario_Prestamo_libro = telefono_Usuario_Prestamo_libro;
    }
}
