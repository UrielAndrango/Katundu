package com.example.katundu.ui.logged;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.ControladoraPresentacio;
import com.example.katundu.ui.Offer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListOffer extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_wish_list:
                    Intent intent = new Intent(ListOffer.this, ListWish.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //onNewIntent(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();

                    //Si lo hacemos con ventanas independientes, quitamos los TRUES
                    return true;
                case R.id.navigation_own_list:
                    return true;
                case R.id.navigation_fav_list:
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offer);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final TextView NomUsuari = findViewById(R.id.nomUsuari);
        final ImageView ImgSettings = findViewById(R.id.img_settings);
        final ImageView Atras = findViewById(R.id.User_Atras);
        //USERNAME DEL USUARIO
        NomUsuari.setText(ControladoraPresentacio.getUsername());

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOffer.this, MenuPrincipal.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                onNewIntent(intent);
                //startActivity(intent);
                finish();
            }
        });

        ImgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOffer.this, Ajustes.class);
                startActivity(intent);
                //finish();
            }
        });

        //Valoracion Usuario
        final TextView valoracion_usuario = findViewById(R.id.textView_valoracio_numero_User);
        valoracion_usuario.setText(Double.toString(ControladoraPresentacio.getValoracion()));
        final ImageView star1 = findViewById(R.id.imageViewStar1_User);
        final ImageView star2 = findViewById(R.id.imageViewStar2_User);
        final ImageView star3 = findViewById(R.id.imageViewStar3_User);
        final ImageView star4 = findViewById(R.id.imageViewStar4_User);
        final ImageView star5 = findViewById(R.id.imageViewStar5_User);
        ImageView[] stars = {star1, star2, star3, star4, star5};
        int valoracion = (int)ControladoraPresentacio.getValoracion();
        for (int i=0; i<valoracion; ++i) {
            stars[i].setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }

        //Barra Navegacio Llistes
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_own_list);
        //navView.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //Esto se descomentara si sabemos volver a atras de forma "inteligente"
        //Si no gusta se comenta y listo
        NomUsuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOffer.this, EditarPerfil.class);
                startActivity(intent);
                //finish();
            }
        });
       RequestGetOffers();
    }

    private void RequestGetOffers() {
        final String username = ControladoraPresentacio.getUsername();
        final ArrayList<Offer> offer_list = new ArrayList<>();
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(ListOffer.this);

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/getOffers?" + "un=" + username;

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); ++i) {
                        JSONObject info_offer = response.getJSONObject(i);

                        String id = info_offer.getString("id"); //TODO: ACTUALITZA AMB CAMP ID
                        String name = info_offer.getString("name");
                        String category = info_offer.getString("category");
                        String type = info_offer.getString("type");
                        String keywords = info_offer.getString("keywords");
                        String value = info_offer.getString("value");
                        String description = info_offer.getString("description");
                        Offer offer = new Offer(id,name,Integer.parseInt(category),type,keywords,Integer.parseInt(value),description);
                        String tipus = offer.getType();
                        System.out.println("El tipus es : "+ tipus);
                        offer_list.add(offer);
                    }
                    InicialitzaBotonsOffers(offer_list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "Error Respuesta en JSON: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
    private void InicialitzaBotonsOffers(ArrayList<Offer> offer_list) {
        ControladoraPresentacio.setOffer_List(offer_list);
        int numBotones = offer_list.size();

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = findViewById(R.id.listaOffers_LO);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );

        //Creamos los botones en bucle
        for (int i=0; i<numBotones; i++){
            Button button = new Button(this);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(offer_list.get(i).getName() + " - " +offer_list.get(i).getValue() + '€' );
            //Le damos el estilo que queremos
            button.setBackgroundResource(R.drawable.button_rounded);
            button.setHint(offer_list.get(i).getName());
            button.setTextColor(this.getResources().getColor(R.color.colorLetraKatundu));
            button.setContentDescription(offer_list.get(i).getId());
            //Margenes del button
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            //params.setMargins(left, top, right, bottom);
            if(i == 0) params.setMargins(0, 20, 0, 20);
            else params.setMargins(0, 0, 0, 20);
            button.setLayoutParams(params);
            //Asignamose el Listener
            button.setOnClickListener(new ButtonsOnClickListener(this));
            //Añadimos el botón a la botonera
            llBotonera.addView(button);
        }
    }
    private class ButtonsOnClickListener implements View.OnClickListener {
        public ButtonsOnClickListener(ListOffer listOffer) {
        }

        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            //Provando que funciona el boton
            Toast.makeText(getApplicationContext(),b.getText(),Toast.LENGTH_SHORT).show();

            Offer info_offer = ControladoraPresentacio.getOffer_perName(b.getHint().toString());
            //Pasamos los datos del deseo a la controladora
            ControladoraPresentacio.setOffer_id(info_offer.getId());
            ControladoraPresentacio.setOffer_name(info_offer.getName());
            ControladoraPresentacio.setOffer_Categoria(info_offer.getCategory());
            boolean type = true;
            String tipus = info_offer.getType();
            System.out.println("El tipus es : "+ tipus);
            if(tipus.equals("Producte")) type = false;
            ControladoraPresentacio.setOffer_Service(type);
            ControladoraPresentacio.setOffer_PC(info_offer.getKeywords());
            ControladoraPresentacio.setOffer_Value(info_offer.getValue());
            ControladoraPresentacio.setOffer_Description(info_offer.getDescription());
            //Nos vamos a la ventana de User
            Intent intent = new Intent(ListOffer.this, EditOffer.class);
            startActivity(intent);
            //finish();
        }
    }
}

