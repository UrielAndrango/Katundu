package com.example.katundu.ui.logged;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;


public class LlistesUsuari extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistes_usuari);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final Button PerfilButton = findViewById(R.id.nomusuari);

        PerfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LlistesUsuari.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

