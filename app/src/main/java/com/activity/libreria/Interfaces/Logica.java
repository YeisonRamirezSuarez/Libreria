package com.activity.libreria.Interfaces;


import android.content.Context;

public interface Logica {
   void consultaLibros(String URL, Context context, Callback callback);
   void buscarUsuarios(String URL, Context contex, Callback callback);
}
