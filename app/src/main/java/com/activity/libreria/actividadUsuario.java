package com.activity.libreria;


import static com.activity.libreria.bd.NetwordHelper.IP_PUBLICA;
import static com.activity.libreria.bd.NetwordHelper.PUERTO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.Interfaces.Callback;
import com.activity.libreria.adapter.AdapterAdministradorLibroItems;
import com.activity.libreria.adapter.AdapterUsuarioDisponiblesLibroItems;
import com.activity.libreria.adapter.AdapterUsuarioLibroItems;
import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.bd.Conexion;
import com.activity.libreria.metodos.MetodosLibros;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaLibrosPrestados;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.Usuario;

import java.util.ArrayList;

public class actividadUsuario extends AppCompatActivity implements View.OnClickListener,MenuItem.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener, SearchView.OnQueryTextListener, Callback {

    //Los llamamos aqui asi:
    SearchView txtBuscar;
    RecyclerView reciclarVistaUsuario;
    TextView titulo;
    BDHelper BDLibro;
    AdapterUsuarioLibroItems adapterUsuarioLibroItems;
    AdapterAdministradorLibroItems adapterAdministradorLibroItems;
    MetodosUsuario metodosUsuario;
    MetodosLibros metodosLibros;
    TextView nombre_usuario_txt, rol;
    ImageView mas_informacion;
    Button btnPrestar;
    SPreferences sPreferences;
    Usuario usuario;
    Conexion conexion;
    ListaUsuario listaUsuario;
    ListaLibrosPrestados listaLibrosPrestados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_usuario);
        Context context;
        findElement();
    }


    private void findElement() {
        sPreferences = new SPreferences(this);
        metodosUsuario = new MetodosUsuario(this);
        metodosLibros = new MetodosLibros(this);
        txtBuscar = findViewById(R.id.txtBuscar);
        txtBuscar.setOnQueryTextListener(actividadUsuario.this);
        txtBuscar.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.seach_hint) + "</font>"));
        txtBuscar.setVisibility(View.VISIBLE);
        //nombre_usuario_txt = findViewById(R.id.nombre_usuario_txt);
        rol = findViewById(R.id.rol);
        reciclarVistaUsuario = findViewById(R.id.reciclarVistaUsuario);
        mas_informacion = findViewById(R.id.funcionesUser);
        mas_informacion.setOnClickListener(this);
        btnPrestar = findViewById(R.id.btnPrestar);
        btnPrestar.setOnClickListener(this);
        titulo = findViewById(R.id.tituloBannerUser);
        titulo.setText("Mis Libros Prestados");
        conexion = new Conexion();
        listaUsuario = new ListaUsuario();
        listaLibrosPrestados = new ListaLibrosPrestados();
        //conexion.consultaLibrosPrestados("http://"+IP_PUBLICA+":"+PUERTO+"/php/libros_prestados_disponibles.php", this, this);
        conexion.consultaLibrosPrestados("https://"+IP_PUBLICA+"/consulta_libro_prestado.php?correo="+sPreferences.getSharedPreference()+"", this, this);
        conexion.buscarUsuarios("https://"+IP_PUBLICA+"/consulta_usuario.php?correo="+sPreferences.getSharedPreference()+"", this, this);
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
        listaLibrosPrestados = (ListaLibrosPrestados) object;
        adapterUsuarioLibroItems = new AdapterUsuarioLibroItems(actividadUsuario.this, listaLibrosPrestados.getLibros());
        reciclarVistaUsuario.setAdapter(adapterUsuarioLibroItems);
        reciclarVistaUsuario.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarDatosUsuarios(Object object) {
        // Aqui es como se muestra el nombre del Usuario que ingreso

        listaUsuario= (ListaUsuario) object;
        rol.setText(listaUsuario.getUsuarios().get(0).getRol_Usuario());
        nombre_usuario_txt.setText(listaUsuario.getUsuarios().get(0).getNombre_Usuario());
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
}