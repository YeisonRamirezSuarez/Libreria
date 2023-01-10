package com.activity.libreria.MVP;

import static com.activity.libreria.bd.NetwordHelper.IP_PUBLICA;
import static com.activity.libreria.modelos.Constantes.*;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.LibrosPrestadosAdmin;
import com.activity.libreria.Login;
import com.activity.libreria.MVP.Interfaces.CallbackLibro;
import com.activity.libreria.MVP.Interfaces.interfaces;
import com.activity.libreria.MVP.Presenter.Presenter;
import com.activity.libreria.R;
import com.activity.libreria.Registrar;
import com.activity.libreria.actividadAdministrador;
import com.activity.libreria.actividadUsuario;
import com.activity.libreria.adapter.AdapterAdministradorHistorialLibroPrestadoItems;
import com.activity.libreria.adapter.AdapterAdministradorLibroItems;
import com.activity.libreria.adapter.AdapterAdministradorLibroPrestadoItems;
import com.activity.libreria.adapter.AdapterUsuarioDisponiblesLibroItems;
import com.activity.libreria.adapter.AdapterUsuarioLibroItems;
import com.activity.libreria.bd.Conexion;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.LibrosPrestadosRsp;
import com.activity.libreria.modelos.LibrosRsp;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaLibrosPrestados;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.Usuario;
import com.activity.libreria.modelos.UsuarioRsp;
import com.bumptech.glide.Glide;

import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifImageView;

public class MasterControl extends AppCompatActivity implements interfaces.View , PopupMenu.OnMenuItemClickListener, CallbackLibro, SearchView.OnQueryTextListener {

    interfaces.Presenter presenter;
    ListaUsuario listaUsuario;
    ListaLibros listaLibros;
    ListaLibrosPrestados listaLibrosPrestados;
    LibrosPrestadosRsp librosPrestadosRsp;
    LibrosRsp librosRsp;
    UsuarioRsp usuarioRsp;
    AdapterAdministradorLibroItems adapterAdministradorLibroItems;
    AdapterAdministradorLibroPrestadoItems adapterAdministradorLibroPrestadoItems;
    AdapterAdministradorHistorialLibroPrestadoItems adapterAdministradorHistorialLibroPrestadoItems;
    AdapterUsuarioDisponiblesLibroItems adapterUsuarioDisponiblesLibroItems;
    AdapterUsuarioLibroItems adapterUsuarioLibroItems;
    String SCREEN = "";
    boolean filtro = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter(this, this);
        showScreen(SCREEN_LOGIN, "", "");
        listaUsuario = new ListaUsuario();
        librosRsp = new LibrosRsp();
        usuarioRsp = new UsuarioRsp();
        librosPrestadosRsp = new LibrosPrestadosRsp();
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

