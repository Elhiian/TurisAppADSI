package com.example.elhiian.turisappadsi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elhiian.turisappadsi.Clases.Sitios;
import com.squareup.picasso.Picasso;

public class DetalleActivity extends AppCompatActivity {
    ImageView detalleImagen;
    TextView detalleNombre,detalleDescripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        detalleImagen=findViewById(R.id.detalleImagen);
        detalleNombre=findViewById(R.id.detalleNombre);
        detalleDescripcion=findViewById(R.id.detalleDescripcion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Sitios sitios=null;
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            sitios= (Sitios) bundle.getSerializable("sitio");
            detalleNombre.setText(sitios.getNombre());
            detalleDescripcion.setText(sitios.getDescripcion());
            Picasso.with(this).load("http://turisapp.esy.es/"+sitios.getFoto()).into(detalleImagen);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }


        return super.onOptionsItemSelected(item);

    }
}
