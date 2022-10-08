package com.activity.libreria.MVP.Model;

import static com.activity.libreria.bd.NetwordHelper.IP_PUBLICA;
import static com.activity.libreria.modelos.Constantes.*;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.activity.libreria.MVP.Interfaces.interfaces;
import com.activity.libreria.actividadAdministrador;
import com.activity.libreria.actividadUsuario;
import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.LibrosPrestadosRsp;
import com.activity.libreria.modelos.LibrosRsp;
import com.activity.libreria.modelos.ListaLibros;
import com.activity.libreria.modelos.ListaLibrosPrestados;
import com.activity.libreria.modelos.ListaUsuario;
import com.activity.libreria.modelos.UsuarioRsp;
import com.activity.libreria.splashscreen;
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
        String URL = "https://"+IP_PUBLICA+"/validar_usuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                           presenter.respuesta(response, SCREEN_LOGIN);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        System.out.printf(error.toString());
                    }
                }) {

            @Override
            public Map<String, String> getParams() {
                HashMap<String, String> headers = new HashMap<String, String>();
//               headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                headers.put("usuario", usuarioRsp.getCorreo_Electronico());
                headers.put("password", usuarioRsp.getContrasena_Usuario());
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void peticionCrearLibro(LibrosRsp librosRsp) {
        final String nombre=librosRsp.getTitulo_libro();
        final String autor=librosRsp.getAutor_libro();
        final String cantidad=librosRsp.getCantidad_libro();
        final String urlLibro=librosRsp.getUrl_libro();
        final String imagen=librosRsp.getImagen_libro();
        final String descripcion=librosRsp.getDescripcion_libro();

        String url="https://"+IP_PUBLICA+"/registro_libro.php?Titulo_libro="+nombre+"&Autor_libro="+autor+"&Cantidad_libro="+cantidad+"&Url_libro="+urlLibro+"&Imagen_libro="+imagen+"&Descripcion_libro="+descripcion+"";
        RequestQueue servicio= Volley.newRequestQueue(context);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                presenter.respuesta(response, SCREEN_CREAR_LIBRO);
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
            int id_libro = librosRsp.get_id();
            String nombre = librosRsp.getTitulo_libro();
            String autor = librosRsp.getAutor_libro();
            String cantidad = librosRsp.getCantidad_libro();
            String urlLibro = librosRsp.getUrl_libro();
            String imagen = librosRsp.getImagen_libro();
            String descripcion = librosRsp.getDescripcion_libro();

            String url = "https://" + IP_PUBLICA + "/actualizar_libro.php?id=" + id_libro + "&Titulo_libro=" + nombre + "&Autor_libro=" + autor + "&Cantidad_libro=" + cantidad + "&Url_libro=" + urlLibro + "&Imagen_libro=" + imagen + "&Descripcion_libro=" + descripcion + "";
            RequestQueue servicio = Volley.newRequestQueue(context);
            StringRequest respuesta = new StringRequest(
                    Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("correcto")) {
                        presenter.respuesta(response, screen);
                    } else if (response.equals("Fallo")) {
                        Toast.makeText(context,
                                "Fallo la actualizacion correctamente", Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,
                            error.toString(), Toast.LENGTH_LONG).show();
                }
            });
            servicio.add(respuesta);
        }else if(screen.equals(SCREEN_ACTUALIZAR_LIBRO_PRESTADO)){
            int id_libro = librosRsp.get_id();
            String nombre=librosRsp.getTitulo_libro();
            String autor=librosRsp.getAutor_libro();
            String cantidad=librosRsp.getCantidad_libro();
            String urlLibro=librosRsp.getUrl_libro();
            String imagen=librosRsp.getImagen_libro();
            String descripcion=librosRsp.getDescripcion_libro();

            String url="https://"+IP_PUBLICA+"/actualizar_libro_prestado.php?id="+ id_libro +"&Titulo_libro="+nombre+"&Autor_libro="+autor+"&Cantidad_libro="+cantidad+"&Url_libro="+urlLibro+"&Imagen_libro="+imagen+"&Descripcion_libro="+descripcion+"";
            RequestQueue servicio= Volley.newRequestQueue(context);
            StringRequest respuesta=new StringRequest(
                    Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.printf(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,
                            error.toString(),Toast.LENGTH_LONG).show();
                }
            });
            servicio.add(respuesta);
        }
    }

    @Override
    public void peticionEliminarLibro(LibrosRsp librosRsp, String screen) {
        if (screen.equals(SCREEN_ACTUALIZAR_LIBRO)) {
            int id_libro = librosRsp.get_id();
            String URL = "https://" + IP_PUBLICA + "/eliminar_libro.php?id=" + id_libro + "";
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
            int id_libro = librosRsp.get_id();
            String URL = "https://"+IP_PUBLICA+"/eliminar_libro_prestado_id.php?id="+id_libro+"";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            presenter.respuesta(response, SCREEN_ACTUALIZAR_LIBRO_PRESTADO);
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
        String URL = "https://"+IP_PUBLICA+"/eliminar_libro_prestado.php?id="+id+"";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        peticionConsultaLibroId(librosPrestadosRsp,"");
                        presenter.respuesta(response, screen);
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
        final int id=librosRsp.get_id();
        final String nombre=librosRsp.getTitulo_libro();
        final String autor=librosRsp.getAutor_libro();
        final String cantidad=librosRsp.getCantidad_libro();
        final String urlLibro=librosRsp.getUrl_libro();
        final String imagen=librosRsp.getImagen_libro();
        final String descripcion=librosRsp.getDescripcion_libro();
        final String Fecha_Prestamo_libro=fecha_registro;
        final String Correo_Prestamo_libro=listaUsuario.getUsuarios().get(0).getCorreo_Electronico();
        final String Nombre_Usuario_Prestamo_libro=listaUsuario.getUsuarios().get(0).getNombre_Usuario();
        final String Telefono_Usuario_Prestamo_libro =listaUsuario.getUsuarios().get(0).getTelefono_Usuario();

        String url="https://"+IP_PUBLICA+"/registro_libro_prestado.php?id="+id+"&Titulo_libro="+nombre+"&Autor_libro="+autor+"&Cantidad_libro="+cantidad+"&Url_libro="+urlLibro+"&Imagen_libro="+imagen+"&Descripcion_libro="+descripcion+"" +
                "&Fecha_Prestamo_libro="+Fecha_Prestamo_libro+"&Correo_Prestamo_libro="+Correo_Prestamo_libro+"&Nombre_Usuario_Prestamo_libro="+Nombre_Usuario_Prestamo_libro+"&Telefono_Usuario_Prestamo_libro="+Telefono_Usuario_Prestamo_libro+"";
        RequestQueue servicio= Volley.newRequestQueue(context);
        StringRequest respuesta=new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                InputlibrosCantidad(listaLibros);
                presenter.respuesta(response, screen);
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

    public boolean InputlibrosCantidad(ListaLibros listaLibros){
        final int id=listaLibros.getLibros().get(0).get_id();
        final String cantidad= String.valueOf(Integer.parseInt(listaLibros.getLibros().get(0).getCantidad_libro()) - 1);

        String url="https://"+IP_PUBLICA+"/actualizar_cantidad_libro.php?id="+id+"&Cantidad_libro="+cantidad+"";
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
        String URL = "https://"+IP_PUBLICA+"/consulta_usuario.php?correo="+sPreferences.getSharedPreference()+"";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaUsuario = gson.fromJson(String.valueOf(response), ListaUsuario.class);
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
        String URL = "https://"+IP_PUBLICA+"/libros_disponibles.php";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaLibros = gson.fromJson(String.valueOf(response), ListaLibros.class);
                        presenter.respuestaLibros(listaLibros, screen);
                        //callback.getLibrosDisponibles(listaLibros);
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
    public void peticionLibrosPrestados(String screen) {
        String URL = "https://"+IP_PUBLICA+"/libros_prestados_disponibles_por_id.php";
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
        int id = librosPrestadosRsp.get_id_Libro();
        String URL = "https://"+IP_PUBLICA+"/consulta_libros_prestados.php?id="+id+"";
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
        String URL = "https://"+IP_PUBLICA+"/consulta_libro_prestado.php?correo="+sPreferences.getSharedPreference()+"";
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
        String URL = "https://"+IP_PUBLICA+"/libros_prestados_disponibles.php";
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
        int id = librosPrestadosRsp.get_id_Libro();
        String URL = "https://"+IP_PUBLICA+"/consulta_libro_id.php?id="+id+"";
        String correo = sPreferences.getSharedPreference();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        listaLibros = gson.fromJson(String.valueOf(response), ListaLibros.class);
                        InputlibrosCantidad(listaLibros);
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
