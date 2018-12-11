package com.example.elhiian.turisappadsi;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.elhiian.turisappadsi.Clases.Sitios;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class UbicacionesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Sitios> listaSitios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listaSitios= (ArrayList<Sitios>) getIntent().getSerializableExtra("lista");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        cargarUbicaciones(mMap);

    }

    private void cargarUbicaciones(GoogleMap mapa) {
        for (int i=0; i<listaSitios.size(); i++){
            LatLng coordenadas=new LatLng(Double.parseDouble(listaSitios.get(i).getLatitud())  ,   Double.parseDouble(listaSitios.get(i).getLongitud()));
            mapa.addMarker(new MarkerOptions().position(coordenadas).title(listaSitios.get(i).getNombre()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas,10));
        }

    }
}
