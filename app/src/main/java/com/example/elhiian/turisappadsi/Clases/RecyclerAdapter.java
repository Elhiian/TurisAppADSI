package com.example.elhiian.turisappadsi.Clases;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elhiian.turisappadsi.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    ArrayList<Sitios> listaSitios;

    public RecyclerAdapter(ArrayList<Sitios> listaSitios) {
        this.listaSitios = listaSitios;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_lista_sitios,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return listaSitios.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView listaImagen;
        TextView listaNombre,listaDescripcion,listaDireccion;

        public Holder(@NonNull View itemView) {
            super(itemView);
            listaImagen=itemView.findViewById(R.id.listaImagen);
            listaNombre=itemView.findViewById(R.id.listaNombre);
            listaDescripcion=itemView.findViewById(R.id.listaDescripcion);
            listaDireccion=itemView.findViewById(R.id.listaDireccion);
        }
    }
}
