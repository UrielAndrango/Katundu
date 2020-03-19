package com.example.katundu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;
import com.example.katundu.ui.logged.MenuPrincipal;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private MenuPrincipal menuPrincipal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Oculta la barra que dice el nombre de la apliacion en la Action Bar (asi de momento)
        getSupportActionBar().hide();
        //Firebase Anlytics de prueba
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button login = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView no_registrado = findViewById(R.id.no_registrado);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String welcome = getString(R.string.welcome) + usernameEditText.getText().toString();
                Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, MenuPrincipal.class);
                startActivity(intent);
                finish();
            }
        });

        no_registrado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Bundle bundle = new Bundle();
        //bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}

/*
private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
 */
