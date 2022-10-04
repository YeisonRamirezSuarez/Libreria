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

import com.activity.libreria.ActualizarLibros;
import com.activity.libreria.R;
import com.activity.libreria.VerMiLibro;
import com.activity.libreria.modelos.Libros;
import com.activity.libreria.modelos.LibrosPrestadosRsp;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterUsuarioLibroItems extends RecyclerView.Adapter<AdapterUsuarioLibroItems.MyViewHolder> {
    //Creamos el proceso para que identifique el la vista de Mi fila
    private Context context;
    private ArrayList<LibrosPrestadosRsp> listaLibros;
    private ArrayList<LibrosPrestadosRsp> listaOriginal;


    public AdapterUsuarioLibroItems(Context context, ArrayList<LibrosPrestadosRsp> listaLibros) {
        this.context = context;
        this.listaLibros = listaLibros;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaLibros);

        //listaOriginal.addAll(listaLibros);
    }

    //Aqui agregamos la vista
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mi_item_libros_usuario_prestado, parent, false);
        return new MyViewHolder(view);
    }

    //Aqui diferenciamos entre el que guardamos y lo que traeremos en la Vista de mi fila
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Cargamos la imagen
        Glide.with(context)
                .load(listaLibros.get(position).getImagen_libro_Prestado())
                .error(R.drawable.error)
                .into(holder.imageView_txt_prestado);

        holder.nombre_libro_txt_prestado.setText(String.valueOf(listaLibros.get(position).getTitulo_libro_Prestado()));
        holder.autor_libro_txt_prestado.setText(String.valueOf(listaLibros.get(position).getAutor_libro_Prestado()));
        holder.fecha_prestamo_libro_txt_prestado.setText(String.valueOf(listaLibros.get(position).getFecha_Prestamo_libro()));

    }

   /* public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaLibros.clear();
            listaLibros.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Libros> collecion = listaLibros.stream()
                        .filter(i -> i.getNombre_Usuario_Prestamo_libro().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaLibros.clear();
                listaLibros.addAll(collecion);
            } else {
                for (Libros libros : listaOriginal) {
                    if (libros.getNombreLibro().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaLibros.add(libros);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

    //Aqui igualamos los dos valores y los llamamos con ItemView
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nombre_libro_txt_prestado;
        TextView autor_libro_txt_prestado;
        TextView fecha_prestamo_libro_txt_prestado;
        ImageView imageView_txt_prestado;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_libro_txt_prestado = itemView.findViewById(R.id.nombre_libro_txt_prestado);
            autor_libro_txt_prestado = itemView.findViewById(R.id.autor_libro_txt_prestado);
            fecha_prestamo_libro_txt_prestado = itemView.findViewById(R.id.fecha_prestamo_libro_txt_prestado);
            imageView_txt_prestado = itemView.findViewById(R.id.imageView_txt_prestado);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, VerMiLibro.class);
                        intent.putExtra("ID", listaLibros.get(getAdapterPosition()).get_id());
                        context.startActivity(intent);
                }
            });


        }
    }
}
