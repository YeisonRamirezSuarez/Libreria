package com.activity.libreria;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.adapter.AdapterAdministradorLibroItems;
import com.activity.libreria.adapter.AdapterUsuarioLibroItems;
import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.Usuario;

import java.util.ArrayList;

public class actividadUsuario extends AppCompatActivity implements View.OnClickListener,MenuItem.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener, SearchView.OnQueryTextListener {

    //Los llamamos aqui asi:
    SearchView txtBuscar;
    RecyclerView reciclarVistaUsuario;
    TextView titulo;
    BDHelper BDLibro;
    AdapterUsuarioLibroItems adapterUsuarioLibroItems;
    AdapterAdministradorLibroItems adapterAdministradorLibroItems;
    MetodosUsuario metodosUsuario;
    MetodosLibros metodosLibros;
    TextView nombre_usuario_txt;
    ImageView mas_informacion;
    Button btnPrestar;
    SPreferences sPreferences;
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_usuario);
        Context context;
        findElement();
        traerRecyclerView();
        //cargarDatosUsuarios();
    }

    private void traerRecyclerView() {
        //Aqui es donde nos muestra los libros 1 por 1 Disponibles
        ArrayList<Libros> listaLibros = metodosLibros.ArraysLibrosPrestados();
        adapterUsuarioLibroItems = new AdapterUsuarioLibroItems(actividadUsuario.this, listaLibros);
        reciclarVistaUsuario.setAdapter(adapterUsuarioLibroItems);
        reciclarVistaUsuario.setLayoutManager(new LinearLayoutManager(actividadUsuario.this));
    }

    private void findElement() {
        sPreferences = new SPreferences(this);
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        txtBuscar = findViewById(R.id.txtBuscar);
        txtBuscar.setOnQueryTextListener(actividadUsuario.this);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        txtBuscar.setVisibility(View.VISIBLE);
        nombre_usuario_txt = findViewById(R.id.nombre_usuario_txt);
        reciclarVistaUsuario = findViewById(R.id.reciclarVistaUsuario);
        mas_informacion = findViewById(R.id.funcionesUser);
        mas_informacion.setOnClickListener(this);
        btnPrestar = findViewById(R.id.btnPrestar);
        btnPrestar.setOnClickListener(this);
        titulo = findViewById(R.id.tituloBannerUser);
        titulo.setText("Mis Libros Prestados");
    }

    private void cargarDatosUsuarios() {
        // Aqui es como se muestra el nombre del Usuario que ingreso
        ArrayList<Usuario> listaUsuario = metodosUsuario.almacenarDatosEnArraysUsuario();
        nombre_usuario_txt.setText(listaUsuario.get(0).getNombreUsuario());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPrestar:
                Intent intent = new Intent(actividadUsuario.this, librosDisponiblesUsuario.class);
                startActivity(intent);
                break;
            case R.id.funcionesUser:
                showPopup(view);
                break;
        }

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

    public void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.mas_usuario);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salirLogin:
                Toast.makeText(this, "Cerrar Sesion", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(actividadUsuario.this, Login.class);
                startActivity(intent2);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapterUsuarioLibroItems.filtrado(s);
        return false;
    }

}