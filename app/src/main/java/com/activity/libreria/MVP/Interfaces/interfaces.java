package com.activity.libreria.MVP.Interfaces;

import com.activity.libreria.modelos.LibrosPrestadosRsp;
import com.activity.libreria.modelos.LibrosRsp;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.UsuarioRsp;

public interface interfaces {
    interface View{
        void verLogin();
        void verUsuario(Object object);
        void verAdministrador(Object object);
        void verPantallaCarga(String screen, Object object, String typo);
        void verCrearLibro();
        void verActualizarLibro(Object object);
        void verLibrosPrestadosAdmin(Object object);
        void verHistorialLibrosPrestados(Object object, Object object2);
        void verLibrosDisponiblesUsuario(Object object);
        void verPrestarLibro(Object object);
        void verLeerLibro(Object object);

        void mostrarUsuario(Object object);


        void respuestas(String respuesta, String screen);
        void libros(Object object, Object object2, String screen);
        void showScreen(String screen, Object object, String typo);
    }

    interface Presenter{
        void consultarDatosLogin(UsuarioRsp usuarioRsp);
        void respuesta(String respuesta, String screen);

        void pedirLibros(String screen);
        void pedirLibrosPrestadosUsuario(String screen);
        void pedirLibrosPrestados(String screen);
        void pedirLibrosPrestadosId(Object object, String screen);
        void respuestaLibros(Object object, String screen);

        void pedirUsuario();
        void respuestaUsuario(Object object);
        void respuestaLibrosPrestados(Object object, Object object2, String screen);

        void registrarLibro(LibrosRsp librosRsp);

        void actualizarLibro(LibrosRsp librosRsp, String screen);

        void borrarLibro(LibrosRsp librosRsp, String screen);
        void borrarLibroPrestado(LibrosPrestadosRsp librosPrestadosRsp, String screen);
        void prestarLibro(ListaUsuario listaUsuario, LibrosRsp librosRsp, String screen);
        void consultarLibroPrestado(String screen);

    }

    interface Model{
        void peticionValidarLogin(UsuarioRsp usuarioRsp);
        void peticionCrearLibro(LibrosRsp librosRsp);
        void peticionActualizarLibro(LibrosRsp librosRsp,  String screen);
        void peticionEliminarLibro(LibrosRsp librosRsp, String screen);
        void peticionEliminarLibroPrestado(LibrosPrestadosRsp librosPrestadosRsp,String screen);

        void peticionPrestarLibro(ListaUsuario listaUsuario, LibrosRsp librosRsp, String screen);

        void peticionUsuario();
        void peticionLibros(String screen);
        void peticionLibrosPrestados(String screen);
        void peticionLibrosPrestadosId(Object object, String screen);
        void peticionLibrosPrestadosUsuario(String screen);

        void peticionConsultaLibroPrestados(String screen);
    }
}
