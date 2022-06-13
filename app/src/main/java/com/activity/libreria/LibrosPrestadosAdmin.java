package com.activity.libreria;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.adapter.AdapterAdministradorLibroPrestadoItems;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.modelos.Administrador;
import com.activity.libreria.modelos.Libros;

import java.util.ArrayList;

public class LibrosPrestadosAdmin extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    //Los llamamos aqui asi:
    SearchView txtBuscar;
    RecyclerView reciclarVista;
    AdapterAdministradorLibroPrestadoItems adapterAdministradorLibroPrestadoItems;
    MetodosUsuario metodosUsuario;
    MetodosAdministrador metodosAdministrador;
    MetodosLibros metodosLibros;
    TextView nombre_administrador_txt, titulo;
    ImageView mas_funciones_usuario, volver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_libros_prestados);

        findElement();
        traerRecyclerView();
        cargarDatosAdministrador();
    }

    private void cargarDatosAdministrador() {
        ArrayList<Administrador> listaAdministrador  = metodosAdministrador.selectAdministrador();
        nombre_administrador_txt.setText(listaAdministrador.get(0).getNombreAdministrador());
    }

    private void traerRecyclerView() {
        //Aqui es donde nos muestra los libros 1 por 1 Disponibles
        ArrayList<Libros> listaLibros = metodosLibros.ArraysLibrosPrestadosAdmin();
        adapterAdministradorLibroPrestadoItems = new AdapterAdministradorLibroPrestadoItems(LibrosPrestadosAdmin.this, listaLibros);
        reciclarVista.setAdapter(adapterAdministradorLibroPrestadoItems);
        reciclarVista.setLayoutManager(new GridLayoutManager(LibrosPrestadosAdmin.this, 2));
    }

    private void findElement() {
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        metodosAdministrador = new MetodosAdministrador(this);
        nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
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


}