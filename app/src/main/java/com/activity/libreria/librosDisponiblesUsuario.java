package com.activity.libreria;


import static com.activity.libreria.bd.NetwordHelper.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.Interfaces.Callback;
import com.activity.libreria.Interfaces.Logica;
import com.activity.libreria.adapter.AdapterUsuarioDisponiblesLibroItems;
import com.activity.libreria.bd.Conexion;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.LibrosRsp;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.Usuario;
import com.activity.libreria.modelos.UsuarioRsp;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class librosDisponiblesUsuario extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener, Callback {

    //Los llamamos aqui asi:
    SearchView txtBuscar;
    RecyclerView reciclarVista;
    AdapterUsuarioDisponiblesLibroItems adapterUsuarioDisponiblesLibroItems;
    MetodosUsuario metodosUsuario;
    MetodosLibros metodosLibros;
    TextView rol,nombre_usuario_txt, titulo;
    ImageView mas_funciones_usuario, volver;
    Button btnPrestar;
    ListaLibros listaLibros;
    ListaUsuario listaUsuario;
    SPreferences sPreferences;
    Usuario usuario;
    Conexion conexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libros_disponibles_usuario);
        Context context;
        findElement();

    }

    private void findElement() {
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        usuario = new Usuario();
        sPreferences = new SPreferences(this);
        listaLibros = new ListaLibros();
        listaUsuario = new ListaUsuario();
        txtBuscar = findViewById(R.id.txtBuscar);
        txtBuscar.setOnQueryTextListener(this);
        txtBuscar.setVisibility(View.VISIBLE);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        nombre_usuario_txt = findViewById(R.id.nombre_usuario_txt);
        rol = findViewById(R.id.rol);
        reciclarVista = findViewById(R.id.reciclarVista);
        mas_funciones_usuario = findViewById(R.id.funcionesUser);
        mas_funciones_usuario.setVisibility(View.GONE);
        btnPrestar = findViewById(R.id.btnPrestar);
        titulo = findViewById(R.id.tituloBannerUser);
        titulo.setText("Libros Disponibles");
        volver = findViewById(R.id.volverLibros);
        volver.setVisibility(View.VISIBLE);
        volver.setOnClickListener(this);
        conexion = new Conexion();
        conexion.consultaLibros("http://"+IP_PUBLICA+":"+PUERTO+"/php/libros_disponibles.php", this, this);
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


    private void traerRecyclerView(Object object) {
        //Aqui es donde nos muestra los libros 1 por 1 Disponibles
        //consultaLibros("http://192.168.1.11:80/php/libros_disponibles.php");
        //getData("http://192.168.1.11:80/php/libros_disponibles.php");
        ListaLibros lista = (ListaLibros) object;
        //ArrayList<LibrosRsp> lista = (ListaLibros) object.getClass().get;
        //ArrayList<Libros> listaLibros = metodosLibros.almacenarDatosEnArrays();
        adapterUsuarioDisponiblesLibroItems = new AdapterUsuarioDisponiblesLibroItems(this, lista.getLibros());
        reciclarVista.setAdapter(adapterUsuarioDisponiblesLibroItems);
        reciclarVista.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void cargarDatosUsuarios(Object object) {
        // Aqui es como se muestra el nombre del Usuario que ingreso

        ListaUsuario lista = (ListaUsuario) object;
        rol.setText(lista.getUsuarios().get(0).getRol_Usuario());
        nombre_usuario_txt.setText(lista.getUsuarios().get(0).getNombre_Usuario());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.volverLibros:
                Intent intent = new Intent(librosDisponiblesUsuario.this, actividadUsuario.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapterUsuarioDisponiblesLibroItems.filtrado(s);
        return false;
    }


    @Override
    public void getLibrosDisponibles(Object object) {
        traerRecyclerView(object);
    }

    @Override
    public void getUsuarioActivo(Object object) {
        cargarDatosUsuarios(object);
    }

    @Override
    public void onBackPressed() {

    }
    //Tambien se puede usar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.game_menu, menu);
//        return true;
//    }
    //Para inflar en la vista seria
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.new_game:
//                newGame();
//                return true;
//            case R.id.help:
//                showHelp();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    //Aqui es donde esta el Boton flotante


    //para buscador
}