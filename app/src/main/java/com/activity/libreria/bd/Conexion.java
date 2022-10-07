package com.activity.libreria.bd;

import android.content.Context;
import android.widget.Toast;

import com.activity.libreria.Interfaces.Callback;
import com.activity.libreria.Interfaces.Logica;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaLibrosPrestados;
import com.activity.libreria.modelos.ListaUsuario;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Conexion implements Logica{

    ListaLibros listaLibros;
    ListaLibrosPrestados listaLibrosPrestados;
    ListaUsuario listaUsuario;

    @Override
    public void consultaLibros(String URL, Context context, Callback callback) {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaLibros = gson.fromJson(String.valueOf(response), ListaLibros.class);
                        callback.getLibrosDisponibles(listaLibros);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) ;
        //{
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//               params.put("id","1");
//               params.put("name", "myname");
//               return params;
//            }
//        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsObjRequest);

    }

    @Override
    public void consultaLibrosPrestados(String URL, Context context, Callback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaLibrosPrestados = gson.fromJson(String.valueOf(response), ListaLibrosPrestados.class);
                        callback.getLibrosDisponibles(listaLibrosPrestados);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) ;
//                {
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id","1");
//                params.put("name", "myname");
//                return params;
//            };
        //};
        requestQueue.add(jsObjRequest);
    }

    @Override
    public void buscarUsuarios(String URL, Context context, Callback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaUsuario = gson.fromJson(String.valueOf(response), ListaUsuario.class);
                        callback.getUsuarioActivo(listaUsuario);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) ;
//                {
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id","1");
//                params.put("name", "myname");
//                return params;
//            };
        //};
        requestQueue.add(jsObjRequest);
    }

}


