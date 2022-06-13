package com.activity.libreria.metodos;

import static com.activity.libreria.bd.BDHelper.COLUMN_LIBRO_PRESTADOS_CORREO_PRESTO;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_TELEFONO;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_CONTRASENA;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_CORREO;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_DIRECCION;
import static com.activity.libreria.bd.BDHelper.COLUMN_USUARIO_NOMBRE;
import static com.activity.libreria.bd.BDHelper.TABLE_LIBROS_PRESTADOS;
import static com.activity.libreria.bd.BDHelper.TABLE_USER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.activity.libreria.bd.BDHelper;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.Usuario;

import java.util.ArrayList;

public class MetodosUsuario {

    ArrayList<Usuario> lista;
    Context context;
    Usuario usuario;
    SQLiteDatabase sql;
    BDHelper bdHelper;

    public MetodosUsuario(Context context) {
        this.context = context;
        usuario = new Usuario();
    }



    public Usuario traerDatos(){
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();
        SPreferences sPreferences = new SPreferences(context);

        Cursor cursorUsuarios;


        try {
            cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USUARIO_CORREO + " = '" + sPreferences.getSharedPreference() + "' ", null);

            if (cursorUsuarios.moveToFirst()) {
                usuario = new Usuario();
                usuario.setNombreUsuario(cursorUsuarios.getString(1));
                usuario.setTelefonoUsuario(cursorUsuarios.getString(3));

            }

            cursorUsuarios.close();

            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return usuario;
        }
    }





    public long registrarUsuario(Usuario usuario) {
        long id = 0;

        try (BDHelper bdHelper = new BDHelper(context)) {
            SQLiteDatabase db = bdHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USUARIO_NOMBRE, usuario.getNombreUsuario());
            values.put(COLUMN_USUARIO_CORREO, usuario.getCorreoUsuario());
            values.put(COLUMN_USUARIO_TELEFONO, usuario.getTelefonoUsuario());
            values.put(COLUMN_USUARIO_DIRECCION, usuario.getDireccionUsuario());
            values.put(COLUMN_USUARIO_CONTRASENA, usuario.getContraseñaUsuario());
            id = db.insert(TABLE_USER, null, values);

        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }


