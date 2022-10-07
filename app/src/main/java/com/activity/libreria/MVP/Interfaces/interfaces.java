package com.activity.libreria.MVP.Interfaces;

import android.content.Context;

import com.activity.libreria.modelos.LibrosRsp;
import com.activity.libreria.modelos.UsuarioRsp;

public interface interfaces {
    interface View{
        void verLogin();
        void verUsuario();
        void verAdministrador(Object object);
        void verPantallaCarga(String screen, Object object, String typo);
        void verCrearLibro();
        void verActualizarLibro(Object object);

        void mostrarUsuario(Object object);


        void respuestas(String respuesta, String screen);
        void libros(Object object, String screen);
        void showScreen(String screen, Object object, String typo);
    }

    interface Presenter{
        void consultarDatosLogin(UsuarioRsp usuarioRsp);
        void respuesta(String respuesta, String screen);

        void pedirLibros(String screen);
        void respuestaLibros(Object object, String screen);

        void pedirUsuario();
        void respuestaUsuario(Object object);

        void registrarLibro(LibrosRsp librosRsp);

        void actualizarLibro(LibrosRsp librosRsp, String screen);

        void borrarLibro(LibrosRsp librosRsp, String screen);

    }

    interface Model{
        void peticionValidarLogin(UsuarioRsp usuarioRsp);
        void peticionCrearLibro(LibrosRsp librosRsp);
        void peticionActualizarLibro(LibrosRsp librosRsp,  String screen);
        void peticionEliminarLibro(LibrosRsp librosRsp, String screen);

        void peticionUsuario();
        void peticionLibros(String screen);
    }
}
