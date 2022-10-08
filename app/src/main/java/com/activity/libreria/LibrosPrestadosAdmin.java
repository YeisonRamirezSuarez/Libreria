package com.activity.libreria;


import static com.activity.libreria.bd.NetwordHelper.IP_PUBLICA;
import static com.activity.libreria.bd.NetwordHelper.PUERTO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.Interfaces.Callback;
import com.activity.libreria.adapter.AdapterAdministradorLibroPrestadoItems;
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

import java.util.ArrayList;

public class LibrosPrestadosAdmin extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener , Callback {

    //Los llamamos aqui asi:
    SearchView txtBuscar;
    RecyclerView reciclarVista;
    AdapterAdministradorLibroPrestadoItems adapterAdministradorLibroPrestadoItems;
    MetodosUsuario metodosUsuario;
    MetodosAdministrador metodosAdministrador;
    MetodosLibros metodosLibros;
    TextView nombre_administrador_txt, titulo, rol;
    ImageView mas_funciones_usuario, volver;
    ListaUsuario listaUsuario;
    ListaLibrosPrestados listaLibrosPrestados;
    ListaLibros listaLibros;
    Conexion conexion;
    SPreferences sPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_libros_prestados);
        findElement();

    }


    private void findElement() {
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        metodosAdministrador = new MetodosAdministrador(this);
        nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        rol = findViewById(R.id.rol);
        txtBuscar = findViewById(R.id.txtBuscar);
        txtBuscar.setOnQueryTextListener(this);
        txtBuscar.setVisibility(View.VISIBLE);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        reciclarVista = findViewById(R.id.reciclarVista);
        mas_funciones_usuario = findViewById(R.id.funcionesUser);
        mas_funciones_usuario.setVisibility(View.GONE);
        volver = findViewById(R.id.volverLibros);
        volver.setVisibility(View.VISIBLE);
        volver.setOnClickListener(this);
        titulo = findViewById(R.id.tituloBanner);
        titulo.setText("Libros Prestados");
        conexion = new Conexion();
        sPreferences = new SPreferences(this);
        listaUsuario = new ListaUsuario();
        listaLibros = new ListaLibros();
        listaLibrosPrestados = new ListaLibrosPrestados();
        conexion.buscarUsuarios("https://"+IP_PUBLICA+"/consulta_usuario.php?correo="+sPreferences.getSharedPreference()+"", this, this);
        conexion.consultaLibrosPrestados("https://"+IP_PUBLICA+"/libros_prestados_disponibles_por_id.php", this, this);
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

    private void traerRecyclerView(Object object) {
        //Aqui es donde nos muestra los libros 1 por 1 Disponibles
        listaLibrosPrestados = (ListaLibrosPrestados) object;
       // adapterAdministradorLibroPrestadoItems = new AdapterAdministradorLibroPrestadoItems(LibrosPrestadosAdmin.this, listaLibrosPrestados.getLibros());
        reciclarVista.setAdapter(adapterAdministradorLibroPrestadoItems);
        reciclarVista.setLayoutManager(new GridLayoutManager(LibrosPrestadosAdmin.this, 2));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.volverLibros:
                Intent intent = new Intent(LibrosPrestadosAdmin.this, actividadAdministrador.class);
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
        adapterAdministradorLibroPrestadoItems.filtrado(s);
        return false;
    }


    @Override
    public void getLibrosDisponibles(Object object) {
        traerRecyclerView(object);
    }

    @Override
    public void getUsuarioActivo(Object object) {
        cargarDatosAdministrador(object);
    }

    @Override
    public void onBackPressed() {

    }
}