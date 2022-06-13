package com.activity.libreria;

import static com.activity.libreria.bd.BDHelper.TABLE_LIBROS;
import static com.activity.libreria.bd.BDHelper.TABLE_LIBROS_PRESTADOS;
import static com.activity.libreria.bd.BDHelper.TABLE_USER;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.Usuario;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VerMiLibro extends AppCompatActivity implements View.OnClickListener {

    TextView nombreLibro_ver;
    TextView nombre_usuario_txt;
    TextView autorLibro_ver;
    TextView descripcionLibro_ver, titulo;
    ImageView imageView_txt;
    ImageView volverLibros, funciones;
    Button devolver_boton;
    Button ver_libro_boton;
    MetodosUsuario metodosUsuario;
    MetodosLibros metodosLibros;
    Libros libros;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_usuario_libro);
        findElement();
        traerIdRecyclerView(savedInstanceState);
        cargarDatosUsuarios();
        cargarDatosLibro();
    }

    private void findElement() {
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        nombre_usuario_txt = findViewById(R.id.nombre_usuario_txt);
        nombreLibro_ver = findViewById(R.id.nombreLibro_ver);
        autorLibro_ver = findViewById(R.id.autorLibro_ver);
        descripcionLibro_ver = findViewById(R.id.descripcionLibro_ver);
        imageView_txt = findViewById(R.id.imageView_txt);
        volverLibros = findViewById(R.id.volverLibros);
        volverLibros.setVisibility(View.VISIBLE);
        volverLibros.setOnClickListener(this);
        devolver_boton = findViewById(R.id.devolver_boton);
        devolver_boton.setOnClickListener(this);
        ver_libro_boton = findViewById(R.id.ver_libro_boton);
        ver_libro_boton.setOnClickListener(this);
        funciones = findViewById(R.id.funcionesUser);
        funciones.setVisibility(View.GONE);
        titulo = findViewById(R.id.tituloBannerUser);
        titulo.setText("MiLibro");

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
        libros = metodosLibros.verLibroPrestado(id);

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

    private int sumarLibro(String cantidad) {
        int sumar = Integer.parseInt(cantidad) + 1;
        return sumar;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.volverLibros:
                Intent i = new Intent(this, actividadUsuario.class);
                startActivity(i);
                break;
            case R.id.ver_libro_boton:
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(libros.getUrlLibro()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "URL NO EXISTENTE...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.devolver_boton:
                metodosLibros.eliminarLibroPrestado(libros);
                libros = metodosLibros.traerDatosCantidadd(libros.getId_libro());
                int suma = sumarLibro(libros.getCantidadLibro());
                libros.setCantidadLibro(String.valueOf(suma));
                metodosLibros.actualizarCantidadLibro(libros);
                Intent i3 = new Intent(this, actividadUsuario.class);
                startActivity(i3);
        }

    }
}

