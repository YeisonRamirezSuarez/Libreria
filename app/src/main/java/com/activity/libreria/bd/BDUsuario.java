package com.activity.libreria.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.activity.libreria.metodos.SPreferences;
import com.activity.libreria.modelos.Libros;

import java.util.ArrayList;

public class BDUsuario extends BDHelper{

    Context context;

    public BDUsuario(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    public ArrayList<Libros> mostrarLibroUsuario() {

        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();
        SPreferences sPreferences = new SPreferences(context);

        ArrayList<Libros> listaLibros = new ArrayList<>();
        Libros libros;
        Cursor cursorLibros;


        cursorLibros = sql.rawQuery("SELECT * FROM " + TABLE_USER  + " WHERE " + "loginCorreo" + " = '" + sPreferences.getSharedPreference()  + "' ", null);

        if (cursorLibros.moveToFirst()) {
            do {
                libros = new Libros();
                libros.setId(cursorLibros.getInt(0));
                libros.setNombreLibro(cursorLibros.getString(1));
                libros.setAutorLibro(cursorLibros.getString(2));
                libros.setCantidadLibro(cursorLibros.getString(3));
                listaLibros.add(libros);
            } while (cursorLibros.moveToNext());
        }

        cursorLibros.close();

        return listaLibros;
    }

    public Libros verLibroUsuario(int id) {

        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();
        Libros libros = null;
        Cursor cursorLibros;

        cursorLibros = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorLibros.moveToFirst()) {
            libros = new Libros();
            libros.setId(cursorLibros.getInt(0));
            libros.setNombreLibro(cursorLibros.getString(1));
            libros.setAutorLibro(cursorLibros.getString(2));
            libros.setCantidadLibro(cursorLibros.getString(3));
        }

        cursorLibros.close();

        return libros;
    }

    public boolean editarLibroUsuario(Libros libros) {

        boolean correcto = false;

        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();

        try {
            sql.execSQL("UPDATE " + TABLE_USER + " SET TituloLibro = '" + libros.getNombreLibro()  + "', autor = '" + libros.getAutorLibro() + "', paginaLibros = '" + libros.getCantidadLibro() + "' WHERE id='" + libros.getId() + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            sql.close();
        }

        return correcto;
    }

    public boolean eliminarLibroUsuario(Libros libros) {

        boolean correcto = false;

        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();

        try {
            sql.execSQL("DELETE FROM " + TABLE_USER + " WHERE id = '" + libros.getId() + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            sql.close();
        }

        return correcto;
    }
}

