package com.example.katundu.ui.logged;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.katundu.R;
import com.example.katundu.ui.ControladoraPresentacio;

public class VisualizeChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize_chat);

        final String username1 = ControladoraPresentacio.getUsername();
        final TextView username2 = findViewById(R.id.nomUsuari);
        final ImageView Atras = findViewById(R.id.ViewChat_Atras);
        
        username2.setText("nomUsuari"); //TODO: posar nom usuari amb qui parles (agafar de la CONTROLADORA CHAT)

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualizeChat.this, MenuPrincipal.class); //TODO: canviar pantalla MenuPrincipal per pantalla ListChats
                onNewIntent(intent);
                finish();
            }
        });

    }
}
