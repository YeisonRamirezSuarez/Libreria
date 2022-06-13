package com.activity.libreria.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.libreria.R;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.Usuario;

import java.util.ArrayList;

public class AdapterLibroIcons extends RecyclerView.Adapter<AdapterLibroIcons.MyViewHolderUsuario> {
    //Creamos el proceso para que identifique el la vista de Mi fila

    ArrayList<Libros> listaLibro;
    ArrayList<Libros> listaOriginal;

    public AdapterLibroIcons(ArrayList<Libros> listaLibro) {
        this.listaLibro = listaLibro;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaLibro);
    }

    //Aqui agregamos la vista
    @NonNull
    @Override
    public MyViewHolderUsuario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_agregar_libro, null, false);
        return new MyViewHolderUsuario(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderUsuario holder, int position) {
        holder.nombreLibroView.setText(String.valueOf(listaLibro.get(position).getNombreLibro()));
        holder.autorLibroView.setText(String.valueOf(listaLibro.get(position).getAutorLibro()));
        holder.cantidadLibroView.setText(String.valueOf(listaLibro.get(position).getCantidadLibro()));
        holder.urlLibroView.setText(String.valueOf(listaLibro.get(position).getUrlLibro()));
        holder.urlImagen_view.setText(String.valueOf(listaLibro.get(position).getUrlImagen()));
        holder.descripcion_view.setText(String.valueOf(listaLibro.get(position).getDescripcion()));

    }

    @Override
    public int getItemCount() {
        return listaLibro.size();
    }

    //Aqui igualamos los dos valores y los llamamos con ItemView
    public class MyViewHolderUsuario extends RecyclerView.ViewHolder {

        TextView nombreLibroView;
        TextView autorLibroView;
        TextView cantidadLibroView;
        TextView urlLibroView;
        TextView urlImagen_view;
        TextView descripcion_view;

        public MyViewHolderUsuario(@NonNull View itemView) {
            super(itemView);
            nombreLibroView = itemView.findViewById(R.id.nombreLibro_view);
            autorLibroView = itemView.findViewById(R.id.autorLibro_view);
            cantidadLibroView = itemView.findViewById(R.id.cantidadLibros_view);
            urlLibroView = itemView.findViewById(R.id.urlLibro_view);
            urlImagen_view = itemView.findViewById(R.id.urlImagen_view);
            descripcion_view = itemView.findViewById(R.id.descripcion_view);

        }

    }
}
