package com.activity.libreria.modelos;

import java.util.ArrayList;

public class ListaLibrosPrestados {
ArrayList<LibrosPrestadosRsp> data;

    public ArrayList<LibrosPrestadosRsp> getLibros() {
        return data;
    }

    public void setLibros(ArrayList<LibrosPrestadosRsp> libros) {
        this.data = libros;
    }
}
