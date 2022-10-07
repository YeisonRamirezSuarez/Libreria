package com.activity.libreria.MVP.Presenter;

import static com.activity.libreria.modelos.Constantes.*;

import android.content.Context;

import com.activity.libreria.MVP.Interfaces.interfaces;
import com.activity.libreria.MVP.Model.Model;
import com.activity.libreria.modelos.LibrosRsp;
import com.activity.libreria.modelos.UsuarioRsp;

public class Presenter implements interfaces.Presenter {

    interfaces.View view;
    interfaces.Model model;

    public Presenter(interfaces.View view, Context context) {
        this.view = view;
        this.model = new Model(this, context);
    }

    @Override
    public void consultarDatosLogin(UsuarioRsp usuarioRsp) {
        model.peticionValidarLogin(usuarioRsp);
    }

    @Override
    public void respuesta(String respuesta, String screen) {
        view.respuestas(respuesta, screen);
    }

    @Override
    public void pedirLibros(String screen) {
        model.peticionLibros(screen);
    }

    @Override
    public void respuestaLibros(Object object, String screen) {
        view.libros(object, screen);
    }

    @Override
    public void pedirUsuario() {
        model.peticionUsuario();
    }

    @Override
    public void respuestaUsuario(Object object) {
        view.mostrarUsuario(object);
    }

    @Override
    public void registrarLibro(LibrosRsp librosRsp) {
        model.peticionCrearLibro(librosRsp);

    }

    @Override
    public void actualizarLibro(LibrosRsp librosRsp, String screen) {
        model.peticionActualizarLibro(librosRsp, screen);
    }

    @Override
    public void borrarLibro(LibrosRsp librosRsp, String screen) {
        model.peticionEliminarLibro(librosRsp, screen);
    }
}
