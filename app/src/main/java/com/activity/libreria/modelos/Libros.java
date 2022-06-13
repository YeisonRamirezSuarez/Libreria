package com.activity.libreria.modelos;

public class Libros {
    private int id;
    private int id_libro;
    private String nombreLibro;
    private String autorLibro;
    private String cantidadLibro;
    private String urlLibro;
    private String urlImagen;
    private String descripcion;
    private String fechaPrestamo;
    private String nombrePrestamo;
    private String telefonoPrestamo;
    private String correoPrestamo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public String getAutorLibro() {
        return autorLibro;
    }

    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }

    public String getCantidadLibro() {
        return cantidadLibro;
    }

    public String getUrlLibro() {
        return urlLibro;
    }

    public void setUrlLibro(String urlLibro) {
        this.urlLibro = urlLibro;
    }

    public void setCantidadLibro(String cantidadLibro) {
        this.cantidadLibro = cantidadLibro;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }


    public String getNombrePrestamo() {
        return nombrePrestamo;
    }

    public void setNombrePrestamo(String nombrePrestamo) {
        this.nombrePrestamo = nombrePrestamo;
    }

    public String getTelefonoPrestamo() {
        return telefonoPrestamo;
    }

    public void setTelefonoPrestamo(String telefonoPrestamo) {
        this.telefonoPrestamo = telefonoPrestamo;
    }

    public String getCorreoPrestamo() {
        return correoPrestamo;
    }

    public void setCorreoPrestamo(String correoPrestamo) {
        this.correoPrestamo = correoPrestamo;
    }
}
