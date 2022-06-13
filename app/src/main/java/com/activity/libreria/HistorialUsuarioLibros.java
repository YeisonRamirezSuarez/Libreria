package com.activity.libreria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.adapter.AdapterAdministradorHistorialLibroPrestadoItems;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.modelos.Administrador;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.Usuario;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HistorialUsuarioLibros extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView_txt;
    TextView nombre_administrador_txt;
    ImageView volverLibros, funciones;
    TextView nombreLibroHistorial_ver;
    AdapterAdministradorHistorialLibroPrestadoItems adapterAdministradorHistorialLibroPrestadoItems;
    RecyclerView reciclarVistaHistorial;
    TextView autorLibroHistorial_ver, titulo;
    TextView descripcionLibroHistorial_ver;
    Libros libros;
    Usuario usuario;
    MetodosLibros metodosLibros;
    MetodosAdministrador metodosAdministrador;
    MetodosUsuario metodosUsuario;
    int id = 0;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_historial_libros);
        traerIdRecyclerView(savedInstanceState);
        findElement();
        traerRecyclerView();
        cargarDatosUsuarios();
        cargarDatosLibro();
    }

    private void findElement() {
        usuario = new Usuario();
        metodosAdministrador = new MetodosAdministrador(this);
        metodosLibros = new MetodosLibros(this);
        libros = new Libros();
        metodosUsuario = new MetodosUsuario(this);
        imageView_txt = findViewById(R.id.imageView_txt);
        nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        nombreLibroHistorial_ver = findViewById(R.id.nombreLibroHistorial_ver);
        reciclarVistaHistorial = findViewById(R.id.reciclarVistaHistorial);
        autorLibroHistorial_ver = findViewById(R.id.autorLibroHistorial_ver);
        descripcionLibroHistorial_ver = findViewById(R.id.descripcionLibroHistorial_ver);
        volverLibros = findViewById(R.id.volverLibros);
        volverLibros.setOnClickListener(this);
        volverLibros.setVisibility(View.VISIBLE);
        titulo = findViewById(R.id.tituloBanner);
        titulo.setText("MiLibro");
        funciones = findViewById(R.id.funcionesUser);
        funciones.setVisibility(View.GONE);

    }

    private void traerIdRecyclerView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else id = (int) savedInstanceState.getSerializable("ID");
    }

    private void traerRecyclerView() {
        ArrayList<Usuario> listaUser = metodosUsuario.correoPrestado(id);
        metodosUsuario.correoUsuarioPrestado(usuario.getCorreoUsuario());
        adapterAdministradorHistorialLibroPrestadoItems = new AdapterAdministradorHistorialLibroPrestadoItems(this, listaUser);
        reciclarVistaHistorial.setAdapter(adapterAdministradorHistorialLibroPrestadoItems);
        reciclarVistaHistorial.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarDatosUsuarios() {
        //Nombre en Vista
        ArrayList<Administrador> listaAdministrador = metodosAdministrador.selectAdministrador();
        nombre_administrador_txt.setText(listaAdministrador.get(0).getNombreAdministrador());
    }

    private void cargarDatosLibro() {
        libros = metodosLibros.verLibro(id);

        if (libros != null) {
            nombreLibroHistorial_ver.setText(libros.getNombreLibro());
            autorLibroHistorial_ver.setText(libros.getAutorLibro());
            descripcionLibroHistorial_ver.setText(libros.getDescripcion());
            Glide.with(this)
                    .load(libros.getUrlImagen())
                    .error(R.drawable.error)
                    .into(imageView_txt);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.volverLibros:
                Intent i2 = new Intent(HistorialUsuarioLibros.this, LibrosPrestadosAdmin.class);
                startActivity(i2);
                break;


        }
    }
}
