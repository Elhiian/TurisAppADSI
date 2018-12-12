package com.example.elhiian.turisappadsi.Clases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elhiian.turisappadsi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> implements View.OnClickListener {
    ArrayList<Sitios> listaSitios;
    Context context;
    View.OnClickListener click;
    boolean isList;

    public RecyclerAdapter(ArrayList<Sitios> listaSitios, Context contex, boolean isList) {
        this.listaSitios = listaSitios;
        this.context=contex;
        this.isList=isList;
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        if (isList==true){
            view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_lista_sitios,viewGroup,false);
        }else{
            view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_lista_sitios_grid,viewGroup,false);
        }
        view.setOnClickListener(this);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.listaNombre.setText(listaSitios.get(i).getNombre());
        holder.listaDireccion.setText(listaSitios.get(i).getUbicacion());
        Picasso.with(context).load("http://turisapp.esy.es/"+listaSitios.get(i).getFoto()).into(holder.listaImagen);
        if (isList==true){
            holder.listaDescripcion.setText(listaSitios.get(i).getDescripcioncorta());
        }

    }

    @Override
    public int getItemCount() {
        return listaSitios.size();
    }

    @Override
    public void onClick(View v) {
        if (click!=null){
            click.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.click=listener;
    }


    public class Holder extends RecyclerView.ViewHolder {
        ImageView listaImagen;
        TextView listaNombre,listaDescripcion,listaDireccion;

        public Holder(@NonNull View itemView) {
            super(itemView);
            if (isList==true){
                listaImagen=itemView.findViewById(R.id.listaImagen);
                listaNombre=itemView.findViewById(R.id.listaNombre);
                listaDescripcion=itemView.findViewById(R.id.listaDescripcion);
                listaDireccion=itemView.findViewById(R.id.listaDireccion);
            }else{
                listaImagen=itemView.findViewById(R.id.gridImagen);
                listaNombre=itemView.findViewById(R.id.gridNombre);
                listaDireccion=itemView.findViewById(R.id.gridDireccion);
            }
        }
    }
}
