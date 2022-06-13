package com.activity.libreria.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//tenemos que realizar el extends SQLiteOpenHelper y creamos constructor y metodos

public class BDHelper extends SQLiteOpenHelper {

    //creamos el contexto en private

    private Context context;

    //Creamos la Base de Datos aqui es la parte inicial donde vamos a guardar los archivos
    private static final String DATABASE_NAME = "Biblioteca.db";
    private static final int DATABASE_VERSION = 1;

    //Creamos las Tablas que vamos a utilizar en nuestro proyecto
    //Tabla Libros
    public static final String TABLE_LIBROS = "Libros";
    public static final String COLUMN_LIBRO_ID = "_id";
    public static final String COLUMN_LIBRO_NOMBRE = "Titulo_libro";
    public static final String COLUMN_LIBRO_AUTOR = "Autor_libro";
    public static final String COLUMN_LIBRO_CANTIDAD = "Cantidad_libro";
    public static final String COLUMN_LIBRO_URL = "Url_libro";
    public static final String COLUMN_LIBRO_IMAGEN = "Imagen_libro";
    public static final String COLUMN_LIBRO_DESCRIPCION = "Descripcion_libro";



    //Tabla Libros Prestados
    public static final String TABLE_LIBROS_PRESTADOS = "Libros_Prestados";
    public static final String COLUMN_LIBRO_PRESTADOS_ID = "_id";
    public static final String COLUMN_LIBRO_PRESTADOS_LIBRO_ID = "_id_Libro";
    public static final String COLUMN_LIBRO_PRESTADOS_NOMBRE = "Titulo_libro_Prestado";
    public static final String COLUMN_LIBRO_PRESTADOS_AUTOR = "Autor_libro_Prestado";
    public static final String COLUMN_LIBRO_PRESTADOS_URL = "Url_libro_Prestado";
    public static final String COLUMN_LIBRO_PRESTADOS_IMAGEN = "Imagen_libro_Prestado";
    public static final String COLUMN_LIBRO_PRESTADOS_DESCRIPCION = "Descripcion_libro_Prestado";
    public static final String COLUMN_LIBRO_PRESTADOS_FECHA = "Fecha_Prestamo_libro";
    public static final String COLUMN_LIBRO_PRESTADOS_CORREO_PRESTO = "Correo_Prestamo_libro";
    public static final String COLUMN_LIBRO_PRESTADOS_NOMBRE_PRESTO = "Nombre_Usuario_Prestamo_libro";
    public static final String COLUMN_LIBRO_PRESTADOS_TELEFONO_PRESTO = "Telefono_Usuario_Prestamo_libro";



    //Tabla Usuario
    public static final String TABLE_USER = "Usuario";
    public static final String COLUMN_USUARIO_ID = "_id";
    public static final String COLUMN_USUARIO_NOMBRE = "Nombre_Usuario";
    public static final String COLUMN_USUARIO_CORREO = "CorreoElectronico_Usuario";
    public static final String COLUMN_USUARIO_TELEFONO = "Telefono_Usuario";
    public static final String COLUMN_USUARIO_DIRECCION = "Direccion_Usuario";
    public static final String COLUMN_USUARIO_CONTRASENA = "Contrasena_Usuario";
    //Tabla Administrador
    public static final String TABLE_ADMIN = "Administrador";
    public static final String COLUMN_ADMIN_ID = "_id";
    public static final String COLUMN_ADMIN_NOMBRE = "Nombre_Administrador";
    public static final String COLUMNA_ADMIN_CORREO = "CorreoElectronico_Administrador";
    public static final String COLUMN_ADMIN_CONTRASENA = "Contrasena_Administrador";


    public BDHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //le pasamos el contexto aqui
        this.context = context;
    }

    @Override
    //Aqui creamos la tablas en la base de Datos
    public void onCreate(SQLiteDatabase bd) {
        String TablaUsuario = " CREATE TABLE " + TABLE_USER +
                " (" + COLUMN_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_NOMBRE + " TEXT, " +
                COLUMN_USUARIO_CORREO + " TEXT, " +
                COLUMN_USUARIO_TELEFONO + " TEXT, " +
                COLUMN_USUARIO_DIRECCION + " TEXT, " +
                COLUMN_USUARIO_CONTRASENA + " TEXT); ";
        //Aqui usamos ese String con un modelo de SQL
        bd.execSQL(TablaUsuario);
        String TablaLibro = " CREATE TABLE " + TABLE_LIBROS +
                " (" + COLUMN_LIBRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIBRO_NOMBRE + " TEXT, " +
                COLUMN_LIBRO_AUTOR + " TEXT, " +
                COLUMN_LIBRO_CANTIDAD + " TEXT, " +
                COLUMN_LIBRO_URL + " TEXT, " +
                COLUMN_LIBRO_IMAGEN + " TEXT, " +
                COLUMN_LIBRO_DESCRIPCION + " TEXT); ";
        //Aqui usamos ese String con un modelo de SQL
        bd.execSQL(TablaLibro);
        String TablaLibroPrestado = " CREATE TABLE " + TABLE_LIBROS_PRESTADOS +
                " (" + COLUMN_LIBRO_PRESTADOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIBRO_PRESTADOS_LIBRO_ID + " TEXT, " +
                COLUMN_LIBRO_PRESTADOS_NOMBRE + " TEXT, " +
                COLUMN_LIBRO_PRESTADOS_AUTOR + " TEXT, " +
                COLUMN_LIBRO_PRESTADOS_URL + " TEXT, " +
                COLUMN_LIBRO_PRESTADOS_IMAGEN + " TEXT, " +
                COLUMN_LIBRO_PRESTADOS_DESCRIPCION + " TEXT, " +
                COLUMN_LIBRO_PRESTADOS_FECHA + " TEXT, " +
                COLUMN_LIBRO_PRESTADOS_CORREO_PRESTO + " TEXT, " +
                COLUMN_LIBRO_PRESTADOS_NOMBRE_PRESTO + " TEXT, " +
                COLUMN_LIBRO_PRESTADOS_TELEFONO_PRESTO + " TEXT); ";
        //Aqui usamos ese String con un modelo de SQL
        bd.execSQL(TablaLibroPrestado);
        String TablaAdministrador = " CREATE TABLE " + TABLE_ADMIN +
                " (" + COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADMIN_NOMBRE + " TEXT, " +
                COLUMNA_ADMIN_CORREO + " TEXT, " +
                COLUMN_ADMIN_CONTRASENA + " TEXT); ";
        //Aqui usamos ese String con un modelo de SQL
        bd.execSQL(TablaAdministrador);
    }

    @Override
    //Aqui utilizamos igualmente la Base de datos
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {
        bd.execSQL(" DROP TABLE IF EXISTS " + TABLE_LIBROS);
        onCreate(bd);

    }

    public Cursor leerTodosDatos() {
        return leerTodosDatos();
    }
}
