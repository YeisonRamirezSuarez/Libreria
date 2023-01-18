package com.activity.libreria.MVP.Model;

import static com.activity.libreria.bd.NetwordHelper.IP_PUBLICA;
import static com.activity.libreria.modelos.Constantes.*;

import android.content.Context;
import android.widget.Toast;

import com.activity.libreria.MVP.Interfaces.interfaces;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.LibrosPrestadosRsp;
import com.activity.libreria.modelos.LibrosRsp;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaLibrosPrestados;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.Mensajes;
import com.activity.libreria.modelos.UsuarioRsp;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Model implements interfaces.Model{

    Context context;
    interfaces.Presenter presenter;
    ListaLibros listaLibros;
    ListaUsuario listaUsuario;
    LibrosPrestadosRsp librosPrestadosRsp;
    ListaLibrosPrestados listaLibrosPrestados;
    SPreferences sPreferences;

    public Model(interfaces.Presenter presenter, Context context){
        this.context = context;
        this.presenter = presenter;
        listaLibros = new ListaLibros();
        listaUsuario = new ListaUsuario();
        listaLibrosPrestados = new ListaLibrosPrestados();
        librosPrestadosRsp = new LibrosPrestadosRsp();
        sPreferences = new SPreferences(context);
    }

    @Override
    public void peticionValidarLogin(UsuarioRsp usuarioRsp) {
        String URL = "https://"+IP_PUBLICA+"/login.php";

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("email", usuarioRsp.getEmail());
        postParam.put("password", usuarioRsp.getPassword());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        UsuarioRsp rol = gson.fromJson(String.valueOf(response), UsuarioRsp.class);
                        presenter.respuesta(rol.getRol(), SCREEN_LOGIN);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjReq);
    }

    @Override
    public void peticionCrearLibro(LibrosRsp librosRsp) {

        String URL="https://"+IP_PUBLICA+"/libro.php";

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("title", librosRsp.getTitle());
        postParam.put("author", librosRsp.getAuthor());
        postParam.put("quantity", librosRsp.getQuantity());
        postParam.put("book_url", librosRsp.getBook_url());
        postParam.put("image_url", librosRsp.getImage_url());
        postParam.put("description", librosRsp.getDescription());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Mensajes msj = gson.fromJson(String.valueOf(response), Mensajes.class);
                        presenter.respuesta(msj.getMensaje(), SCREEN_CREAR_LIBRO);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjReq);
    }

    @Override
    public void peticionCrearUsuario(UsuarioRsp usuarioRsp) {
        String URL="https://"+IP_PUBLICA+"/usuario.php";

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("name", usuarioRsp.getName());
        postParam.put("email", usuarioRsp.getEmail());
        postParam.put("phone", usuarioRsp.getPhone());
        postParam.put("address", usuarioRsp.getAddress());
        postParam.put("password", usuarioRsp.getPassword());
        postParam.put("rol", "usuario");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Mensajes msj = gson.fromJson(String.valueOf(response), Mensajes.class);
                        presenter.respuesta(msj.getMensaje(), SCREEN_REGISTRAR);
                        //presenter.respuesta(msj.getMensaje(), SCREEN_CREAR_LIBRO);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjReq);
        final String nombre=usuarioRsp.getName();
        final String correo=usuarioRsp.getEmail();
        final String telefono=usuarioRsp.getPhone();
        final String direccion=usuarioRsp.getAddress();
        final String contrasena=usuarioRsp.getPassword();

        String url="https://"+IP_PUBLICA+"/registro_usuario.php?Nombre_Usuario="+nombre+"&CorreoElectronico_Usuario="+correo+"&Telefono_Usuario="+telefono+"&Direccion_Usuario="+direccion+"&Contrasena_Usuario="+contrasena+"";
        RequestQueue servicio= Volley.newRequestQueue(context);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                public void onResponse(String response) {
                   presenter.respuesta(response, SCREEN_REGISTRAR);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,
                            "Error comunicación"+error,Toast.LENGTH_SHORT).show();
                }
            });
            servicio.add(respuesta);

    }


    @Override
    public void peticionActualizarLibro(LibrosRsp librosRsp, String screen) {
        if (screen.equals(SCREEN_ACTUALIZAR_LIBRO)) {
            String URL="https://"+IP_PUBLICA+"/libro.php?update=1&id="+librosRsp.getId()+"";

            Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("title", librosRsp.getTitle());
            postParam.put("author", librosRsp.getAuthor());
            postParam.put("quantity", librosRsp.getQuantity());
            postParam.put("book_url", librosRsp.getBook_url());
            postParam.put("image_url", librosRsp.getImage_url());
            postParam.put("description", librosRsp.getDescription());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    URL, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Gson gson = new Gson();
                            Mensajes msj = gson.fromJson(String.valueOf(response), Mensajes.class);
                            presenter.respuesta(msj.getMensaje(), screen);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Gson gson = new Gson();
                    Mensajes msj = gson.fromJson(String.valueOf(error), Mensajes.class);
                    Toast.makeText(context, msj.getMensaje(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonObjReq);

        }else if(screen.equals(SCREEN_ACTUALIZAR_LIBRO_PRESTADO)){

            String URL="https://"+IP_PUBLICA+"/libro.php?prestado=1&id="+librosRsp.getId()+"";

            Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("title", librosRsp.getTitle());
            postParam.put("author", librosRsp.getAuthor());
            postParam.put("quantity", librosRsp.getQuantity());
            postParam.put("book_url", librosRsp.getBook_url());
            postParam.put("image_url", librosRsp.getImage_url());
            postParam.put("description", librosRsp.getDescription());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    URL, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Gson gson = new Gson();
                            Mensajes msj = gson.fromJson(String.valueOf(response), Mensajes.class);
                            System.out.printf(msj.getMensaje());
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonObjReq);
        }
    }

    @Override
    public void peticionEliminarLibro(LibrosRsp librosRsp, String screen) {
        if (screen.equals(SCREEN_ACTUALIZAR_LIBRO)) {
            String URL="https://"+IP_PUBLICA+"/libro.php?delete=1&id="+librosRsp.getId()+"";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }else if(screen.equals(SCREEN_ACTUALIZAR_LIBRO_PRESTADO)){
            String URL="https://"+IP_PUBLICA+"/libro.php?delete_prestado=1&id="+librosRsp.getId()+"";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            presenter.respuesta("Libro eliminado", SCREEN_ACTUALIZAR_LIBRO_PRESTADO);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void peticionEliminarLibroPrestado(LibrosPrestadosRsp librosPrestadosRsp,String screen) {
        int id = librosPrestadosRsp.get_id();
        String URL = "https://"+IP_PUBLICA+"/peticiones.php?delete=1&id="+id+"";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        peticionConsultaLibroId(librosPrestadosRsp,"");
                        presenter.respuesta("Libro devuelto", screen);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void peticionPrestarLibro(ListaUsuario listaUsuario, LibrosRsp librosRsp, String screen) {
        DateFormat df = new SimpleDateFormat(" d MMM yyyy, HH:mm"); //definir formato para fecha
        String fecha_registro = df.format(Calendar.getInstance().getTime()); //obtener fecha
        LibrosPrestadosRsp libros = new LibrosPrestadosRsp();

        libros.setId_book(librosRsp.getId());
        libros.setTitle(librosRsp.getTitle());
        libros.setAuthor(librosRsp.getAuthor());
        libros.setBook_url(librosRsp.getBook_url());
        libros.setImage_url(librosRsp.getImage_url());
        libros.setDescription(librosRsp.getDescription());
        libros.setDate(fecha_registro);
        libros.setEmail_user(listaUsuario.getUsuarios().get(0).getEmail());
        libros.setName_user(listaUsuario.getUsuarios().get(0).getName());
        libros.setPhone_user(listaUsuario.getUsuarios().get(0).getPhone());

        String URL="https://"+IP_PUBLICA+"/peticiones.php?prestar=1";

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("id_book", String.valueOf(libros.getId_book()));
        postParam.put("title", libros.getTitle());
        postParam.put("author", libros.getAuthor());
        postParam.put("book_url", libros.getBook_url());
        postParam.put("image_url", libros.getImage_url());
        postParam.put("description", libros.getDescription());
        postParam.put("date", libros.getDate());
        postParam.put("email_user", libros.getEmail_user());
        postParam.put("name_user", libros.getName_user());
        postParam.put("phone_user", libros.getPhone_user());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        InputlibrosCantidadRestar(listaLibros, libros.getId_book());
                        presenter.respuesta("Libro prestado", screen);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjReq);
    }

    public boolean InputlibrosCantidadRestar(ListaLibros listaLibros, int id_libro){
        //final String cantidad= String.valueOf(Integer.parseInt(listaLibros.getLibros().get(0).getQuantity()) - 1);
        String cantidad = null;
        int cantidadLibro = 0;
        for(LibrosRsp libro : listaLibros.getLibros()){
            if (libro.getId() == id_libro){
                cantidad = libro.getQuantity();
                cantidadLibro = Integer.parseInt(cantidad) - 1;
            }
        }

        String url="https://"+IP_PUBLICA+"/peticiones.php?cantidad=1&id="+id_libro+"&cantidad_libro="+cantidadLibro+"";
        RequestQueue servicio= Volley.newRequestQueue(context);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "Error comunicación"+error,Toast.LENGTH_SHORT).show();
            }
        });
        servicio.add(respuesta);
        return true;
    }

    public boolean InputlibrosCantidadSumar(ListaLibros listaLibros){
        final int id=listaLibros.getLibros().get(0).getId();
        final String cantidad= String.valueOf(Integer.parseInt(listaLibros.getLibros().get(0).getQuantity()) + 1);

        String url="https://"+IP_PUBLICA+"/peticiones.php?cantidad=1&id="+id+"&cantidad_libro="+cantidad+"";
        RequestQueue servicio= Volley.newRequestQueue(context);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "Error comunicación"+error,Toast.LENGTH_SHORT).show();
            }
        });
        servicio.add(respuesta);
        return true;
    }

    @Override
    public void peticionUsuario() {
        String URL = "https://"+IP_PUBLICA+"/usuario.php?email="+sPreferences.getSharedPreference()+"";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        UsuarioRsp usuario = gson.fromJson(String.valueOf(response), UsuarioRsp.class);
                        ArrayList<UsuarioRsp> lista = new ArrayList<>();
                        lista.add(usuario);
                        listaUsuario.setUsuarios(lista);
                        //listaUsuario = gson.fromJson(String.valueOf(response), ListaUsuario.class);
                        presenter.respuestaUsuario(listaUsuario);
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

    @Override
    public void peticionLibros(String screen) {

        String URL = "https://"+IP_PUBLICA+"/libro.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Gson gson = new Gson();
//                        UsuarioRsp usuario = gson.fromJson(String.valueOf(response), UsuarioRsp.class);
//                        ArrayList<UsuarioRsp> lista = new ArrayList<>();
//                        lista.add(usuario);
//                        listaUsuario.setUsuarios(lista);
//                        //listaUsuario = gson.fromJson(String.valueOf(response), ListaUsuario.class);
//                        presenter.respuestaUsuario(listaUsuario);
                        Gson gson = new Gson();
                        listaLibros = gson.fromJson(String.valueOf(response), ListaLibros.class);
                        presenter.respuestaLibros(listaLibros, screen);
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

    @Override
    public void peticionLibrosPrestados(String screen) {
        String URL = "https://"+IP_PUBLICA+"/peticiones.php?prestado=1";
        String correo = sPreferences.getSharedPreference();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaLibrosPrestados = gson.fromJson(String.valueOf(response), ListaLibrosPrestados.class);
                        presenter.respuestaLibrosPrestados(listaLibrosPrestados, correo, screen);
                      //  callback.getLibrosDisponibles(listaLibrosPrestados);
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
    public void peticionLibrosPrestadosId(Object object, String screen) {
        librosPrestadosRsp = (LibrosPrestadosRsp) object;
        int id = librosPrestadosRsp.getId_book();
        String URL = "https://"+IP_PUBLICA+"/peticiones.php?prestado=1&id="+id+"";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaLibrosPrestados = gson.fromJson(String.valueOf(response), ListaLibrosPrestados.class);
                        presenter.respuestaLibrosPrestados(librosPrestadosRsp, listaLibrosPrestados, screen);
                        //callback.getLibrosDisponibles(listaLibrosPrestados);
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
    public void peticionLibrosPrestadosUsuario(String screen) {
        String URL = "https://"+IP_PUBLICA+"/libro.php?email="+sPreferences.getSharedPreference()+"";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaLibrosPrestados = gson.fromJson(String.valueOf(response), ListaLibrosPrestados.class);
                        presenter.respuestaLibrosPrestados(listaLibrosPrestados, "", screen);
                        //callback.getLibrosDisponibles(listaLibrosPrestados);
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
    public void peticionConsultaLibroPrestados(String screen) {
        String URL = "https://"+IP_PUBLICA+"/peticiones.php?prestados=1";
        String correo = sPreferences.getSharedPreference();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaLibrosPrestados = gson.fromJson(String.valueOf(response), ListaLibrosPrestados.class);
                        presenter.respuestaLibrosPrestados(listaLibrosPrestados, correo, screen);
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

    public void peticionConsultaLibroId(LibrosPrestadosRsp librosPrestadosRsp,String screen) {
        int id = librosPrestadosRsp.getId_book();
        String URL = "https://"+IP_PUBLICA+"/libro.php?id="+id+"";
        String correo = sPreferences.getSharedPreference();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaLibros = gson.fromJson(String.valueOf(response), ListaLibros.class);
                        InputlibrosCantidadSumar(listaLibros);
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
}
