package com.example.katundu.ui.logged;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.katundu.ui.ControladoraPresentacio;

public class ChooseActionOffer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action_offer);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final ImageView Atras = findViewById(R.id.ChooseActionOffer_Atras);
        final Button ModificarOffer = findViewById(R.id.modificar_offer);
        final Button DeleteOffer = findViewById(R.id.delete_offer);
        final String id = ControladoraPresentacio.getOffer_id();

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Cuidado porque vuelve a User no a la ventana que me invoca
                Intent intent = new Intent(ChooseActionOffer.this, ListOffer.class);
                onNewIntent(intent);
                //startActivity(intent);
                finish();
            }
        });

        ModificarOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseActionOffer.this, EditOffer.class);
                startActivity(intent);
                //finish();
            }
        });

        DeleteOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestDeleteOffer(id);
            }
        });
    }

    private void RequestDeleteOffer(final String id) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ChooseActionOffer.this);

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/deleteoffer?" + "id=" + id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("0")) { //Account modified successfully
                            String offer_deleted_successfully = getString(R.string.offer_deleted_successfully);
                            Toast toast = Toast.makeText(getApplicationContext(), offer_deleted_successfully, Toast.LENGTH_SHORT);
                            toast.show();

                            //Volvemos a User
                            Intent intent = new Intent(ChooseActionOffer.this, ListOffer.class);
                            startActivity(intent);
                            finish();
                        }
                        else { //response == "1" No s'ha esborrat el desig
                            String texterror = getString(R.string.error);
                            Toast toast = Toast.makeText(ChooseActionOffer.this, texterror, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String texterror = getString(R.string.error);
                Toast toast = Toast.makeText(ChooseActionOffer.this, texterror, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
