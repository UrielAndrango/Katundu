package com.example.katundu.ui.logged;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;
import com.example.katundu.ui.ControladoraPresentacio;


public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final TextView NomUsuari = findViewById(R.id.nomUsuari);
        final ImageView ImgSettings = findViewById(R.id.img_settings);
        final ImageView Atras = findViewById(R.id.User_Atras);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, MenuPrincipal.class);
                startActivity(intent);
                finish();
            }
        });

        //Esto se descomentara si sabemos volver a atras de forma "inteligente"
        /*
        NomUsuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, EditarPerfil.class);
                startActivity(intent);
                finish();
            }
        });
*/
        ImgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, Ajustes.class);
                startActivity(intent);
                finish();
            }
        });

        //USERNAME DEL USUARIO
        NomUsuari.setText(ControladoraPresentacio.getUsername());
    }
}

