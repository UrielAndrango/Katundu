package com.example.katundu.ui.logged;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;

public class AddWish extends AppCompatActivity {
//TODO:Añadir 2h por AddWish y Add + Refactor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);
        //Escondemos la Action Bar porque usamos la ToolBar, aunque podriamos usar la ActionBar
        getSupportActionBar().hide();

        final ImageView Atras = findViewById(R.id.AddWish_Atras);
        final Button Add_Wish = findViewById(R.id.ok_button_AddWish);
        final EditText nombre = findViewById(R.id.editTextNom_AddWish);
        final EditText palabras_clave = findViewById(R.id.editTextParaulesClau_AddWish);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWish.this, Add.class);
                startActivity(intent);
                finish();
            }
        });

        Add_Wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean okay = false;
                //Comprovaciones de que ha puesto cosas
                if (nombre.length() == 0) {
                    String texterror = getString(R.string.add_product_no_hay_nombre);
                    Toast toast = Toast.makeText(AddWish.this, texterror, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (palabras_clave.length() == 0) {
                        String texterror = getString(R.string.add_product_no_hay_palabras_clave);
                        Toast toast = Toast.makeText(AddWish.this, texterror, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        okay = true;
                    }
                }
                if (okay) {
                    //Nos vamos a la ventana de User
                    Intent intent = new Intent(AddWish.this, User.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
