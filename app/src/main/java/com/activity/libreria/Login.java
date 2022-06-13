package com.activity.libreria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.metodos.MetodosAdministrador;
import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Administrador;
import com.activity.libreria.modelos.Usuario;


public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText correoUsuario;
    EditText contraseñaUsuario;
    TextView textRegistrar;
    Button btnEntrar;
    Usuario usuario;
    MetodosUsuario metodosUsuario;
    MetodosAdministrador metodosAdministrador;
    SPreferences sPreferences;
    Administrador administrador;
    BDHelper bdHelper;

    String nombreQuemado = "Yeison Fabian Ramirez Suarez";
    String correoQuemado = "admin@gmail.com";
    String contraseñaQuemado = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findElement();


    }

    private void findElement() {
        bdHelper = new BDHelper(this);
        usuario = new Usuario();
        metodosUsuario = new MetodosUsuario(this);
        administrador = new Administrador();
        metodosAdministrador = new MetodosAdministrador(this);
        sPreferences = new SPreferences(this);
        correoUsuario = findViewById(R.id.correoUsuario);
        contraseñaUsuario = findViewById(R.id.contraseñaUsuario);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(this);
        textRegistrar = findViewById(R.id.textRegistrar);
        textRegistrar.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEntrar: {
                String correoLogin = correoUsuario.getText().toString().trim();
                String contraseñaLogin = contraseñaUsuario.getText().toString().trim();
                usuario.setCorreoUsuario(correoLogin);
                usuario.setContraseñaUsuario(contraseñaLogin);
                if (correoLogin.equals("") && contraseñaLogin.equals("")) {
                    Toast.makeText(getApplicationContext(), "ERROR: Campos vacios", Toast.LENGTH_LONG).show();
                } else if (metodosUsuario.login(usuario) == 1) {
                    sPreferences.setSharedPreference(correoUsuario.getText().toString());
                    Toast.makeText(getApplicationContext(), "Datos correctos", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(Login.this, actividadUsuario.class);
                    startActivity(i2);
                    //Valido si lo que se esta ingresando en el Editex es igual a los datos que estan quemados
                } else if (correoUsuario.getText().toString().equals(correoQuemado) && contraseñaUsuario.getText().toString().equals(contraseñaQuemado)) {
                    administrador.setNombreAdministrador(nombreQuemado);
                    administrador.setCorreoAdministrador(correoQuemado);
                    administrador.setContraseñaAdministrador(contraseñaQuemado);
                    sPreferences.setSharedPreference(correoQuemado);
                    //Valido si el administrador ya esta registrado
                    if (metodosAdministrador.login(administrador) == 0) {
                        metodosAdministrador.registrarAdministrador(administrador);
                    }
                    Intent i2 = new Intent(Login.this, actividadAdministrador.class);
                    startActivity(i2);
                } else {
                    Toast.makeText(getApplicationContext(), "DATOS INCORRECTOS", Toast.LENGTH_LONG).show();
                }
            }
            break;
            case R.id.textRegistrar:
                Intent i = new Intent(Login.this, Registrar.class);
                startActivity(i);
                break;
        }

    }
}