package com.example.katundu.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();

        //No funciona porque no entiendo como funciona el original, pero no peta :)
        final EditText passwordEditText = findViewById(R.id.password1);


        final Button registrat = findViewById(R.id.SaveButton);


        registrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                String url = "https://us-central1-test-8ea8f.cloudfunctions.net/signup?un=test&pw=1234&n=testname&lat=12&lon=34";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                registrat.setText("Response is: " + response.substring(0, 500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        registrat.setText("That didn't work!");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}


