package com.activity.libreria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.activity.libreria.metodos.MetodosUsuario;
import com.activity.libreria.modelos.Usuario;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity implements View.OnClickListener {

    EditText correoUsuario;
    EditText contraseñaUsuario;
    EditText nombreUsuario;
    EditText telefonoUsuario;
    EditText dirreccionUsuario;
    TextView textRegRegistrar;
    Button botonRegistrar;
    Usuario usuario;
    MetodosUsuario metodosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);
        findElement();
    }

    private void findElement() {
        usuario = new Usuario();
        metodosUsuario = new MetodosUsuario(this);
        correoUsuario = findViewById(R.id.registrarCorreo);
        contraseñaUsuario = findViewById(R.id.registrarContraseña);
        nombreUsuario = findViewById(R.id.registrarNombre);
        telefonoUsuario = findViewById(R.id.registrarTelefono);
        dirreccionUsuario = findViewById(R.id.registrarDireccion);
        botonRegistrar = findViewById(R.id.btnRegRegistrar);
        botonRegistrar.setOnClickListener(this);
        textRegRegistrar = findViewById(R.id.textRegRegistrar);
        textRegRegistrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegRegistrar:
            {
                //cetiando
                String correo = correoUsuario.getText().toString();
                String contraseña = contraseñaUsuario.getText().toString();
                String nombre = nombreUsuario.getText().toString();
                String telefono = telefonoUsuario.getText().toString();
                String direccion = dirreccionUsuario.getText().toString();

                usuario.setCorreoUsuario(correo);
                usuario.setContraseñaUsuario(contraseña);
                usuario.setNombreUsuario(nombre);
                usuario.setTelefonoUsuario(telefono);
                usuario.setDireccionUsuario(direccion);

                //Validaciones
                if (usuario.getCorreoUsuario().isEmpty() || usuario.getContraseñaUsuario().isEmpty() || usuario.getNombreUsuario().isEmpty() || usuario.getTelefonoUsuario().isEmpty()) {
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
                                    if (validar()){
                                        Intent intent = new Intent(Registrar.this, Login.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            break;
            case R.id.textRegRegistrar:
                Intent intent1 = new Intent(Registrar.this, Login.class);
                startActivity(intent1);
                finish();
                break;
        }

    }
    public boolean validar(){

        final String nombre=usuario.getNombreUsuario();
        final String correo=usuario.getCorreoUsuario();
        final String telefono=usuario.getTelefonoUsuario();
        final String direccion=usuario.getDireccionUsuario();
        final String contrasena=usuario.getContraseñaUsuario();

        String url="http://192.168.1.11:80/php/registro_usuario.php?Nombre_Usuario="+nombre+"&CorreoElectronico_Usuario="+correo+"&Telefono_Usuario="+telefono+"&Direccion_Usuario="+direccion+"&Contrasena_Usuario="+contrasena+"";
        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),
                        response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Error comunicación"+error,Toast.LENGTH_SHORT).show();
            }
        });
        servicio.add(respuesta);
        return true;
    }
}