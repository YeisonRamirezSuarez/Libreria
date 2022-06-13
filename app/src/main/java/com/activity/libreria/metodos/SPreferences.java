package com.activity.libreria.metodos;

import android.content.Context;
import android.content.SharedPreferences;

public class SPreferences {
    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public SPreferences(Context context){
        this.context = context;
        sp = context.getSharedPreferences("base_sp", context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setSharedPreference(String datoGuardar){
        editor.putString("dato", datoGuardar);
        editor.apply();
    }

    public String getSharedPreference(){
        return sp.getString("dato","dato no encontrado");
    }
}

