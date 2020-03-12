package com.example.katundu.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.katundu.R;

public class RegisterActivity extends AppCompatActivity {

    //de momento asi pero hay que hacerle una clase especial
    //o poner el codigo necesario dentro de esta clase
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();

        //No funciona porque no entiendo como funciona el original, pero no peta :)
        final EditText passwordEditText = findViewById(R.id.password1);



        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerViewModel.isPasswordValid(passwordEditText.getText().toString());
                }
                return false;
            }
        });
    }
}

