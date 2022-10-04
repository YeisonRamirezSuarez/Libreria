package com.activity.libreria;

import static com.activity.libreria.bd.BDHelper.TABLE_LIBROS;
import static com.activity.libreria.bd.BDHelper.TABLE_LIBROS_PRESTADOS;
import static com.activity.libreria.bd.BDHelper.TABLE_USER;
import static com.activity.libreria.bd.NetwordHelper.IP_PUBLICA;
import static com.activity.libreria.bd.NetwordHelper.PUERTO;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.activity.libreria.Interfaces.Callback;
import com.activity.libreria.adapter.AdapterUsuarioLibroItems;
import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.bd.Conexion;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaLibrosPrestados;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.Usuario;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VerMiLibro extends AppCompatActivity implements View.OnClickListener, Callback {

    TextView nombreLibro_ver;
    TextView nombre_usuario_txt, rol;
    TextView autorLibro_ver;
    TextView descripcionLibro_ver, titulo;
    ImageView imageView_txt;
    ImageView volverLibros, funciones;
    Button devolver_boton;
    Button ver_libro_boton;
    MetodosUsuario metodosUsuario;
    MetodosLibros metodosLibros;
    Libros libros;
    SPreferences sharedPreferences;
    ListaLibros listaLibros;
    ListaUsuario listaUsuario;
    Conexion conexion;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_usuario_libro);
        traerIdRecyclerView(savedInstanceState);
        findElement();

    }

    private void findElement() {
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        nombre_usuario_txt = findViewById(R.id.nombre_usuario_txt);
        rol = findViewById(R.id.rol);
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
        sharedPreferences = new SPreferences(this);
        conexion = new Conexion();
        conexion.consultaLibros("http://"+IP_PUBLICA+":"+PUERTO+"/php/consulta_libro_id.php?id="+id+"", this, this);
        conexion.buscarUsuarios("http://"+IP_PUBLICA+":"+PUERTO+"/php/consulta_usuario.php?correo="+sharedPreferences.getSharedPreference()+"", this, this);
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
        // Aqui es como se muestra el nombre del Usuario que ingreso

        listaUsuario= (ListaUsuario) object;
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_usuario_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
    }

    private void cargarDatosLibro(Object object) {
        ListaLibros libros = (ListaLibros) object;
        listaLibros = libros;
        if (libros != null) {
            nombreLibro_ver.setText(libros.getLibros().get(0).getTitulo_libro());
            autorLibro_ver.setText(libros.getLibros().get(0).getAutor_libro());
            descripcionLibro_ver.setText(libros.getLibros().get(0).getDescripcion_libro());
            Glide.with(this)
                    .load(libros.getLibros().get(0).getImagen_libro())
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
                    intent.setData(Uri.parse(listaLibros.getLibros().get(0).getUrl_libro()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "URL NO EXISTENTE...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.devolver_boton:
               // metodosLibros.eliminarLibroPrestado(libros);
                //libros = metodosLibros.traerDatosCantidadd(libros.getId_libro());
                //int suma = sumarLibro(libros.getCantidadLibro());
                //libros.setCantidadLibro(String.valueOf(suma));
                //metodosLibros.actualizarCantidadLibro(libros);
                eliminarLibroPrestado("http://"+IP_PUBLICA+":"+PUERTO+"/php/eliminar_libro_prestado.php?id="+listaLibros.getLibros().get(0).get_id()+"");

        }

    }
    public void eliminarLibroPrestado(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), actividadUsuario.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

