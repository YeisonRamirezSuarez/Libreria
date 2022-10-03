package com.activity.libreria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activity.libreria.Interfaces.Callback;
import com.activity.libreria.bd.Conexion;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.Usuario;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PrestarLibroUsuario extends AppCompatActivity implements View.OnClickListener, Callback {

    TextView nombreLibro_ver;
    TextView nombre_usuario_txt;
    TextView rol;
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
    SPreferences sharedPreferences;
    Libros libros;
    Conexion conexion;
    ListaUsuario listaUsuario;
    ListaLibros listaLibros;
    int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prestar_libro_usuario);
        findElement();
        traerIdRecyclerView(savedInstanceState);

    }

    private void findElement() {
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        usuario = new Usuario();
        listaUsuario = new ListaUsuario();
        listaLibros = new ListaLibros();
        sharedPreferences = new SPreferences(this);
        nombreLibro_ver = findViewById(R.id.nombreLibro_ver);
        nombre_usuario_txt = findViewById(R.id.nombre_usuario_txt);
        rol = findViewById(R.id.rol);
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
        conexion = new Conexion();
        conexion.consultaLibros("http://192.168.1.11:80/php/libros_disponibles.php", this, this);
        conexion.buscarUsuarios("http://192.168.1.11/php/consulta_usuario.php?correo="+sharedPreferences.getSharedPreference()+"", this, this);
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

    private void cargarDatosUsuarios(Object object) {
        //Aqui obtiene el nombre del usuario

        ListaUsuario lista = (ListaUsuario) object;
        rol.setText(lista.getUsuarios().get(0).getRol_Usuario());
        nombre_usuario_txt.setText(lista.getUsuarios().get(0).getNombre_Usuario());
    }

    private void cargarDatosLibro(Object object) {
        ListaLibros libros = (ListaLibros) object;

        if (libros != null) {
            id--;
            nombreLibro_ver.setText(libros.getLibros().get(id).getTitulo_libro());
            autorLibro_ver.setText(libros.getLibros().get(id).getAutor_libro());
            descripcionLibro_ver.setText(libros.getLibros().get(id).getDescripcion_libro());
            Glide.with(this)
                    .load(libros.getLibros().get(id).getImagen_libro())
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
        switch (view.getId()) {
            case R.id.volverLibros:
                Intent i = new Intent(this, librosDisponiblesUsuario.class);
                startActivity(i);
                break;
            case R.id.prestar_libro:
                InputlibrosPrestado(listaLibros, listaUsuario);
                DateFormat df = new SimpleDateFormat(" d MMM yyyy, HH:mm"); //definir formato para fecha
                String fecha_registro = df.format(Calendar.getInstance().getTime()); //obtener fecha
                ArrayList<Libros> listaPrestado = metodosLibros.ArraysLibrosPre();
                libros.setId_libro(id);
                libros.setFechaPrestamo(fecha_registro);
                libros.setNombreLibro(nombreLibro_ver.getText().toString());
                libros.setAutorLibro(autorLibro_ver.getText().toString());
                libros.setDescripcion(descripcionLibro_ver.getText().toString());
                usuario = metodosUsuario.traerDatos();
                boolean siExiste = false;
                for (int j = 0; j < listaPrestado.size(); j++)
                {
                    if (listaPrestado.get(j).getCorreoPrestamo().equals(sharedPreferences.getSharedPreference()) && listaPrestado.get(j).getId_libro() == id)
                    {
                        siExiste = true;
                    }
                }
                if (siExiste)
                {
                    Toast.makeText(this, "Libro ya esta prestado", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //metodosLibros.prestarLibro(libros, usuario);
                    InputlibrosPrestado(listaLibros, listaUsuario);
                    libros.setCantidadLibro(String.valueOf(restarLibro()));
                    metodosLibros.actualizarCantidadLibro(libros);
                    Toast.makeText(getApplicationContext(),"Libro prestado...", Toast.LENGTH_LONG).show();

                }

                break;
        }
    }
    public boolean InputlibrosPrestado(ListaLibros listaLibros, ListaUsuario listaUsuario){
        DateFormat df = new SimpleDateFormat(" d MMM yyyy, HH:mm"); //definir formato para fecha
        String fecha_registro = df.format(Calendar.getInstance().getTime()); //obtener fecha
        final String nombre=listaLibros.getLibros().get(id).getTitulo_libro();
        final String autor=listaLibros.getLibros().get(id).getAutor_libro();
        final String cantidad=listaLibros.getLibros().get(id).getCantidad_libro();
        final String urlLibro=listaLibros.getLibros().get(id).getUrl_libro();
        final String imagen=listaLibros.getLibros().get(id).getImagen_libro();
        final String descripcion=listaLibros.getLibros().get(id).getDescripcion_libro();
        final String Fecha_Prestamo_libro=fecha_registro;
        final String Correo_Prestamo_libro=listaUsuario.getUsuarios().get(id).getCorreo_Electronico();
        final String Nombre_Usuario_Prestamo_libro=listaUsuario.getUsuarios().get(id).getNombre_Usuario();
        final String Telefono_Usuario_Prestamo_libro =listaUsuario.getUsuarios().get(id).getTelefono_Usuario();

        String url="http://192.168.1.21:80/php/registro_libro.php?Titulo_libro="+nombre+"&Autor_libro="+autor+"&Cantidad_libro="+cantidad+"&Url_libro="+urlLibro+"&Imagen_libro="+imagen+"&Descripcion_libro="+descripcion+"" +
                "&Fecha_Prestamo_libro="+Fecha_Prestamo_libro+"&Correo_Prestamo_libro="+Correo_Prestamo_libro+"&Nombre_Usuario_Prestamo_libro="+Nombre_Usuario_Prestamo_libro+"&Telefono_Usuario_Prestamo_libro="+Telefono_Usuario_Prestamo_libro+"";
       RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),
                        response,Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(getApplicationContext(), actividadUsuario.class);
                startActivity(i2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Error comunicación"+error,Toast.LENGTH_SHORT).show();
            }
        });
        servicio.add(respuesta);
        return true;
    }

    @Override
    public void getLibrosDisponibles(Object object) {
        cargarDatosLibro(object);
    }

    @Override
    public void getUsuarioActivo(Object object) {
        cargarDatosUsuarios(object);
    }
}

