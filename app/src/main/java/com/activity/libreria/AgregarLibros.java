package com.activity.libreria;

import static com.activity.libreria.bd.NetwordHelper.IP_PUBLICA;
import static com.activity.libreria.bd.NetwordHelper.PUERTO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.activity.libreria.Interfaces.Callback;
import com.activity.libreria.bd.Conexion;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Administrador;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.ListaUsuario;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AgregarLibros extends AppCompatActivity implements View.OnClickListener, Callback {

    EditText nombreLibroInput;
    EditText autorLibroInput;
    EditText cantidadLibroInput;
    EditText urlLibroInput;
    TextView titulo;
    EditText urlImagenInput;
    EditText descripcionInput;
    ImageView volverLibros, funciones;
    TextView nombre_administrador_txt, rol;
    Button guardar_boton;
    Libros libros;
    MetodosAdministrador metodosAdministrador;
    MetodosLibros metodosLibros;
    Conexion conexion;
    ListaUsuario listaUsuario;
    SPreferences sPreferences;
    static int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_agregar_libro);
        findElement();
    }

    private void findElement() {
        libros = new Libros();
        metodosAdministrador = new MetodosAdministrador(this);
        metodosLibros = new MetodosLibros(this);
        nombreLibroInput = findViewById(R.id.nombreLibro_input);
        nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        rol = findViewById(R.id.rol);
        autorLibroInput = findViewById(R.id.autorLibro_input);
        cantidadLibroInput = findViewById(R.id.cantidadLibros_input);
        urlLibroInput = findViewById(R.id.urlLibro_input);
        urlImagenInput = findViewById(R.id.urlImagen_input);
        descripcionInput = findViewById(R.id.descripcion_input);
        volverLibros = findViewById(R.id.volverLibros);
        volverLibros.setVisibility(View.VISIBLE);
        volverLibros.setOnClickListener(this);
        funciones = findViewById(R.id.funcionesUser);
        funciones.setVisibility(View.GONE);
        guardar_boton = findViewById(R.id.guardar_boton);
        guardar_boton.setOnClickListener(this);
        titulo = findViewById(R.id.tituloBanner);
        titulo.setText("Agregar Libro");
        conexion = new Conexion();
        listaUsuario = new ListaUsuario();
        sPreferences = new SPreferences(this);
        conexion.buscarUsuarios("http://"+IP_PUBLICA+"/consulta_usuario.php?correo="+sPreferences.getSharedPreference()+"", this, this);

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


    private void cargarDatosAdministrador(Object object) {
        // Aqui es como se muestra el nombre del Usuario que ingreso

        listaUsuario= (ListaUsuario) object;
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.volverLibros:
                Intent i2 = new Intent(AgregarLibros.this, actividadAdministrador.class);
                startActivity(i2);
                break;
            case R.id.guardar_boton: {
                if (!nombreLibroInput.getText().toString().equals("") && !autorLibroInput.getText().toString().equals("")
                        && !cantidadLibroInput.getText().toString().equals("")
                        && !urlLibroInput.getText().toString().equals("")
                        && !urlImagenInput.getText().toString().equals("")
                        && !descripcionInput.getText().toString().equals("")) {
                    libros.setNombreLibro(nombreLibroInput.getText().toString());
                    libros.setAutorLibro(autorLibroInput.getText().toString());
                    libros.setCantidadLibro(cantidadLibroInput.getText().toString());
                    libros.setUrlLibro(urlLibroInput.getText().toString());
                    libros.setUrlImagen(urlImagenInput.getText().toString());
                    libros.setDescripcion(descripcionInput.getText().toString());
                    validar();
                } else {
                    Toast.makeText(this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }

            break;
        }
    }

    public boolean validar(){

        final String nombre=libros.getNombreLibro();
        final String autor=libros.getAutorLibro();
        final String cantidad=libros.getCantidadLibro();
        final String urlLibro=libros.getUrlLibro();
        final String imagen=libros.getUrlImagen();
        final String descripcion=libros.getDescripcion();

        String url="http://"+IP_PUBLICA+"/registro_libro.php?Titulo_libro="+nombre+"&Autor_libro="+autor+"&Cantidad_libro="+cantidad+"&Url_libro="+urlLibro+"&Imagen_libro="+imagen+"&Descripcion_libro="+descripcion+"";
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),
                        response,Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), actividadAdministrador.class);
                startActivity(i);
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
    public void getLibrosDisponibles(Object object) { }

    @Override
    public void getUsuarioActivo(Object object) {
        cargarDatosAdministrador(object);
    }

    @Override
    public void onBackPressed() {

    }
}