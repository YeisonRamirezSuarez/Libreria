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

import androidx.appcompat.app.AppCompatActivity;

import com.activity.libreria.Interfaces.Callback;
import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.bd.Conexion;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.LibrosRsp;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaUsuario;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ActualizarLibros extends AppCompatActivity implements View.OnClickListener, Callback {

    TextView nombre_administrador_txt, rol;
    EditText nombreLibroView;
    EditText autorLibroView;
    TextView titulo;
    EditText cantidadLibroView;
    EditText urlLibroView;
    EditText urlImagenView;
    EditText descripcionView;
    ImageView volverView;
    ImageView funciones, borrar;
    Button actualizar_boton;
    MetodosUsuario metodosUsuario;
    MetodosAdministrador metodosAdministrador;
    MetodosLibros metodosLibros;
    Libros libros;
    LibrosRsp librosRsp;
    BDHelper bdHelper;
    ListaUsuario listaUsuario;
    ListaLibros listaLibros;
    Conexion conexion;
    SPreferences sPreferences;
    int id_libro = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_actualizar_libro);
        traerIdRecyclerView(savedInstanceState);
        findElement();


    }

    private void findElement() {
        bdHelper = new BDHelper(this);
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        metodosAdministrador = new MetodosAdministrador(this);
        nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        rol = findViewById(R.id.rol);
        nombreLibroView = findViewById(R.id.nombreLibro_view);
        autorLibroView = findViewById(R.id.autorLibro_view);
        cantidadLibroView = findViewById(R.id.cantidadLibros_view);
        urlLibroView = findViewById(R.id.urlLibro_view);
        urlImagenView = findViewById(R.id.urlImagen_view);
        descripcionView = findViewById(R.id.descripcion_view);
        volverView = findViewById(R.id.volverLibros);
        volverView.setVisibility(View.VISIBLE);
        funciones = findViewById(R.id.funcionesUser);
        borrar = findViewById(R.id.borrar);
        borrar.setVisibility(View.VISIBLE);
        funciones.setVisibility(View.GONE);
        volverView.setOnClickListener(this);
        actualizar_boton = findViewById(R.id.actualizar_boton);
        actualizar_boton.setOnClickListener(this);
        titulo = findViewById(R.id.tituloBanner);
        titulo.setText("Actualizar Libros");
        conexion = new Conexion();
        listaLibros = new ListaLibros();
        listaUsuario = new ListaUsuario();
        librosRsp = new LibrosRsp();
        sPreferences = new SPreferences(this);
        clickBorrar();
        conexion.consultaLibros("http://"+IP_PUBLICA+":"+PUERTO+"/php/consulta_libro_id.php?id="+ id_libro +"", this, this);
        conexion.buscarUsuarios("http://"+IP_PUBLICA+":"+PUERTO+"/php/consulta_usuario.php?correo="+sPreferences.getSharedPreference()+"", this, this);

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
                id_libro = Integer.parseInt(null);
            } else {
                id_libro = extras.getInt("ID");
            }
        } else id_libro = (int) savedInstanceState.getSerializable("ID");
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
            nombreLibroView.setText(listaLibros.getLibros().get(0).getTitulo_libro());
            autorLibroView.setText(listaLibros.getLibros().get(0).getAutor_libro());
            cantidadLibroView.setText(listaLibros.getLibros().get(0).getCantidad_libro());
            urlLibroView.setText(listaLibros.getLibros().get(0).getUrl_libro());
            urlImagenView.setText(listaLibros.getLibros().get(0).getImagen_libro());
            descripcionView.setText(listaLibros.getLibros().get(0).getDescripcion_libro());
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
                seteo();
                if (!nombreLibroView.getText().toString().equals("") && !autorLibroView.getText().toString().equals("")
                        && !cantidadLibroView.getText().toString().equals("")
                        && !urlLibroView.getText().toString().equals("")
                        && !urlImagenView.getText().toString().equals("")
                        && !descripcionView.getText().toString().equals("")) {
                  actualizarLibro();
                  actualizar_libro_prestado();
                } else {
                    Toast.makeText(this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
                break;
        }
    }

    private void seteo() {
        librosRsp.setTitulo_libro(nombreLibroView.getText().toString().trim());
        librosRsp.setAutor_libro(autorLibroView.getText().toString().trim());
        librosRsp.setCantidad_libro(cantidadLibroView.getText().toString().trim());
        librosRsp.setUrl_libro(urlLibroView.getText().toString().trim());
        librosRsp.setImagen_libro(urlImagenView.getText().toString().trim());
        librosRsp.setDescripcion_libro(descripcionView.getText().toString().trim());
    }

    public boolean actualizarLibro(){

         String nombre=librosRsp.getTitulo_libro();
         String autor=librosRsp.getAutor_libro();
         String cantidad=librosRsp.getCantidad_libro();
         String urlLibro=librosRsp.getUrl_libro();
         String imagen=librosRsp.getImagen_libro();
         String descripcion=librosRsp.getDescripcion_libro();

        String url="http://"+IP_PUBLICA+":"+PUERTO+"/php/actualizar_libro.php?id="+ id_libro +"&Titulo_libro="+nombre+"&Autor_libro="+autor+"&Cantidad_libro="+cantidad+"&Url_libro="+urlLibro+"&Imagen_libro="+imagen+"&Descripcion_libro="+descripcion+"";
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("correcto"))
                {
                    Toast.makeText(getApplicationContext(),
                            "Libro actualizado correctamente",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), actividadAdministrador.class);
                    startActivity(i);
                }else if(response.equals("Fallo")){
                    Toast.makeText(getApplicationContext(),
                            "Fallo la actualizacion correctamente",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        servicio.add(respuesta);
        return true;
    }

    public boolean actualizar_libro_prestado(){

        String nombre=librosRsp.getTitulo_libro();
        String autor=librosRsp.getAutor_libro();
        String cantidad=librosRsp.getCantidad_libro();
        String urlLibro=librosRsp.getUrl_libro();
        String imagen=librosRsp.getImagen_libro();
        String descripcion=librosRsp.getDescripcion_libro();

        String url="http://"+IP_PUBLICA+":"+PUERTO+"/php/actualizar_libro_prestado.php?id="+ id_libro +"&Titulo_libro="+nombre+"&Autor_libro="+autor+"&Cantidad_libro="+cantidad+"&Url_libro="+urlLibro+"&Imagen_libro="+imagen+"&Descripcion_libro="+descripcion+"";
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.printf(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        servicio.add(respuesta);
        return true;
    }

    public void eliminarLibro(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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

    public void eliminarLibroPrestado(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(getApplicationContext(), actividadAdministrador.class);
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

    private void clickBorrar(){
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarLibro("http://"+IP_PUBLICA+":"+PUERTO+"/php/eliminar_libro.php?id="+ id_libro +"");
                eliminarLibroPrestado("http://"+IP_PUBLICA+":"+PUERTO+"/php/eliminar_libro_prestado_id.php?id="+id_libro+"");
            }
        });
    }

    @Override
    public void getLibrosDisponibles(Object object) {
        cargarDatosLibro(object);
    }

    @Override
    public void getUsuarioActivo(Object object) {
        cargarDatosAdministrador(object);
    }

    @Override
    public void onBackPressed() {

    }
}

