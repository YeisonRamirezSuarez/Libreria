package com.activity.libreria.metodos;

import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_AUTOR;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_DESCRIPCION;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_ID;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_IMAGEN;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_CANTIDAD;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_NOMBRE;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_AUTOR;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_CORREO_PRESTO;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_DESCRIPCION;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_FECHA;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_ID;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_IMAGEN;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_LIBRO_ID;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_NOMBRE;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_NOMBRE_PRESTO;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_TELEFONO_PRESTO;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_URL;
import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_URL;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_CORREO;
import static com.activity.libreria.bd.BDHelper.TABLE_LIBROS;
import static com.activity.libreria.bd.BDHelper.TABLE_LIBROS_PRESTADOS;
import static com.activity.libreria.bd.BDHelper.TABLE_USER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.activity.libreria.LibrosPrestadosAdmin;
import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.Usuario;

import java.util.ArrayList;

public class MetodosLibros {
    Context context;

    public MetodosLibros(Context context) {
        this.context = context;
    }

    BDHelper bdHelper;

    //ArrayLis Libros Prestado por el Usuario

    public ArrayList<Libros> ArraysLibrosPrestados() {

        SPreferences sPreferences;
        sPreferences = new SPreferences(context);

        ArrayList<Libros> listaLibros = new ArrayList<>();
        Cursor cursor;
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM " + TABLE_LIBROS_PRESTADOS + " WHERE " + COLUMN_LIBRO_PRESTADOS_CORREO_PRESTO + " = '" + sPreferences.getSharedPreference() + "' ", null);


        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Libros libros = new Libros();
                libros.setId(cursor.getInt(0));
                libros.setId_libro(cursor.getInt(1));
                libros.setNombreLibro(cursor.getString(2));
                libros.setAutorLibro(cursor.getString(3));
                libros.setUrlLibro(cursor.getString(4));
                libros.setUrlImagen(cursor.getString(5));
                libros.setDescripcion(cursor.getString(6));
                libros.setFechaPrestamo(cursor.getString(7));
                libros.setCorreoPrestamo(cursor.getString(8));
                libros.setNombrePrestamo(cursor.getString(9));
                libros.setTelefonoPrestamo(cursor.getString(10));

                listaLibros.add(libros);
            }

        }
        return listaLibros;
    }

    public ArrayList<Libros> ArraysLibrosPrestadosAdmin() {

        SPreferences sPreferences;
        sPreferences = new SPreferences(context);

        ArrayList<Libros> listaLibros = new ArrayList<>();
        Cursor cursor;
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM " + TABLE_LIBROS_PRESTADOS + " GROUP BY " + COLUMN_LIBRO_PRESTADOS_LIBRO_ID, null);


        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Libros libros = new Libros();
                libros.setId(cursor.getInt(0));
                libros.setId_libro(cursor.getInt(1));
                libros.setNombreLibro(cursor.getString(2));
                libros.setAutorLibro(cursor.getString(3));
                libros.setUrlLibro(cursor.getString(4));
                libros.setUrlImagen(cursor.getString(5));
                libros.setDescripcion(cursor.getString(6));
                libros.setFechaPrestamo(cursor.getString(7));
                libros.setCorreoPrestamo(cursor.getString(8));
                libros.setNombrePrestamo(cursor.getString(9));
                libros.setTelefonoPrestamo(cursor.getString(10));

                listaLibros.add(libros);
            }

        }
        return listaLibros;
    }

    public ArrayList<Libros> ArraysLibrosPre() {

        SPreferences sPreferences;
        sPreferences = new SPreferences(context);

        ArrayList<Libros> listaLibros = new ArrayList<>();
        Cursor cursor;
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM " + TABLE_LIBROS_PRESTADOS, null);


        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Libros libros = new Libros();
                libros.setId(cursor.getInt(0));
                libros.setId_libro(cursor.getInt(1));
                libros.setNombreLibro(cursor.getString(2));
                libros.setAutorLibro(cursor.getString(3));
                libros.setUrlLibro(cursor.getString(4));
                libros.setUrlImagen(cursor.getString(5));
                libros.setDescripcion(cursor.getString(6));
                libros.setFechaPrestamo(cursor.getString(7));
                libros.setCorreoPrestamo(cursor.getString(8));
                libros.setNombrePrestamo(cursor.getString(9));
                libros.setTelefonoPrestamo(cursor.getString(10));

                listaLibros.add(libros);
            }

        }
        return listaLibros;
    }


    public ArrayList<Libros> almacenarDatosEnArrays() {

        ArrayList<Libros> listaLibros = new ArrayList<>();
        Cursor cursor;
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();
        cursor = sql.rawQuery("select * from " + TABLE_LIBROS, null);


        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Libros libros = new Libros();
                libros.setId(cursor.getInt(0));
                libros.setNombreLibro(cursor.getString(1));
                libros.setAutorLibro(cursor.getString(2));
                libros.setCantidadLibro(cursor.getString(3));
                libros.setUrlLibro(cursor.getString(4));
                libros.setUrlImagen(cursor.getString(5));
                libros.setDescripcion(cursor.getString(6));

                listaLibros.add(libros);
            }

        }
        return listaLibros;
    }

    //Aqui Creamos para Prestar Libros

    public void prestarLibro(Libros libros, Usuario usuario) {
        SPreferences sPreferences;
        sPreferences = new SPreferences(context);
        try (BDHelper bdHelper = new BDHelper(context);
        ) {
            SQLiteDatabase bd = bdHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_LIBRO_PRESTADOS_LIBRO_ID, libros.getId_libro());
            contentValues.put(COLUMN_LIBRO_PRESTADOS_NOMBRE, libros.getNombreLibro());
            contentValues.put(COLUMN_LIBRO_PRESTADOS_AUTOR, libros.getAutorLibro());
            contentValues.put(COLUMN_LIBRO_PRESTADOS_URL, libros.getUrlLibro());
            contentValues.put(COLUMN_LIBRO_PRESTADOS_IMAGEN, libros.getUrlImagen());
            contentValues.put(COLUMN_LIBRO_PRESTADOS_DESCRIPCION, libros.getDescripcion());
            contentValues.put(COLUMN_LIBRO_PRESTADOS_FECHA, libros.getFechaPrestamo());
            contentValues.put(COLUMN_LIBRO_PRESTADOS_CORREO_PRESTO, sPreferences.getSharedPreference());
            contentValues.put(COLUMN_LIBRO_PRESTADOS_NOMBRE_PRESTO, usuario.getNombreUsuario());
            contentValues.put(COLUMN_LIBRO_PRESTADOS_TELEFONO_PRESTO, usuario.getTelefonoUsuario());


            //Agregamos a la base de datos los valores
            long resultado = bd.insert(TABLE_LIBROS_PRESTADOS, null, contentValues);

            if (resultado <= 0) {
                Toast.makeText(context, "Fallo el proceso de guardado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Agregado correctamente", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Aqui Creamos para Agregar libros
    public boolean guardarLibro(Libros libros) {

        try (BDHelper bdHelper = new BDHelper(context);
        ) {
            SQLiteDatabase bd = bdHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_LIBRO_NOMBRE, libros.getNombreLibro());
            contentValues.put(COLUMN_LIBRO_AUTOR, libros.getAutorLibro());
            contentValues.put(COLUMN_LIBRO_CANTIDAD, libros.getCantidadLibro());
            contentValues.put(COLUMN_LIBRO_URL, libros.getUrlLibro());
            contentValues.put(COLUMN_LIBRO_IMAGEN, libros.getUrlImagen());
            contentValues.put(COLUMN_LIBRO_DESCRIPCION, libros.getDescripcion());


            //Agregamos a la base de datos los valores
            long resultado = bd.insert(TABLE_LIBROS, null, contentValues);

            if (resultado <= 0) {
                Toast.makeText(context, "Fallo el proceso de guardado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Agregado correctamente", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }


    //Aqui creamos para Ver
    public Libros verLibro(int id) {

        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();

        Libros libros = null;
        Cursor cursorLibro;


        try {
            cursorLibro = db.rawQuery("SELECT * FROM " + TABLE_LIBROS + " WHERE _id = " + id, null);

            if (cursorLibro.moveToFirst()) {
                libros = new Libros();
                libros.setId(cursorLibro.getInt(0));
                libros.setNombreLibro(cursorLibro.getString(1));
                libros.setAutorLibro(cursorLibro.getString(2));
                libros.setCantidadLibro(cursorLibro.getString(3));
                libros.setUrlLibro(cursorLibro.getString(4));
                libros.setUrlImagen(cursorLibro.getString(5));
                libros.setDescripcion(cursorLibro.getString(6));


            }

            cursorLibro.close();

            return libros;
        } catch (Exception e) {
            e.printStackTrace();
            return libros;
        }
    }

    //Aqui creamos para Ver
    public Libros verLibroPrestado(int id) {

        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();

        Libros libros = null;
        Cursor cursorLibro;


        try {
            cursorLibro = db.rawQuery("SELECT * FROM " + TABLE_LIBROS_PRESTADOS + " WHERE _id = " + id ,null);

            if (cursorLibro.moveToFirst()) {
                libros = new Libros();
                libros.setId(cursorLibro.getInt(0));
                libros.setId_libro(cursorLibro.getInt(1));
                libros.setNombreLibro(cursorLibro.getString(2));
                libros.setAutorLibro(cursorLibro.getString(3));
                libros.setUrlLibro(cursorLibro.getString(4));
                libros.setUrlImagen(cursorLibro.getString(5));
                libros.setDescripcion(cursorLibro.getString(6));
                libros.setFechaPrestamo(cursorLibro.getString(7));
                libros.setCorreoPrestamo(cursorLibro.getString(8));
                libros.setNombrePrestamo(cursorLibro.getString(9));
                libros.setTelefonoPrestamo(cursorLibro.getString(10));

            }

            cursorLibro.close();

            return libros;
        } catch (Exception e) {
            e.printStackTrace();
            return libros;
        }
    }

    //Editar creamos para Editar
    public boolean editarLibroPrestado(Libros libros) {

        boolean correcto = false;

        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_LIBROS_PRESTADOS + " SET Titulo_libro_Prestado = '" + libros.getNombreLibro() + "', Autor_libro_Prestado = '" + libros.getAutorLibro() + "', Url_libro_Prestado = '" + libros.getUrlLibro() + "', Imagen_libro_Prestado = '" + libros.getUrlImagen() + "', Descripcion_libro_Prestado = '" + libros.getDescripcion() + "' WHERE _id='" + libros.getId() + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }


    //Editar creamos para Editar
    public boolean editarLibro(Libros libros) {

        boolean correcto = false;

        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_LIBROS + " SET Titulo_libro = '" + libros.getNombreLibro() + "', Autor_libro = '" + libros.getAutorLibro() + "', Cantidad_libro = '" + libros.getCantidadLibro() + "', Url_libro = '" + libros.getUrlLibro() + "', Imagen_libro = '" + libros.getUrlImagen() + "', Descripcion_libro = '" + libros.getDescripcion() + "' WHERE _id='" + libros.getId() + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    //Editar creamos para actualizar cantidad libro
    public boolean actualizarCantidadLibro(Libros libros) {

        boolean correcto = false;

        BDHelper dbHelper = new BDHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_LIBROS + " SET  Cantidad_libro = '" + libros.getCantidadLibro() + "' WHERE _id='" + libros.getId() + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }


    //Aqui Agregamos para eliminar de Tabla prestados
    public boolean eliminarLibroPrestado(Libros libros) {
        boolean correcto = false;
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_LIBROS_PRESTADOS + " WHERE _id = '" + libros.getId() + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }


    //Aqui agregamos para eliminar
    public boolean eliminarLibro(Libros libros) {

        boolean correcto = false;

        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_LIBROS + " WHERE _id = '" + libros.getId() + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public Libros traerDatosCantidadd(int id) {
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();
        SPreferences sPreferences = new SPreferences(context);
        Libros libros;
        libros = new Libros();
        Cursor cursorLibros;
        String cantidad = "";

        cursorLibros = db.rawQuery("SELECT * FROM " + TABLE_LIBROS + " WHERE " + COLUMN_LIBRO_ID + " = '" + id + "' ", null);

        if (cursorLibros.moveToFirst()) {

            libros.setId(cursorLibros.getInt(0));

            libros.setCantidadLibro(cursorLibros.getString(3));

        }

        cursorLibros.close();
        return libros;
    }

    //Agregar lo de la base de datos para el RecicleView
    public Cursor leerTodosDatos() {
        try (BDHelper bdHelper = new BDHelper(context)) {

            String tablaLibros = "SELECT * FROM " + TABLE_LIBROS;
            SQLiteDatabase bd = bdHelper.getReadableDatabase();

            Cursor cursor = null;
            if (bd != null) {
                cursor = bd.rawQuery(tablaLibros, null);
            }
            return cursor;
        }

    }

}

