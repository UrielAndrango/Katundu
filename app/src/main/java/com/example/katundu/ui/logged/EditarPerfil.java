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

public class EditarPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final ImageView Atras = findViewById(R.id.DeleteAccount_Atras);
        final Button SaveButton = findViewById(R.id.save_button);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText nameEditText = findViewById(R.id.editTextNomUsuari);
        final EditText passwordEditText = findViewById(R.id.editTextContrasenya);
        final EditText latitudeEditText = findViewById(R.id.editTextLatitud);
        final EditText birthDateEditText = findViewById(R.id.editTextBirthDate);
        final EditText longitudeEditText = findViewById(R.id.editTextLongitud);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarPerfil.this, Ajustes.class);
                startActivity(intent);
                finish();
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(EditarPerfil.this);

                String url = "https://us-central1-test-8ea8f.cloudfunctions.net/modify_personal_credentials?" +
                        "un=" + usernameEditText.getText() + "&" +
                        "pw=" + passwordEditText.getText() + "&" +
                        "n=" + nameEditText.getText() + "&" +
                        "lat=" + latitudeEditText.getText() + "&" +
                        "lon=" + longitudeEditText.getText();

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")) {
                                    String account_modified_successfully = getString(R.string.account_modified_successfully);
                                    Toast toast = Toast.makeText(getApplicationContext(), account_modified_successfully, Toast.LENGTH_SHORT);
                                    toast.show();

                                    Intent intent = new Intent(EditarPerfil.this, Ajustes.class);
                                    startActivity(intent);
                                    //finish();
                                }
                                else { //response == "No such user in the database"
                                    String texterror = getString(R.string.error);
                                    Toast toast = Toast.makeText(EditarPerfil.this, texterror, Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {  //TODO: aixo ho podem treure?
                        String texterror = getString(R.string.error);
                        Toast toast = Toast.makeText(EditarPerfil.this, texterror, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}

