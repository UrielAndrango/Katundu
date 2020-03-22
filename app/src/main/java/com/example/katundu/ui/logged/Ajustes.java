package com.example.katundu.ui.logged;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.login.LoginActivity;

public class Ajustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final ImageView Atras = findViewById(R.id.Ajustes_Atras);
        final Button ModificarPerfil = findViewById(R.id.modificar_perfil);
        final Button Logout = findViewById(R.id.logout);
        //modificar estos botones, esto es solo provisional
        final Button deleteAccount = findViewById(R.id.deleteaccount);
        final EditText usernameEditText = findViewById(R.id.editTextNomUsuari);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ajustes.this, User.class);
                startActivity(intent);
                finish();
            }
        });

        ModificarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ajustes.this, EditarPerfil.class);
                startActivity(intent);
                finish();
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FALTA HACER LOGOUT DE VERDAD
                Intent intent = new Intent(Ajustes.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Ajustes.this);

                String url = "https://us-central1-test-8ea8f.cloudfunctions.net/deleteaccount?" +
                        "un=" + usernameEditText.getText();

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("Account deleted successfully")) {
                                    String account_deleted_successfully = getString(R.string.account_deleted_successfully);
                                    Toast toast = Toast.makeText(getApplicationContext(), account_deleted_successfully, Toast.LENGTH_SHORT);
                                    toast.show();

                                    Intent intent = new Intent(Ajustes.this, LoginActivity.class);
                                    startActivity(intent);
                                    //finish();
                                }
                                else { //response == "Something went wrong"
                                    String texterror = getString(R.string.error);
                                    Toast toast = Toast.makeText(Ajustes.this, texterror, Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {  //TODO: aixo ho podem treure?
                        String texterror = getString(R.string.error);
                        Toast toast = Toast.makeText(Ajustes.this, texterror, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}