//    public int buscar(String correo){
//        int x = 0;
//        lista = selectUsuarios();
//        for (Usuario usuario : lista){
//            if (usuario.getCorreoUsuario().equals(correo)){
//                x++;
//            }
//        }
//        return x;
//    }

    public ArrayList<Usuario> almacenarDatosEnArraysUsuario() {

        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        SPreferences sPreferences;
        sPreferences = new SPreferences(context);
        Cursor cursor;
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USUARIO_CORREO + " = '" + sPreferences.getSharedPreference() + "' ", null);


        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setNombreUsuario(cursor.getString(1));
                usuario.setCorreoUsuario(cursor.getString(2));
                usuario.setTelefonoUsuario(cursor.getString(3));
                usuario.setDireccionUsuario(cursor.getString(4));
                usuario.setContraseñaUsuario(cursor.getString(5));

                listaUsuario.add(usuario);
            }

        }
        return listaUsuario;
    }



    public ArrayList<Usuario> almacenarDatosEnArraysUsuarioAdmin() {

        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        SPreferences sPreferences;
        sPreferences = new SPreferences(context);
        Cursor cursor;
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM " + TABLE_USER, null);


        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setNombreUsuario(cursor.getString(1));
                usuario.setCorreoUsuario(cursor.getString(2));
                usuario.setTelefonoUsuario(cursor.getString(3));
                usuario.setDireccionUsuario(cursor.getString(4));
                usuario.setContraseñaUsuario(cursor.getString(5));

                listaUsuario.add(usuario);
            }

        }
        return listaUsuario;
    }


    public ArrayList<Usuario> selectUsuarios() {
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();
        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        listaUsuario.clear();
        Cursor cursor = db.rawQuery("select * from " + TABLE_USER, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setNombreUsuario(cursor.getString(1));
                usuario.setCorreoUsuario(cursor.getString(2));
                usuario.setTelefonoUsuario(cursor.getString(3));
                usuario.setDireccionUsuario(cursor.getString(4));
                usuario.setContraseñaUsuario(cursor.getString(5));
                listaUsuario.add(usuario);

            } while (cursor.moveToNext());
        }
        return listaUsuario;
    }

    public int login(Usuario usuario) {
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase db = bdHelper.getWritableDatabase();
        int a = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM  " + TABLE_USER, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                if (cursor.getString(2).equals(usuario.getCorreoUsuario()) && cursor.getString(5).equals(usuario.getContraseñaUsuario())) {
                    a++;
                }
            } while (cursor.moveToNext());
        }
        return a;
    }

    public Usuario getUsuarioById(int id) {
        ArrayList<Usuario> listaUsuario = selectUsuarios();
        for (Usuario usuario : listaUsuario) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario getUsuario(Usuario usuario) {
        lista = selectUsuarios();
        for (Usuario usuario1 : lista) {
            if (usuario1.getCorreoUsuario().equals(usuario.getCorreoUsuario()) && usuario1.getContraseñaUsuario().equals(usuario.getContraseñaUsuario())) {
                return usuario;
            }
        }
        return null;
    }

    public boolean contieneSoloLetras(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            //Si no está a y z, ni entre A y Z, ni es un espacio
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ' || c == 'ñ' || c == 'Ñ'
                    || c == 'á' || c == 'é' || c == 'í' || c == 'ó' || c == 'ú'
                    || c == 'Á' || c == 'É' || c == 'Í' || c == 'Ó' || c == 'Ú')) {
                return false;
            }
        }
        return true;
    }

    public boolean validarCorreo(Usuario usuario) {
        if (usuario.getCorreoUsuario().indexOf("@") != -1 && (usuario.getCorreoUsuario().length() == 1)) {
            Toast.makeText(context.getApplicationContext(), "Correo no debe empezar con @",
                    Toast.LENGTH_LONG).show(); //Mostrando un mensaje de error / alerta
            return false;
        } else {
            //Creo un patron
            // Pattern p = Pattern.compile("[0 - 9]");
            return true;
        }
    }

    public boolean validarTelefono(String telefono) {
        if (telefono.length() != 10) {
            return false;
        } else {
            for (int x = 0; x < telefono.length(); x++) {
                char c = telefono.charAt(x);
                //Si el primero no es 5
//                if (x == 5 && c != '5') {
//                    return false;
//                }
//                //Si el segundo no es 7
//                if (x == 7 && c != '7') {
//                    return false;
//                }
                //Si el resto no tiene numero
                if (!(c >= '0' && c <= '9')) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean validarContraseña(String contraseña) {
        boolean numeros = false;
        boolean letras = false;
        for (int x = 0; x < usuario.getContraseñaUsuario().length(); x++) {
            char c = usuario.getContraseñaUsuario().charAt(x);
            // Si no esta entre a y z, ni entre A y Z, ni es un espacio
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ' || c == 'ñ' || c == 'Ñ'
                    || c == 'á' || c == 'é' || c == 'í' || c == 'ó' || c == 'ú'
                    || c == 'Á' || c == 'É' || c == 'Í' || c == 'Ó' || c == 'Ú')) {
                letras = true;
            }
            if ((c >= '0' && c <= '9')) {
                numeros = true;
            }
        }
        if (numeros == true && letras == true) {
            return true;
        }
        return false;
    }

    //crear un metodo que me busque el la tabla libros prestados los correos que correspondan al id del libro
    //Otro metodo que me busque en la tabla de usuarios, los datos que correspondan al correo


    public ArrayList<Usuario> correoPrestado(int id) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        Cursor cursor;
        Usuario usuario = null;
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM " + TABLE_LIBROS_PRESTADOS + " WHERE _id_Libro " +" GROUP BY " + COLUMN_LIBRO_PRESTADOS_CORREO_PRESTO +"" , null);

        if (cursor.moveToFirst()) {
            do {
             //   listaUsuarios.add(correoUsuarioPrestado(String.valueOf(cursor.getColumnIndex(COLUMN_LIBRO_PRESTADOS_CORREO_PRESTO))));
                listaUsuarios.add(correoUsuarioPrestado( cursor.getString(8)));
            } while (cursor.moveToNext());

        }
        cursor.close();

    return listaUsuarios;
    }

    public Usuario correoUsuarioPrestado(String correo) {

        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        Cursor cursor;
        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase sql = bdHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE  CorreoElectronico_Usuario  = '" + correo + "'"  ,null);

        Usuario usuario = new Usuario();
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No hay datos", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                usuario.setId(cursor.getInt(0));
                usuario.setNombreUsuario(cursor.getString(1));
                usuario.setCorreoUsuario(cursor.getString(2));
                usuario.setTelefonoUsuario(cursor.getString(3));
            }

        }
        return usuario;
    }




}

