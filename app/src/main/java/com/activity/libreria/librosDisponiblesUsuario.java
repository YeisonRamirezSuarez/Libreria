package com.activity.libreria;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.adapter.AdapterUsuarioDisponiblesLibroItems;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.Usuario;

import java.util.ArrayList;

public class librosDisponiblesUsuario extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    //Los llamamos aqui asi:
    SearchView txtBuscar;
    RecyclerView reciclarVista;
    AdapterUsuarioDisponiblesLibroItems adapterUsuarioDisponiblesLibroItems;
    MetodosUsuario metodosUsuario;
    MetodosLibros metodosLibros;
    TextView nombre_usuario_txt, titulo;
    ImageView mas_funciones_usuario, volver;
    Button btnPrestar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libros_disponibles_usuario);
        Context context;
        findElement();
        traerRecyclerView();
        cargarDatosUsuarios();
    }

    private void findElement() {
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        txtBuscar = findViewById(R.id.txtBuscar);
        txtBuscar.setOnQueryTextListener(this);
        txtBuscar.setVisibility(View.VISIBLE);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        nombre_usuario_txt = findViewById(R.id.nombre_usuario_txt);
        reciclarVista = findViewById(R.id.reciclarVista);
        mas_funciones_usuario = findViewById(R.id.funcionesUser);
        mas_funciones_usuario.setVisibility(View.GONE);
        btnPrestar = findViewById(R.id.btnPrestar);
        titulo = findViewById(R.id.tituloBannerUser);
        titulo.setText("Libros Disponibles");
        volver = findViewById(R.id.volverLibros);
        volver.setVisibility(View.VISIBLE);
        volver.setOnClickListener(this);
    }

    private void traerRecyclerView() {
        //Aqui es donde nos muestra los libros 1 por 1 Disponibles
        ArrayList<Libros> listaLibros = metodosLibros.almacenarDatosEnArrays();
        adapterUsuarioDisponiblesLibroItems = new AdapterUsuarioDisponiblesLibroItems(this, listaLibros);
        reciclarVista.setAdapter(adapterUsuarioDisponiblesLibroItems);
        reciclarVista.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void cargarDatosUsuarios() {
        // Aqui es como se muestra el nombre del Usuario que ingreso
        ArrayList<Usuario> listaUsuario = metodosUsuario.almacenarDatosEnArraysUsuario();
        nombre_usuario_txt.setText(listaUsuario.get(0).getNombreUsuario());
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