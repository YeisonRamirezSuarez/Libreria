package com.activity.libreria.metodos;

import static com.activity.libreria.bd.BDHelper.COLUMNA_ADMIN_CORREO;
import static com.activity.libreria.bd.BDHelper.COLUMN_ADMIN_CONTRASENA;
import static com.activity.libreria.bd.BDHelper.COLUMN_ADMIN_NOMBRE;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_CONTRASENA;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_CORREO;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_DIRECCION;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_NOMBRE;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_TELEFONO;
import static com.activity.libreria.bd.BDHelper.TABLE_ADMIN;
import static com.activity.libreria.bd.BDHelper.TABLE_USER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.widget.AppCompatEditText;

import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.modelos.Administrador;
import com.activity.libreria.modelos.Usuario;


import java.util.ArrayList;

public class MetodosAdministrador {
    ArrayList<Administrador> listaAdmin;
    Context context;
    Administrador administrador;

    public MetodosAdministrador(Context context) {
        this.context = context;
    }

    public ArrayList<Administrador> selectAdministrador() {
        SPreferences sPreferences;
        sPreferences = new SPreferences(context);
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();
        ArrayList<Administrador> listaAdministrador = new ArrayList<>();
        listaAdministrador.clear();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADMIN  + " WHERE " + COLUMNA_ADMIN_CORREO + " = '" + sPreferences.getSharedPreference()  + "' ", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Administrador administrador = new Administrador();
                administrador.setId(cursor.getInt(0));
                administrador.setNombreAdministrador(cursor.getString(1));
                administrador.setCorreoAdministrador(cursor.getString(2));
                administrador.setContraseñaAdministrador(cursor.getString(3));
                listaAdministrador.add(administrador);

            } while (cursor.moveToNext());
        }
        return listaAdministrador;
    }


    public boolean validarAdmin(Administrador admin) {
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getReadableDatabase();


        String query = "SELECT * FROM " + TABLE_ADMIN + " WHERE " + COLUMNA_ADMIN_CORREO + " = '" + admin.getCorreoAdministrador() +
                "' and " + COLUMN_ADMIN_CONTRASENA + " = '" + admin.getContraseñaAdministrador() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    public int login(Administrador administrador) {
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();
        int a = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM  " + TABLE_ADMIN, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                if (cursor.getString(2).equals(administrador.getCorreoAdministrador()) && cursor.getString(3).equals(administrador.getContraseñaAdministrador())) {
                    a++;
                }
            } while (cursor.moveToNext());
        }
        return a;
    }

    public long registrarAdministrador(Administrador administrador) {
        long id = 0;

        try (BDHelper bdHelper = new BDHelper(context)){
            SQLiteDatabase db = bdHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_ADMIN_NOMBRE, administrador.getNombreAdministrador());
            values.put(COLUMNA_ADMIN_CORREO, administrador.getCorreoAdministrador());
            values.put(COLUMN_ADMIN_CONTRASENA, administrador.getContraseñaAdministrador());

            id = db.insert(TABLE_ADMIN,null,values);

        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }
}
