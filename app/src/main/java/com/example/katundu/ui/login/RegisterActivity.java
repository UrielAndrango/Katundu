package com.example.katundu.ui.login;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;

public class RegisterActivity extends AppCompatActivity {

    //de momento asi pero hay que hacerle una clase especial
    //o poner el codigo necesario dentro de esta clase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();

        //No funciona porque no entiendo como funciona el original, pero no peta :)
        final EditText passwordEditText = findViewById(R.id.password1);
    }
}

