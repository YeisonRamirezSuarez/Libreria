package com.activity.libreria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.Usuario;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PrestarLibroUsuario extends AppCompatActivity implements View.OnClickListener {

    TextView nombreLibro_ver;
    TextView nombre_usuario_txt;
    TextView autorLibro_ver;
    TextView descripcionLibro_ver;
    TextView nombreUser, titulo;
    TextView telefonoUser;
    ImageView volverLibros, funciones;
    ImageView imageView_txt;
    Button prestar_libro;
    Usuario usuario;
    MetodosUsuario metodosUsuario;
    MetodosLibros metodosLibros;
    Libros libros;
    int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prestar_libro_usuario);
        findElement();
        traerIdRecyclerView(savedInstanceState);
        cargarDatosUsuarios();
        cargarDatosLibro();
    }

    private void findElement() {
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        usuario = new Usuario();
        nombreLibro_ver = findViewById(R.id.nombreLibro_ver);
        nombre_usuario_txt = findViewById(R.id.nombre_usuario_txt);
        autorLibro_ver = findViewById(R.id.autorLibro_ver);
        descripcionLibro_ver = findViewById(R.id.descripcionLibro_ver);
        nombreUser = findViewById(R.id.nombre_usuario_historial_txt);
        telefonoUser = findViewById(R.id.telefono_historial_txt);
        imageView_txt = findViewById(R.id.imageView_txt);
        prestar_libro = findViewById(R.id.prestar_libro);
        prestar_libro.setOnClickListener(this);
        volverLibros = findViewById(R.id.volverLibros);
        volverLibros.setVisibility(View.VISIBLE);
        volverLibros.setOnClickListener(this);
        funciones = findViewById(R.id.funcionesUser);
        funciones.setVisibility(View.GONE);
        titulo = findViewById(R.id.tituloBannerUser);
        titulo.setText("Prestar Libro");
    }

    private void traerIdRecyclerView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }


    }

    private void cargarDatosUsuarios() {
        //Aqui obtiene el nombre del usuario
        ArrayList<Usuario> listaUsuario = metodosUsuario.almacenarDatosEnArraysUsuario();
        nombre_usuario_txt.setText(listaUsuario.get(0).getNombreUsuario());
    }

    private void cargarDatosLibro() {
        libros = metodosLibros.verLibro(id);

        if (libros != null) {

            nombreLibro_ver.setText(libros.getNombreLibro());
            autorLibro_ver.setText(libros.getAutorLibro());
            descripcionLibro_ver.setText(libros.getDescripcion());
            Glide.with(this)
                    .load(libros.getUrlImagen())
                    .error(R.drawable.error)
                    .into(imageView_txt);
        }
    }

    private int restarLibro(){
        int restar = Integer.parseInt(libros.getCantidadLibro()) - 1;
        return restar;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.volverLibros:
                Intent i = new Intent(this, librosDisponiblesUsuario.class);
                startActivity(i);
                break;
            case R.id.prestar_libro:
                DateFormat df = new SimpleDateFormat(" d MMM yyyy, HH:mm"); //definir formato para fecha
                String fecha_registro = df.format(Calendar.getInstance().getTime()); //obtener fecha
                libros.setId_libro(id);
                libros.setFechaPrestamo(fecha_registro);
                libros.setNombreLibro(nombreLibro_ver.getText().toString());
                libros.setAutorLibro(autorLibro_ver.getText().toString());
                libros.setDescripcion(descripcionLibro_ver.getText().toString());
                usuario = metodosUsuario.traerDatos();
                metodosLibros.prestarLibro(libros, usuario);
                libros.setCantidadLibro(String.valueOf(restarLibro()));
                metodosLibros.actualizarCantidadLibro(libros);
                Toast.makeText(getApplicationContext(),"Libro prestado...", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(this, actividadUsuario.class);
                startActivity(i2);
                break;
        }
    }
}

