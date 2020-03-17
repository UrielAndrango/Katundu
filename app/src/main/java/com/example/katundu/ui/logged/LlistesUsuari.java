package com.example.katundu.ui.logged;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;


public class LlistesUsuari extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistes_usuari);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final Button NomUsuari = findViewById(R.id.nomusuari);
        final ImageView ImgPerfil = findViewById(R.id.img_perfil2);

        NomUsuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LlistesUsuari.this, EditarPerfil.class);
                startActivity(intent);
                finish();
            }
        });

        ImgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LlistesUsuari.this, MenuPrincipal.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

