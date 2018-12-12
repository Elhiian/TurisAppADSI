package com.example.elhiian.turisappadsi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.elhiian.turisappadsi.Clases.Configuracion;

public class SplashActivity extends AppCompatActivity {

    LinearLayout layoutNombre,layoutLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Configuracion.fragment=null;

        layoutLogo=findViewById(R.id.layoutLogo);
        layoutNombre=findViewById(R.id.layoutNombre);

        layoutLogo.setAnimation(AnimationUtils.loadAnimation(this,R.anim.arriba_hacia_abajo));
        layoutNombre.setAnimation(AnimationUtils.loadAnimation(this,R.anim.abajo_hacia_arriba));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED){
                    //abrir modal para pedir permisos al usuario
                    ActivityCompat.requestPermissions(SplashActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);

                }else{
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==0){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                finish();
            }
        }

    }
}


