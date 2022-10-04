package com.activity.libreria.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.HistorialUsuarioLibros;
import com.activity.libreria.R;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.LibrosPrestadosRsp;
import com.activity.libreria.modelos.ListaLibrosPrestados;
import com.activity.libreria.modelos.Usuario;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterAdministradorHistorialLibroPrestadoItems extends RecyclerView.Adapter<AdapterAdministradorHistorialLibroPrestadoItems.MyViewHolder> {
    //Creamos el proceso para que identifique el la vista de Mi fila
    private Context context;
    private ArrayList<LibrosPrestadosRsp> listaUsuario;



    public AdapterAdministradorHistorialLibroPrestadoItems(Context context, ArrayList<LibrosPrestadosRsp> listaUsuario) {
        this.context = context;
        this.listaUsuario = listaUsuario;
    }

    //Aqui agregamos la vista
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mi_item_historial_libros_usuario_prestado_card, parent, false);
        return new MyViewHolder(view);
    }

    //Aqui diferenciamos entre el que guardamos y lo que traeremos en la Vista de mi fila
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nombre_usuario_historial_view.setText(String.valueOf(listaUsuario.get(position).getNombre_Usuario_Prestamo_libro()));
        holder.telefono_historial_view.setText(String.valueOf(listaUsuario.get(position).getTelefono_Usuario_Prestamo_libro()));
        holder.correo_historial_view.setText(String.valueOf(listaUsuario.get(position).getCorreo_Prestamo_libro()));

    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    //Aqui igualamos los dos valores y los llamamos con ItemView
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nombre_usuario_historial_view;
        TextView telefono_historial_view;
        TextView correo_historial_view;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_usuario_historial_view = itemView.findViewById(R.id.nombre_usuario_historial_view);
            telefono_historial_view = itemView.findViewById(R.id.telefono_historial_view);
            correo_historial_view = itemView.findViewById(R.id.correo_historial_view);




        }
    }
}
