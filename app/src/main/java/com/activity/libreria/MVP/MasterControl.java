package com.activity.libreria.MVP;

import static com.activity.libreria.modelos.Constantes.*;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.LibrosPrestadosAdmin;
import com.activity.libreria.MVP.Interfaces.CallbackLibro;
import com.activity.libreria.MVP.Interfaces.interfaces;
import com.activity.libreria.MVP.Presenter.Presenter;
import com.activity.libreria.R;
import com.activity.libreria.actividadAdministrador;
import com.activity.libreria.adapter.AdapterAdministradorLibroItems;
import com.activity.libreria.bd.Conexion;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.LibrosRsp;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaLibrosPrestados;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.UsuarioRsp;

public class MasterControl extends AppCompatActivity implements interfaces.View , PopupMenu.OnMenuItemClickListener, CallbackLibro, SearchView.OnQueryTextListener {

    interfaces.Presenter presenter;
    ListaUsuario listaUsuario;
    ListaLibros listaLibros;
    LibrosRsp librosRsp;
    AdapterAdministradorLibroItems adapterAdministradorLibroItems;
    String SCREEN = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter(this, this);
        showScreen(SCREEN_LOGIN, "", "");
        listaUsuario = new ListaUsuario();
        librosRsp = new LibrosRsp();



    }


    @Override
    public void showScreen(String screen, Object object, String typo) {
        switch (screen) {
            case SCREEN_LOGIN:
                verLogin();
                break;
            case SCREEN_CREAR_LIBRO:
                verCrearLibro();
                break;
            case SCREEN_PANTALLA_CARGA:
                verPantallaCarga(screen, object, typo);
                break;
        }
    }

    @Override
    public void verLogin() {
        setContentView(R.layout.login);
        UsuarioRsp usuarioRsp;
        SPreferences sPreferences;
        usuarioRsp = new UsuarioRsp();
        sPreferences = new SPreferences( this);
        EditText correo = findViewById(R.id.correoUsuario);
        EditText contraseña = findViewById(R.id.contraseñaUsuario);
        TextView textRegistrar = findViewById(R.id.textRegistrar);
        Button btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioRsp.setCorreo_Electronico(correo.getText().toString());
                usuarioRsp.setContrasena_Usuario(contraseña.getText().toString());
                if (usuarioRsp.getCorreo_Electronico().equals("") && usuarioRsp.getCorreo_Electronico().equals("")) {
                    Toast.makeText(getApplicationContext(), "ERROR: Campos vacios", Toast.LENGTH_LONG).show();
                } else {
                    sPreferences.setSharedPreference(usuarioRsp.getCorreo_Electronico());
                    presenter.consultarDatosLogin(usuarioRsp);
                }
            }
        });

    }

    @Override
    public void verUsuario() {
        setContentView(R.layout.actividad_usuario);
        SearchView txtBuscar = findViewById(R.id.txtBuscar);
        RecyclerView reciclarVista =  findViewById(R.id.reciclarVistaUsuario);
        TextView titulo = findViewById(R.id.tituloBannerUser);
        AdapterAdministradorLibroItems adapterAdministradorLibroItems;
        TextView nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        TextView rol = findViewById(R.id.rol);
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
        ImageView mas_informacion = findViewById(R.id.funcionesUser);
        Button btnPrestar = findViewById(R.id.btnPrestar);
        Conexion conexion;
        SPreferences sPreferences;
        ListaUsuario listaUsuario;
        ListaLibros listaLibros;
        ListaLibrosPrestados listaLibrosPrestados;
        txtBuscar = findViewById(R.id.txtBuscar);
        txtBuscar.setOnQueryTextListener(this);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        txtBuscar.setVisibility(View.VISIBLE);
        titulo.setText("Mis Libros Prestados");
        mas_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, SCREEN_USUARIO);
            }
        });


        conexion = new Conexion();
        listaUsuario = new ListaUsuario();
        listaLibrosPrestados = new ListaLibrosPrestados();


    }

    @Override
    public void verAdministrador(Object object) {
        setContentView(R.layout.actividad_administrador);
        SCREEN = SCREEN_ADMINISTRADOR;
        SearchView txtBuscar = findViewById(R.id.txtBuscar);
        RecyclerView reciclarVista = findViewById(R.id.reciclarVista);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        txtBuscar.setOnQueryTextListener(this);
        txtBuscar.setVisibility(View.VISIBLE);
        ImageView mas_informacion = findViewById(R.id.funcionesUser);
        TextView titulo = findViewById(R.id.tituloBanner);
        titulo.setText("Libros Disponibles");
        TextView nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        TextView rol = findViewById(R.id.rol);
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
        ListaLibros listaLibros;
        mas_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, SCREEN_ADMINISTRADOR);
            }
        });

        //Aqui es donde nos muestra los libros 1 por 1
        listaLibros = (ListaLibros) object;
        adapterAdministradorLibroItems = new AdapterAdministradorLibroItems(this, listaLibros.getLibros(), MasterControl.this::clickListener, SCREEN_ACTUALIZAR_LIBRO);
        reciclarVista.setAdapter(adapterAdministradorLibroItems);
        reciclarVista.setLayoutManager(new GridLayoutManager(this, 2));
        //showScreen(SCREEN_PANTALLA_CARGA, "", SCREEN_LIBROS_DISPONIBLES);
    }

    @Override
    public void verPantallaCarga(String screen, Object object, String typo) {
        setContentView(R.layout.splashscreen);
        ImageView imageView = findViewById(R.id.logo);
        presenter.pedirUsuario();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                accion(screen, object, typo);
            }
        }, 1500);
    }

    @Override
    public void verCrearLibro() {
        setContentView(R.layout.admin_agregar_libro);
        TextView nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        TextView rol = findViewById(R.id.rol);
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
        LibrosRsp librosRsp;
        librosRsp = new LibrosRsp();
        EditText nombreLibroInput = findViewById(R.id.nombreLibro_input);
        EditText autorLibroInput = findViewById(R.id.autorLibro_input);
        EditText cantidadLibroInput = findViewById(R.id.cantidadLibros_input);
        EditText urlLibroInput = findViewById(R.id.urlLibro_input);
        EditText urlImagenInput = findViewById(R.id.urlImagen_input);
        EditText descripcionInput = findViewById(R.id.descripcion_input);
        ImageView volverLibros = findViewById(R.id.volverLibros);
        volverLibros.setVisibility(View.VISIBLE);
        volverLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_DISPONIBLES, SCREEN_ADMINISTRADOR);
            }
        });
        ImageView funciones = findViewById(R.id.funcionesUser);
        funciones.setVisibility(View.GONE);
        Button guardar_boton = findViewById(R.id.guardar_boton);
        TextView titulo = findViewById(R.id.tituloBanner);
        titulo.setText("Agregar Libro");
        guardar_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nombreLibroInput.getText().toString().equals("") && !autorLibroInput.getText().toString().equals("")
                        && !cantidadLibroInput.getText().toString().equals("")
                        && !urlLibroInput.getText().toString().equals("")
                        && !urlImagenInput.getText().toString().equals("")
                        && !descripcionInput.getText().toString().equals("")) {
                    librosRsp.setTitulo_libro(nombreLibroInput.getText().toString());
                    librosRsp.setAutor_libro(autorLibroInput.getText().toString());
                    librosRsp.setCantidad_libro(cantidadLibroInput.getText().toString());
                    librosRsp.setUrl_libro(urlLibroInput.getText().toString());
                    librosRsp.setImagen_libro(urlImagenInput.getText().toString());
                    librosRsp.setDescripcion_libro(descripcionInput.getText().toString());
                    presenter.registrarLibro(librosRsp);
                } else {
                    Toast.makeText(getApplicationContext(), "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void verActualizarLibro(Object object) {
        setContentView(R.layout.admin_actualizar_libro);
        librosRsp = (LibrosRsp) object;
        TextView nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        TextView rol = findViewById(R.id.rol);
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
        EditText nombreLibroView = findViewById(R.id.nombreLibro_view);
        EditText autorLibroView = findViewById(R.id.autorLibro_view);
        EditText cantidadLibroView = findViewById(R.id.cantidadLibros_view);
        EditText urlLibroView = findViewById(R.id.urlLibro_view);
        EditText urlImagenView = findViewById(R.id.urlImagen_view);
        EditText descripcionView = findViewById(R.id.descripcion_view);
        ImageView volverView = findViewById(R.id.volverLibros);
        volverView.setVisibility(View.VISIBLE);
        ImageView funciones = findViewById(R.id.funcionesUser);
        ImageView borrar = findViewById(R.id.borrar);
        borrar.setVisibility(View.VISIBLE);
        funciones.setVisibility(View.GONE);
        volverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_DISPONIBLES, SCREEN_ADMINISTRADOR);
            }
        });
        Button actualizar_boton = findViewById(R.id.actualizar_boton);
        TextView titulo = findViewById(R.id.tituloBanner);
        titulo.setText("Actualizar Libros");
        int id = librosRsp.get_id();
        nombreLibroView.setText(librosRsp.getTitulo_libro());
        autorLibroView.setText(librosRsp.getAutor_libro());
        cantidadLibroView.setText(librosRsp.getCantidad_libro());
        urlLibroView.setText(librosRsp.getUrl_libro());
        urlImagenView.setText(librosRsp.getImagen_libro());
        descripcionView.setText(librosRsp.getDescripcion_libro());
        actualizar_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                librosRsp = new LibrosRsp();
                librosRsp.set_id(id);
                librosRsp.setTitulo_libro(nombreLibroView.getText().toString().trim());
                librosRsp.setAutor_libro(autorLibroView.getText().toString().trim());
                librosRsp.setCantidad_libro(cantidadLibroView.getText().toString().trim());
                librosRsp.setUrl_libro(urlLibroView.getText().toString().trim());
                librosRsp.setImagen_libro(urlImagenView.getText().toString().trim());
                librosRsp.setDescripcion_libro(descripcionView.getText().toString().trim());
                if (!nombreLibroView.getText().toString().equals("") && !autorLibroView.getText().toString().equals("")
                        && !cantidadLibroView.getText().toString().equals("")
                        && !urlLibroView.getText().toString().equals("")
                        && !urlImagenView.getText().toString().equals("")
                        && !descripcionView.getText().toString().equals("")) {
                    presenter.actualizarLibro(librosRsp, SCREEN_ACTUALIZAR_LIBRO);
                    presenter.actualizarLibro(librosRsp, SCREEN_ACTUALIZAR_LIBRO_PRESTADO);
                } else {
                    Toast.makeText(getApplicationContext(), "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.borrarLibro(librosRsp, SCREEN_ELIMINAR_LIBRO);
                presenter.borrarLibro(librosRsp, SCREEN_ELIMINAR_LIBRO_PRESTADO);
            }
        });
    }

    @Override
    public void mostrarUsuario(Object object) {
        // Aqui es como se muestra el nombre del Usuario que ingreso
        listaUsuario= (ListaUsuario) object;
    }

    private void accion (String screen, Object object, String typo) {
        switch (String.valueOf(object)) {
            case SCREEN_LIBROS_DISPONIBLES:
                presenter.pedirLibros(typo);
                break;
        }
    }

    @Override
    public void respuestas(String respuesta, String screen) {
        switch (screen){
            case SCREEN_LOGIN:
                if (!respuesta.equals("Fail")) {
                    if (respuesta.equals("usuario")) {
                        showScreen(SCREEN_PANTALLA_CARGA, "", SCREEN_USUARIO);
                    } else if (respuesta.equals("administrador")) {
                        showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_DISPONIBLES, SCREEN_ADMINISTRADOR);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
                break;
            case SCREEN_ELIMINAR_LIBRO_PRESTADO:
            case SCREEN_CREAR_LIBRO:
                Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_DISPONIBLES, SCREEN_ADMINISTRADOR);
                break;
            case SCREEN_ACTUALIZAR_LIBRO:
                if(respuesta.equals("correcto"))
                {
                    showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_DISPONIBLES, SCREEN_ADMINISTRADOR);
                }else if(respuesta.equals("Fallo")){
                    Toast.makeText(getApplicationContext(),
                            "Fallo la actualizacion correctamente",Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    @Override
    public void libros(Object object, String screen) {
        switch (screen){
            case SCREEN_USUARIO:
                verUsuario();
                break;
            case SCREEN_ADMINISTRADOR:
                verAdministrador(object);
                break;
        }
    }

    public void showPopup(View view, String showScreen){
        switch (showScreen){
            case SCREEN_USUARIO:
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.inflate(R.menu.mas_usuario);
                popupMenu.show();
                break;
            case SCREEN_ADMINISTRADOR:
                PopupMenu popupMenu1 = new PopupMenu(this, view);
                popupMenu1.setOnMenuItemClickListener(this);
                popupMenu1.inflate(R.menu.mas_admin);
                popupMenu1.show();
                break;
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.prestadosLibros:
                Toast.makeText(this, "Libros Prestados", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LibrosPrestadosAdmin.class);
                startActivity(intent);
                return true;
            case R.id.agregarLibros:
                showScreen(SCREEN_CREAR_LIBRO, "", "");
               /* Toast.makeText(this, "Agregar Libros", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, AgregarLibros.class);
                startActivity(intent1);*/
                return true;
            case R.id.salirLogin:
                showScreen(SCREEN_LOGIN, "", "");
              /*  Toast.makeText(this, "Cerrar Sesion", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, Login.class);
                startActivity(intent2);*/
                return true;
            default:
                return false;
        }
    }

    @Override
    public void clickListener(Object object, String screen) {
        //if (screen.equals(SCREEN_LIBROS_DISPONIBLES)){
            LibrosRsp librosRsp = (LibrosRsp) object;
            switch (screen){
                case SCREEN_ACTUALIZAR_LIBRO:
                    verActualizarLibro(object);
                    break;
            }
       // }

    }

    @Override
    public boolean onQueryTextSubmit(String s) {

                    adapterAdministradorLibroItems.filtrado(s);


        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
