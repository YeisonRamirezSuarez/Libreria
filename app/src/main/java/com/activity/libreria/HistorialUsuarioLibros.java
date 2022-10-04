package com.activity.libreria;

import static com.activity.libreria.bd.NetwordHelper.IP_PUBLICA;
import static com.activity.libreria.bd.NetwordHelper.PUERTO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.Interfaces.Callback;
import com.activity.libreria.adapter.AdapterAdministradorHistorialLibroPrestadoItems;
import com.activity.libreria.bd.Conexion;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Administrador;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaLibrosPrestados;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.Usuario;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HistorialUsuarioLibros extends AppCompatActivity implements View.OnClickListener, Callback {

    ImageView imageView_txt;
    TextView nombre_administrador_txt, rol;
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
    ListaUsuario listaUsuario;
    ListaLibros listaLibros;
    ListaLibrosPrestados listaLibrosPrestados;
    Conexion conexion;
    int id = 0;
    SPreferences sPreferences;
    boolean siExiste = true;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_historial_libros);
        traerIdRecyclerView(savedInstanceState);
        findElement();

    }

    private void findElement() {
        usuario = new Usuario();
        metodosAdministrador = new MetodosAdministrador(this);
        metodosLibros = new MetodosLibros(this);
        libros = new Libros();
        metodosUsuario = new MetodosUsuario(this);
        imageView_txt = findViewById(R.id.imageView_txt);
        nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        rol = findViewById(R.id.rol);
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
        conexion = new Conexion();
        listaUsuario = new ListaUsuario();
        listaLibros = new ListaLibros();
        listaLibrosPrestados = new ListaLibrosPrestados();
        sPreferences = new SPreferences(this);
        conexion.buscarUsuarios("http://"+IP_PUBLICA+":"+PUERTO+"/php/consulta_usuario.php?correo="+sPreferences.getSharedPreference()+"", this, this);
        conexion.consultaLibros("http://"+IP_PUBLICA+":"+PUERTO+"/php/consulta_libro_id.php?id="+id+"", this, this);


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return true;
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

    private void traerRecyclerView(Object object) {
        listaLibrosPrestados= (ListaLibrosPrestados) object;
        adapterAdministradorHistorialLibroPrestadoItems = new AdapterAdministradorHistorialLibroPrestadoItems(this, listaLibrosPrestados.getLibros());
        reciclarVistaHistorial.setAdapter(adapterAdministradorHistorialLibroPrestadoItems);
        reciclarVistaHistorial.setLayoutManager(new LinearLayoutManager(this));
        siExiste = true;
    }

    private void cargarDatosAdministrador(Object object) {
        // Aqui es como se muestra el nombre del Usuario que ingreso

        listaUsuario= (ListaUsuario) object;
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
    }

    private void cargarDatosLibro(Object object) {
        ListaLibros libros = (ListaLibros) object;
        listaLibros = libros;
        if (libros != null) {
            nombreLibroHistorial_ver.setText(libros.getLibros().get(0).getTitulo_libro());
            autorLibroHistorial_ver.setText(libros.getLibros().get(0).getAutor_libro());
            descripcionLibroHistorial_ver.setText(libros.getLibros().get(0).getDescripcion_libro());
            Glide.with(this)
                    .load(libros.getLibros().get(0).getImagen_libro())
                    .error(R.drawable.error)
                    .into(imageView_txt);
        }
        siExiste = false;
        conexion.consultaLibrosPrestados("http://"+IP_PUBLICA+":"+PUERTO+"/php/consulta_libros_prestados.php?id="+id+"", this, this);
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

    @Override
    public void getLibrosDisponibles(Object object) {
        if (siExiste) {
            cargarDatosLibro(object);
        }else{
            traerRecyclerView(object);
        }

    }

    @Override
    public void getUsuarioActivo(Object object) {
        cargarDatosAdministrador(object);
    }

    @Override
    public void onBackPressed() {

    }
}