    @Override
    public void showScreen(String screen, Object object, String typo) {
        switch (screen) {
            case SCREEN_LOGIN:
                verLogin();
                break;
            case SCREEN_REGISTRAR:
                verRegistrar();
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

        textRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScreen(SCREEN_REGISTRAR, "", "");
            }
        });

    }

    @Override
    public void verRegistrar() {
        setContentView(R.layout.registrarse);
        MetodosUsuario metodosUsuario = new MetodosUsuario(this);
        EditText correoUsuario = findViewById(R.id.registrarCorreo);
        EditText contraseñaUsuario = findViewById(R.id.registrarContraseña);
        EditText nombreUsuario = findViewById(R.id.registrarNombre);
        EditText telefonoUsuario = findViewById(R.id.registrarTelefono);
        EditText dirreccionUsuario = findViewById(R.id.registrarDireccion);
        Button botonRegistrar = findViewById(R.id.btnRegRegistrar);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //cetiando
                    String correo = correoUsuario.getText().toString();
                    String contraseña = contraseñaUsuario.getText().toString();
                    String nombre = nombreUsuario.getText().toString();
                    String telefono = telefonoUsuario.getText().toString();
                    String direccion = dirreccionUsuario.getText().toString();

                    usuarioRsp.setCorreo_Electronico(correo);
                    usuarioRsp.setContrasena_Usuario(contraseña);
                    usuarioRsp.setNombre_Usuario(nombre);
                    usuarioRsp.setTelefono_Usuario(telefono);
                    usuarioRsp.setDireccion_Usuario(direccion);

                    //Validaciones
                    if (usuarioRsp.getCorreo_Electronico().isEmpty() || usuarioRsp.getContrasena_Usuario().isEmpty() || usuarioRsp.getNombre_Usuario().isEmpty() || usuarioRsp.getTelefono_Usuario().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Error: Campos vacios", Toast.LENGTH_LONG).show(); //Mostrar error de campos vacios
                    } else {
                        if (!metodosUsuario.contieneSoloLetras(nombre)){
                            Toast.makeText(getApplicationContext(), "Error: El nombre no debe contener numeros", Toast.LENGTH_LONG).show(); //Mostrar error de nombre que no lleve numeros
                        } else {
                            if (!metodosUsuario.validarTelefono(telefono)){
                                Toast.makeText(getApplicationContext(), "Error: El numero de telefono debe tener 10 digitos", Toast.LENGTH_LONG).show(); //Mostrar error de telefono debe empezar por 57
                            } else {
                                Pattern pattern = Patterns.EMAIL_ADDRESS;
                                if (!pattern.matcher(correo).matches()){
                                    Toast.makeText(getApplicationContext(), "Error: Ingrese un Email Valido", Toast.LENGTH_LONG).show(); //Mostrando correo invalido
                                } else {
                                    if (contraseña.length() < 6){
                                        Toast.makeText(getApplicationContext(), "Error: Ingrese una contraseña minimo 6 digitos", Toast.LENGTH_LONG).show(); //Mostrar error de contraseña
                                    } else {
                                        presenter.registrarUsuario(usuarioRsp);
                                       /* if (validar()){
                                            Intent intent = new Intent(Registrar.this, Login.class);
                                            startActivity(intent);
                                        }*/
                                    }
                                }
                            }
                        }
                    }
                }

        });
        TextView textRegRegistrar = findViewById(R.id.textRegRegistrar);
        textRegRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScreen(SCREEN_LOGIN, "", "");
            }
        });
    }

    @Override
    public void verUsuario(Object object) {
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
        txtBuscar = findViewById(R.id.txtBuscar);
        txtBuscar.setOnQueryTextListener(this);
        filtro = true;
        SCREEN = SCREEN_USUARIO;
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        txtBuscar.setVisibility(View.VISIBLE);
        titulo.setText("Mis Libros Prestados");
        mas_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, SCREEN_USUARIO);
            }
        });
        listaLibrosPrestados = (ListaLibrosPrestados) object;
        adapterUsuarioLibroItems = new AdapterUsuarioLibroItems(this, listaLibrosPrestados.getLibros(), MasterControl.this::clickListener, SCREEN_VER_LIBRO);
        reciclarVista.setAdapter(adapterUsuarioLibroItems);
        reciclarVista.setLayoutManager(new LinearLayoutManager(this));
        btnPrestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pedirLibros(SCREEN_LIBROS_DISPONIBLES);
            }
        });

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
        GifImageView imageView = findViewById(R.id.logo);
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
    public void verLibrosPrestadosAdmin(Object object) {
        setContentView(R.layout.admin_libros_prestados);
        TextView nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        TextView rol = findViewById(R.id.rol);
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
        SearchView txtBuscar = findViewById(R.id.txtBuscar);
        txtBuscar.setOnQueryTextListener(this);
        SCREEN = SCREEN_LIBROS_PRESTADOS_ADMIN;
        txtBuscar.setVisibility(View.VISIBLE);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        RecyclerView reciclarVista = findViewById(R.id.reciclarVista);
        ImageView mas_funciones_usuario = findViewById(R.id.funcionesUser);
        mas_funciones_usuario.setVisibility(View.GONE);
        ImageView volver = findViewById(R.id.volverLibros);
        volver.setVisibility(View.VISIBLE);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_DISPONIBLES, SCREEN_ADMINISTRADOR);
            }
        });
        TextView titulo = findViewById(R.id.tituloBanner);
        titulo.setText("Libros Prestados");
        listaLibrosPrestados = (ListaLibrosPrestados) object;
        adapterAdministradorLibroPrestadoItems = new AdapterAdministradorLibroPrestadoItems(this, listaLibrosPrestados.getLibros(), MasterControl.this::clickListener, SCREEN_LIBROS_PRESTADOS_ADMIN);
        reciclarVista.setAdapter(adapterAdministradorLibroPrestadoItems);
        reciclarVista.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void verHistorialLibrosPrestados(Object object, Object object2) {
        setContentView(R.layout.admin_historial_libros);
        ImageView imageView_txt = findViewById(R.id.imageView_txt);
        TextView nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        TextView rol = findViewById(R.id.rol);
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
        TextView nombreLibroHistorial_ver = findViewById(R.id.nombreLibroHistorial_ver);
        RecyclerView reciclarVistaHistorial = findViewById(R.id.reciclarVistaHistorial);
        TextView autorLibroHistorial_ver = findViewById(R.id.autorLibroHistorial_ver);
        TextView descripcionLibroHistorial_ver = findViewById(R.id.descripcionLibroHistorial_ver);
        ImageView volverLibros = findViewById(R.id.volverLibros);
        volverLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_PRESTADOS_ADMIN, SCREEN_LIBROS_PRESTADOS_ADMIN);
            }
        });
        volverLibros.setVisibility(View.VISIBLE);
        TextView titulo = findViewById(R.id.tituloBanner);
        titulo.setText("MiLibro");
        ImageView funciones = findViewById(R.id.funcionesUser);
        funciones.setVisibility(View.GONE);
        librosPrestadosRsp = (LibrosPrestadosRsp) object;
        if (librosPrestadosRsp != null) {
            nombreLibroHistorial_ver.setText(librosPrestadosRsp.getTitulo_libro_Prestado());
            autorLibroHistorial_ver.setText(librosPrestadosRsp.getAutor_libro_Prestado());
            descripcionLibroHistorial_ver.setText(librosPrestadosRsp.getDescripcion_libro_Prestado());
            Glide.with(this)
                    .load(librosPrestadosRsp.getImagen_libro_Prestado())
                    .error(R.drawable.error)
                    .into(imageView_txt);
        }

        listaLibrosPrestados= (ListaLibrosPrestados) object2;
        adapterAdministradorHistorialLibroPrestadoItems = new AdapterAdministradorHistorialLibroPrestadoItems(this, listaLibrosPrestados.getLibros());
        reciclarVistaHistorial.setAdapter(adapterAdministradorHistorialLibroPrestadoItems);
        reciclarVistaHistorial.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void verLibrosDisponiblesUsuario(Object object) {
        setContentView(R.layout.libros_disponibles_usuario);
        SearchView txtBuscar = findViewById(R.id.txtBuscar);
        SCREEN = SCREEN_LIBROS_DISPONIBLES;
        txtBuscar.setOnQueryTextListener(this);
        txtBuscar.setVisibility(View.VISIBLE);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        TextView nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        TextView rol = findViewById(R.id.rol);
        RecyclerView reciclarVista = findViewById(R.id.reciclarVista);
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
        ImageView mas_funciones_usuario = findViewById(R.id.funcionesUser);
        mas_funciones_usuario.setVisibility(View.GONE);
        Button btnPrestar = findViewById(R.id.btnPrestar);
        TextView titulo = findViewById(R.id.tituloBannerUser);
        titulo.setText("Libros Disponibles");
        ImageView volver = findViewById(R.id.volverLibros);
        volver.setVisibility(View.VISIBLE);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_PRESTADOS, SCREEN_USUARIO);
            }
        });
        ListaLibros lista = (ListaLibros) object;
        adapterUsuarioDisponiblesLibroItems = new AdapterUsuarioDisponiblesLibroItems(this, lista.getLibros(), MasterControl.this::clickListener, SCREEN_LIBROS_DISPONIBLES);
        reciclarVista.setAdapter(adapterUsuarioDisponiblesLibroItems);
        reciclarVista.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void verPrestarLibro(Object object) {
        setContentView(R.layout.prestar_libro_usuario);
        librosRsp = (LibrosRsp) object;
        TextView nombreLibro_ver = findViewById(R.id.nombreLibro_ver);
        TextView nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        TextView rol = findViewById(R.id.rol);
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
        TextView autorLibro_ver = findViewById(R.id.autorLibro_ver);
        TextView descripcionLibro_ver = findViewById(R.id.descripcionLibro_ver);
        TextView nombreUser = findViewById(R.id.nombre_usuario_historial_txt);
        TextView telefonoUser = findViewById(R.id.telefono_historial_txt);
        ImageView imageView_txt = findViewById(R.id.imageView_txt);
        Button prestar_libro = findViewById(R.id.prestar_libro);
        prestar_libro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.consultarLibroPrestado(SCREEN_CONSULTA_PRESTAR);
                //presenter.prestarLibro(listaUsuario, librosRsp, SCREEN_PRESTAR_LIBRO);
            }
        });
        ImageView volverLibros = findViewById(R.id.volverLibros);
        volverLibros.setVisibility(View.VISIBLE);
        volverLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.pedirLibros(SCREEN_LIBROS_DISPONIBLES);
            }
        });
        ImageView funciones = findViewById(R.id.funcionesUser);
        funciones.setVisibility(View.GONE);
        TextView titulo = findViewById(R.id.tituloBannerUser);
        titulo.setText("Prestar Libro");

        if (librosRsp != null) {
            nombreLibro_ver.setText(librosRsp.getTitulo_libro());
            autorLibro_ver.setText(librosRsp.getAutor_libro());
            descripcionLibro_ver.setText(librosRsp.getDescripcion_libro());
            Glide.with(this)
                    .load(librosRsp.getImagen_libro())
                    .error(R.drawable.error)
                    .into(imageView_txt);
        }
    }

    @Override
    public void verLeerLibro(Object object) {
        setContentView(R.layout.vista_usuario_libro);
        librosPrestadosRsp = (LibrosPrestadosRsp) object;
        TextView nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        TextView rol = findViewById(R.id.rol);
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_administrador_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
        TextView nombreLibro_ver = findViewById(R.id.nombreLibro_ver);
        TextView autorLibro_ver = findViewById(R.id.autorLibro_ver);
        TextView descripcionLibro_ver = findViewById(R.id.descripcionLibro_ver);
        ImageView imageView_txt = findViewById(R.id.imageView_txt);
        ImageView volverLibros = findViewById(R.id.volverLibros);
        volverLibros.setVisibility(View.VISIBLE);
        volverLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_PRESTADOS, SCREEN_USUARIO);
            }
        });
        Button devolver_boton = findViewById(R.id.devolver_boton);
        devolver_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.borrarLibroPrestado(librosPrestadosRsp, SCREEN_ELIMINAR_LIBRO_PRESTADO_USUARIO);
            }
        });
        Button ver_libro_boton = findViewById(R.id.ver_libro_boton);
        ver_libro_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    String url = librosPrestadosRsp.getUrl_libro_Prestado();
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "URL NO EXISTENTE...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView funciones = findViewById(R.id.funcionesUser);
        funciones.setVisibility(View.GONE);
        TextView titulo = findViewById(R.id.tituloBannerUser);
        titulo.setText("MiLibro");
        if (listaLibrosPrestados != null) {
            nombreLibro_ver.setText(librosPrestadosRsp.getTitulo_libro_Prestado());
            autorLibro_ver.setText(librosPrestadosRsp.getAutor_libro_Prestado());
            descripcionLibro_ver.setText(librosPrestadosRsp.getDescripcion_libro_Prestado());
            Glide.with(this)
                    .load(librosPrestadosRsp.getImagen_libro_Prestado())
                    .error(R.drawable.error)
                    .into(imageView_txt);
        }
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
            case SCREEN_LIBROS_PRESTADOS_ADMIN:
                presenter.pedirLibrosPrestados(typo);
                break;
            case SCREEN_LIBROS_PRESTADOS:
                presenter.pedirLibrosPrestadosUsuario(typo);
                break;
        }
    }

    @Override
    public void respuestas(String respuesta, String screen) {
        switch (screen){
            case SCREEN_LOGIN:
                if (!respuesta.equals("\tFail")) {
                    if (respuesta.equals("\tusuario")) {
                        showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_PRESTADOS, SCREEN_USUARIO);
                    } else if (respuesta.equals("\tadministrador")) {
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
                if(respuesta.equals("\tcorrecto")) {
                    Toast.makeText(this, "Libro actualizado corrctamente", Toast.LENGTH_SHORT).show();
                    showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_DISPONIBLES, SCREEN_ADMINISTRADOR);
                }else if(respuesta.equals("\tFallo")){
                    Toast.makeText(getApplicationContext(),
                            "Fallo la actualizacion correctamente",Toast.LENGTH_LONG).show();
                }
                break;
            case SCREEN_PRESTAR_LIBRO:
            case SCREEN_ELIMINAR_LIBRO_PRESTADO_USUARIO:
                Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_PRESTADOS, SCREEN_USUARIO);
                break;
            case SCREEN_REGISTRAR:
                Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                showScreen(SCREEN_LOGIN, "", "");
                break;
        }

    }

    @Override
    public void libros(Object object, Object object2, String screen) {
        switch (screen){
            case SCREEN_USUARIO:
                verUsuario(object);
                break;
            case SCREEN_ADMINISTRADOR:
                verAdministrador(object);
                break;
            case SCREEN_LIBROS_PRESTADOS_ADMIN:
                verLibrosPrestadosAdmin(object);
                break;
            case SCREEN_LIBROS_PRESTADOS_HISTORIAL:
                verHistorialLibrosPrestados(object, object2);
                break;
            case SCREEN_LIBROS_DISPONIBLES:
                verLibrosDisponiblesUsuario(object);
                break;
            case SCREEN_CONSULTA_PRESTAR:
                boolean siExiste = false;
                listaLibrosPrestados = (ListaLibrosPrestados) object;
                for (int j = 0; j < listaLibrosPrestados.getLibros().size(); j++) {
                    if (listaLibrosPrestados.getLibros().get(j).getCorreo_Prestamo_libro().equals(object2) && listaLibrosPrestados.getLibros().get(j).get_id_Libro() == librosRsp.get_id()) {
                        siExiste = true;
                    }
                }
                if (siExiste) {
                    Toast.makeText(this, "Libro ya esta prestado", Toast.LENGTH_SHORT).show();
                }
                else {
                    presenter.prestarLibro(listaUsuario, librosRsp, SCREEN_PRESTAR_LIBRO);
                }
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
                showScreen(SCREEN_PANTALLA_CARGA, SCREEN_LIBROS_PRESTADOS_ADMIN, SCREEN_LIBROS_PRESTADOS_ADMIN);
                return true;
            case R.id.agregarLibros:
                showScreen(SCREEN_CREAR_LIBRO, "", "");
                return true;
            case R.id.salirLogin:
                showScreen(SCREEN_LOGIN, "", "");
                return true;
            default:
                return false;
        }
    }

    @Override
    public void clickListener(Object object, String screen) {
            switch (screen){
                case SCREEN_ACTUALIZAR_LIBRO:
                    verActualizarLibro(object);
                    break;
                case SCREEN_LIBROS_PRESTADOS_ADMIN:
                    presenter.pedirLibrosPrestadosId(object, SCREEN_LIBROS_PRESTADOS_HISTORIAL);
                    break;
                case SCREEN_LIBROS_DISPONIBLES:
                    verPrestarLibro(object);
                    break;
                case SCREEN_VER_LIBRO:
                    verLeerLibro(object);
                    break;
            }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        switch (SCREEN){
            case SCREEN_USUARIO:
                adapterUsuarioLibroItems.filtrado(s);
                break;
            case SCREEN_ADMINISTRADOR:
                adapterAdministradorLibroItems.filtrado(s);
                break;
            case SCREEN_LIBROS_PRESTADOS_ADMIN:
                adapterAdministradorLibroPrestadoItems.filtrado(s);
                break;
            case SCREEN_LIBROS_DISPONIBLES:
                adapterUsuarioDisponiblesLibroItems.filtrado(s);
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
    }
}
