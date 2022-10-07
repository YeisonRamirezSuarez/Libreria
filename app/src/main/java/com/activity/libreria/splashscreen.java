package com.activity.libreria;

import static com.activity.libreria.bd.NetwordHelper.IP_PUBLICA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activity.libreria.MVP.Interfaces.interfaces;
import com.activity.libreria.MVP.Presenter.Presenter;
import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Administrador;
import com.activity.libreria.modelos.Usuario;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class splashscreen extends AppCompatActivity  {

    interfaces.View view;
    Context context;
    ImageView imageView;
    String clase;
    interfaces.Presenter presenter;

    public splashscreen(interfaces.View view, Context context) {
        this.view = view;
        this.context = context;
        presenter = new Presenter(view, context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        traerIdRecyclerView(savedInstanceState);
        findElement();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //accion();
                finish();
            }
        }, 5000);
    }

    private void findElement() {
        imageView = findViewById(R.id.logo);
    }

    private void traerIdRecyclerView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                clase = String.valueOf(null);
            } else {
                clase = extras.getString("ACCION");
            }
        } else clase = (String) savedInstanceState.getSerializable("ACCION");
    }

      /*  private void accion (String screen) {
            switch (clase) {
                case "usuario":
                    Intent i = new Intent(splashscreen.this, actividadUsuario.class);
                    startActivity(i);
                    break;
                case "administrador":
                    presenter.pedirLibros();
                    Intent i1 = new Intent(splashscreen.this, actividadAdministrador.class);
                    startActivity(i1);
                    break;
            }*/
        //}





    @Override
    public void onBackPressed() {

    }

}