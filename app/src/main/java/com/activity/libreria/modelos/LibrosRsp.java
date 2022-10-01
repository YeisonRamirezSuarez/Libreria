package com.activity.libreria.modelos;

public class LibrosRsp {

    private int _id;
    private String Titulo_libro;
    private String Autor_libro;
    private String Cantidad_libro;
    private String Url_libro;
    private String Imagen_libro;
    private String Descripcion_libro;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitulo_libro() {
        return Titulo_libro;
    }

    public void setTitulo_libro(String titulo_libro) {
        Titulo_libro = titulo_libro;
    }

    public String getAutor_libro() {
        return Autor_libro;
    }

    public void setAutor_libro(String autor_libro) {
        Autor_libro = autor_libro;
    }

    public String getCantidad_libro() {
        return Cantidad_libro;
    }

    public void setCantidad_libro(String cantidad_libro) {
        Cantidad_libro = cantidad_libro;
    }

    public String getUrl_libro() {
        return Url_libro;
    }

    public void setUrl_libro(String url_libro) {
        Url_libro = url_libro;
    }

    public String getImagen_libro() {
        return Imagen_libro;
    }

    public void setImagen_libro(String imagen_libro) {
        Imagen_libro = imagen_libro;
    }

    public String getDescripcion_libro() {
        return Descripcion_libro;
    }

    public void setDescripcion_libro(String descripcion_libro) {
        Descripcion_libro = descripcion_libro;
    }
}
