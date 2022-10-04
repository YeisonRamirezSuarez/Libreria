package com.activity.libreria.modelos;

import java.util.ArrayList;

public class ListaLibrosPrestados {
ArrayList<LibrosPrestadosRsp> libros_prestados;

    public ArrayList<LibrosPrestadosRsp> getLibros() {
        return libros_prestados;
    }

    public void setLibros(ArrayList<LibrosPrestadosRsp> libros) {
        this.libros_prestados = libros;
    }
}
