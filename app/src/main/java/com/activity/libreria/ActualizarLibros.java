package com.activity.libreria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.modelos.Administrador;
import com.activity.libreria.modelos.Libros;

import java.util.ArrayList;

public class ActualizarLibros extends AppCompatActivity implements View.OnClickListener {

    TextView nombre_administrador_txt;
    EditText nombreLibroView;
    EditText autorLibroView;
    TextView titulo;
    EditText cantidadLibroView;
    EditText urlLibroView;
    EditText urlImagenView;
    EditText descripcionView;
    ImageView volverView;
    ImageView funciones;
    Button actualizar_boton;
    MetodosUsuario metodosUsuario;
    MetodosAdministrador metodosAdministrador;
    MetodosLibros metodosLibros;
    Libros libros;
    BDHelper bdHelper;
    int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_actualizar_libro);
        findElement();
        traerIdRecyclerView(savedInstanceState);
        cargarDatosAdministrador();
        cargarDatosLibro();
    }

    private void findElement() {
        bdHelper = new BDHelper(this);
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        metodosAdministrador = new MetodosAdministrador(this);
        nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        nombreLibroView = findViewById(R.id.nombreLibro_view);
        autorLibroView = findViewById(R.id.autorLibro_view);
        cantidadLibroView = findViewById(R.id.cantidadLibros_view);
        urlLibroView = findViewById(R.id.urlLibro_view);
        urlImagenView = findViewById(R.id.urlImagen_view);
        descripcionView = findViewById(R.id.descripcion_view);
        volverView = findViewById(R.id.volverLibros);
        volverView.setVisibility(View.VISIBLE);
        funciones = findViewById(R.id.funcionesUser);
        funciones.setVisibility(View.GONE);
        volverView.setOnClickListener(this);
        actualizar_boton = findViewById(R.id.actualizar_boton);
        actualizar_boton.setOnClickListener(this);
        titulo = findViewById(R.id.tituloBanner);
        titulo.setText("Actualizar Libros");

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

    private void cargarDatosAdministrador() {
        //Nombre View del administrador
        ArrayList<Administrador> listaAdministrador = metodosAdministrador.selectAdministrador();
        nombre_administrador_txt.setText(listaAdministrador.get(0).getNombreAdministrador());
    }

    private void cargarDatosLibro() {
        libros = metodosLibros.verLibro(id);

        if (libros != null) {
            nombreLibroView.setText(libros.getNombreLibro());
            autorLibroView.setText(libros.getAutorLibro());
            cantidadLibroView.setText(libros.getCantidadLibro());
            urlLibroView.setText(libros.getUrlLibro());
            urlImagenView.setText(libros.getUrlImagen());
            descripcionView.setText(libros.getDescripcion());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.volverLibros:
                Intent i2 = new Intent(this, actividadAdministrador.class);
                startActivity(i2);
                break;
            case R.id.actualizar_boton:
            {
                if (!nombreLibroView.getText().toString().equals("") && !autorLibroView.getText().toString().equals("")
                        && !cantidadLibroView.getText().toString().equals("")
                        && !urlLibroView.getText().toString().equals("")
                        && !urlImagenView.getText().toString().equals("")
                        && !descripcionView.getText().toString().equals("")) {
                    boolean correcto = metodosLibros.editarLibro(libros);
                    if (correcto) {
                        libros.setNombreLibro(nombreLibroView.getText().toString());
                        libros.setAutorLibro(autorLibroView.getText().toString());
                        libros.setCantidadLibro(cantidadLibroView.getText().toString());
                        libros.setUrlLibro(urlLibroView.getText().toString());
                        libros.setUrlImagen(urlImagenView.getText().toString());
                        libros.setDescripcion(descripcionView.getText().toString());
                        metodosLibros.editarLibro(libros);
                        metodosLibros.editarLibroPrestado(libros);

                        Toast.makeText(this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this, actividadAdministrador.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
                break;
        }
    }
}

