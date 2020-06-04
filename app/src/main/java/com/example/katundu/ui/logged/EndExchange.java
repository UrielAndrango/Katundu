package com.example.katundu.ui.logged;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.katundu.R;

public class EndExchange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_exchange);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();
    }
}
