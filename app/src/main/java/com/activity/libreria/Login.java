package com.activity.libreria;

import static com.activity.libreria.bd.NetwordHelper.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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
    public boolean onTouchEvent(MotionEvent event) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEntrar: {
                String correoLogin;
                String contraseñaLogin;
                correoLogin = correoUsuario.getText().toString().trim();
                contraseñaLogin = contraseñaUsuario.getText().toString().trim();

                if (correoLogin.equals("") && contraseñaLogin.equals("")) {
                    Toast.makeText(getApplicationContext(), "ERROR: Campos vacios", Toast.LENGTH_LONG).show();
                } else {
                    validarUsuario("https://"+IP_PUBLICA+"/validar_usuario.php");
                    sPreferences.setSharedPreference(correoLogin);
                }
            }
            break;
            case R.id.textRegistrar:
                Intent i = new Intent(Login.this, Registrar.class);
                startActivity(i);
                break;
        }

    }

    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals("Fail")) {
                            if (response.equals("usuario")) {
                                Intent intent = new Intent(getApplicationContext(), actividadUsuario.class);
                                startActivity(intent);
                            } else if (response.equals("administrador")) {
                                Intent intent = new Intent(getApplicationContext(), actividadAdministrador.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        System.out.printf(error.toString());
                    }
                }) {

            @Override
            public Map<String, String> getParams() {
                HashMap<String, String> headers = new HashMap<String, String>();
//               headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                headers.put("usuario", correoUsuario.getText().toString());
                headers.put("password", contraseñaUsuario.getText().toString());
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

    }


}