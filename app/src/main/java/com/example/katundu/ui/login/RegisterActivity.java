package com.example.katundu.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.logged.MenuPrincipal;

import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText nameEditText = findViewById(R.id.nom);
        final EditText passwordEditText = findViewById(R.id.password1);
        final EditText repeatpasswordEditText = findViewById(R.id.password2);
        final EditText latitudeEditText = findViewById(R.id.latitud);
        final EditText longitudeEditText = findViewById(R.id.longitud);
        final Button registrat = findViewById(R.id.SaveButton);


        registrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(passwordEditText.getText() == repeatpasswordEditText.getText()) {
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

                    String url = "https://us-central1-test-8ea8f.cloudfunctions.net/signup?" +
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
                                    String welcome = getString(R.string.welcome) + usernameEditText.getText().toString();
                                    Toast toast = Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT);
                                    toast.show();

                                    Intent intent = new Intent(RegisterActivity.this, MenuPrincipal.class);
                                    startActivity(intent);
                                    //finish();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String texterror = getString(R.string.incorrect_data);
                            Toast toast = Toast.makeText(RegisterActivity.this, texterror, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
                else {
                    String texterror = getString(R.string.incorrect_password);
                    Toast toast = Toast.makeText(RegisterActivity.this, texterror, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}