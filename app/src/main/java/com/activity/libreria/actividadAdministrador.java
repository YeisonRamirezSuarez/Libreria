package com.activity.libreria;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.activity.libreria.adapter.AdapterAdministradorLibroItems;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.modelos.Administrador;
import com.activity.libreria.modelos.Libros;

import java.util.ArrayList;

public class actividadAdministrador extends AppCompatActivity implements View.OnClickListener,MenuItem.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener, SearchView.OnQueryTextListener {

    //Los llamamos aqui asi:
    SearchView txtBuscar;
    RecyclerView reciclarVista;
    TextView titulo;
    AdapterAdministradorLibroItems adapterAdministradorLibroItems;
    Administrador administrador;
    MetodosAdministrador metodosAdministardor;
    MetodosLibros metodosLibros;
    TextView nombre_administrador_txt;
    ImageView mas_informacion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_administrador);
        findElement();
        traerRecyclerView();
        cargarDatosAdministrador();
    }

    private void findElement() {
        metodosAdministardor = new MetodosAdministrador(this);
        metodosLibros = new MetodosLibros(this);
        txtBuscar = findViewById(R.id.txtBuscar);
        nombre_administrador_txt = findViewById(R.id.nombre_administrador_txt);
        reciclarVista = findViewById(R.id.reciclarVista);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        txtBuscar.setOnQueryTextListener(actividadAdministrador.this);
        txtBuscar.setVisibility(View.VISIBLE);
        mas_informacion = findViewById(R.id.funcionesUser);
        mas_informacion.setOnClickListener(this);
        titulo = findViewById(R.id.tituloBanner);
        titulo.setText("Libros Disponibles");
    }

    private void traerRecyclerView() {
        //Aqui es donde nos muestra los libros 1 por 1
        ArrayList<Libros> listaLibros = metodosLibros.almacenarDatosEnArrays();
        adapterAdministradorLibroItems = new AdapterAdministradorLibroItems(actividadAdministrador.this, listaLibros);
        reciclarVista.setAdapter(adapterAdministradorLibroItems);
        reciclarVista.setLayoutManager(new GridLayoutManager(actividadAdministrador.this, 2));
    }

    private void cargarDatosAdministrador() {
        // Aqui es como se muestra el nombre del Usuario que ingreso
        ArrayList<Administrador> listaAdministrador  = metodosAdministardor.selectAdministrador();
        nombre_administrador_txt.setText(listaAdministrador.get(0).getNombreAdministrador());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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

    public void showPopup(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.mas_admin);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.prestadosLibros:
                Toast.makeText(this, "Libros Prestados", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(actividadAdministrador.this, LibrosPrestadosAdmin.class);
                startActivity(intent);
                return true;
            case R.id.agregarLibros:
                Toast.makeText(this, "Agregar Libros", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(actividadAdministrador.this, AgregarLibros.class);
                startActivity(intent1);
                return true;
            case R.id.salirLogin:
                Toast.makeText(this, "Cerrar Sesion", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(actividadAdministrador.this, Login.class);
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
        adapterAdministradorLibroItems.filtrado(s);
        return false;
    }

    //para buscador


//    public boolean onQueryTextSubmit(String string) {
//        return false;
//    }
//
//    public boolean onQueryTextChange(String string) {
//        adapterAdministradorLibroItems.filtrado(string);
//        return false;
//    }yeisoye
}