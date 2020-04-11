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
        InicialitzaBotonsOffers();
    }
    private void InicialitzaBotonsOffers() {
        ArrayList<ArrayList<String>> offers = RequestGetOffers();
        for ( int j = 0; j < offers.size(); ++j)
        {
            ControladoraPresentacio.afegir_offer_id(offers.get(j).toString());
        }
        //ArrayList<String> offers_ids_list = ControladoraPresentacio.get_offer_ids();
        int numBotones = 7; //offers_ids_list.size();

        //Obtenemos el linear layout donde colocar los botones
       // LinearLayout llBotonera = findViewById(R.id.listaOffers_LO);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );

        //Creamos los botones en bucle
        for (int i=0; i<numBotones; i++){
            Button button = new Button(this);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(offers.indexOf(i));
            //Le damos el estilo que queremos
            button.setBackgroundResource(R.drawable.button_rounded);
            button.setTextColor(this.getResources().getColor(R.color.colorLetraKatundu));
            //Margenes del button
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            //params.setMargins(left, top, right, bottom);
            params.setMargins(0, 0, 0, 20);
            button.setLayoutParams(params);
            //Asignamose el Listener
            button.setOnClickListener(new ButtonsOnClickListener(this));
            //Añadimos el botón a la botonera
            //llBotonera.addView(button);
        }
    }
    private ArrayList<ArrayList<String>> RequestGetOffers() {
        final String username = ControladoraPresentacio.getUsername();
        final ArrayList<ArrayList<String>> offer_list = new ArrayList<>();
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(ListOffer.this);

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/getOffers?un=username";

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); ++i) {
                        JSONObject info_offer = response.getJSONObject(i);

                        String id = info_offer.getString("name"); //TODO: ACTUALITZA AMB CAMP ID
                        String name = info_offer.getString("name");
                        String category = info_offer.getString("category");
                        String type = info_offer.getString("type");
                        String keywords = info_offer.getString("keywords");
                        String value = info_offer.getString("value");
                        ArrayList<String> oferta = new ArrayList<>();
                        oferta.add(name);
                        oferta.add(category);
                        oferta.add(type);
                        oferta.add(keywords);
                        oferta.add(value);

                        //Wish wish = new Offer(id,name,category,type,keywords,value);

                        offer_list.add(oferta);
                        System.out.println(response);
                    }
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

        return offer_list;
    }
    private class ButtonsOnClickListener implements View.OnClickListener {
        public ButtonsOnClickListener(ListOffer listOffer) {
        }

        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            /*
            //Provando que funciona el boton
            //Toast.makeText(getApplicationContext(),b.getText(),Toast.LENGTH_SHORT).show();

            //Pasamos los datos del deseo a la controladora
            ControladoraPresentacio.setOffer_name("Clases Matematicas");
            //TODO: Solo deberia hacer falta el nombre, lo demas se deberia pedir al Servidor cuando se quiera modificar
            ControladoraPresentacio.setOffer_Categoria(5);
            ControladoraPresentacio.setOffer_Service(true);
            ControladoraPresentacio.setOffer_PC("Profesor");
            */
            //Nos vamos a la ventana de User
            Intent intent = new Intent(ListOffer.this, ChooseActionWish.class);
            startActivity(intent);
            //finish();
        }
    }
}

