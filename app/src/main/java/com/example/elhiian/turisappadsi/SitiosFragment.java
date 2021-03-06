package com.example.elhiian.turisappadsi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.elhiian.turisappadsi.Clases.RecyclerAdapter;
import com.example.elhiian.turisappadsi.Clases.Sitios;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SitiosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SitiosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SitiosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SitiosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SitiosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SitiosFragment newInstance(String param1, String param2) {
        SitiosFragment fragment = new SitiosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }




    RecyclerView recyclerLista;
    FloatingActionButton botonFlotante, botonCambiarVista;
    View view;
    ArrayList<Sitios> listadoSitios;
    boolean isList=true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view=inflater.inflate(R.layout.fragment_sitios, container, false);
        recyclerLista=view.findViewById(R.id.recyclerListaSitios);
        botonFlotante=view.findViewById(R.id.botonUbicacion);
        botonCambiarVista=view.findViewById(R.id.botonCambiarVista);

        botonCambiarVista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view,"Cambiando vista",Snackbar.LENGTH_LONG).show();
                if (isList==true){
                    isList=false;
                }else{
                    isList=true;
                }
                consultarLista();
            }
        });


        //implementar el click al boton flotante
        botonFlotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),UbicacionesActivity.class);  //creacion del intent

                Bundle bundle=new Bundle();  //objeto bundle en el cual añadimos los datos
                bundle.putSerializable("lista",listadoSitios);   //añadimos la lista que consultamos a el bundle
                intent.putExtras(bundle);  //agregamos el bundle a la activity
                startActivity(intent);  //iniciamos la nueva actividad
            }
        });

        consultarLista();

        return view;
    }

    private void consultarLista() {
        String accion=getArguments().getString("accion");
        String url="http://turisapp.esy.es/turisapp/informacion.php?action="+accion;

        listadoSitios=new ArrayList<>(); //arreglo de objetos

        AsyncHttpClient consulta=new AsyncHttpClient();
        consulta.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    String respuesta=new String(responseBody);
                    try {
                        //convertir el arreglo
                        JSONArray datos=new JSONArray(respuesta);

                        for (int i=0; i<datos.length(); i++){
                            Sitios sitios=new Sitios();
                            sitios.setId(datos.getJSONObject(i).getString("id"));
                            sitios.setDescripcioncorta(datos.getJSONObject(i).getString("descripcioncorta"));
                            sitios.setUbicacion(datos.getJSONObject(i).getString("ubicacion"));
                            sitios.setDescripcion(datos.getJSONObject(i).getString("descripcion"));
                            sitios.setNombre(datos.getJSONObject(i).getString("nombre"));
                            sitios.setLatitud(datos.getJSONObject(i).getString("latitud"));
                            sitios.setLongitud(datos.getJSONObject(i).getString("longitud"));
                            sitios.setFoto(datos.getJSONObject(i).getString("foto"));

                            //añadir la clase a la lista de objetos
                            listadoSitios.add(sitios);

                        }
                        //creacion del adaptador
                        RecyclerAdapter adapter=new RecyclerAdapter(listadoSitios,getActivity(),isList);

                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(getActivity(),DetalleActivity.class);
                                Bundle bundle=new Bundle();
                                Sitios sitios=listadoSitios.get(recyclerLista.getChildAdapterPosition(v));
                                bundle.putSerializable("sitio",sitios);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                        if (isList==true){
                            recyclerLista.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }else{
                            recyclerLista.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        }
                        recyclerLista.setAdapter(adapter);

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "No se pudo conectar al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
