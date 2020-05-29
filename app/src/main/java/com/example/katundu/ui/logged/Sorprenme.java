package com.example.katundu.ui.logged;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.ControladoraPresentacio;
import com.example.katundu.ui.Offer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sorprenme extends AppCompatActivity {
    private TextView mTextMessage;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    final ArrayList<Offer> ofertes_totals = new ArrayList<Offer>();
    final ArrayList<String> ofertes_matches = new ArrayList<String>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(Sorprenme.this, MenuPrincipal.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    //finish();
                    break;
                case R.id.navigation_surprise:
                    return true;
                case R.id.navigation_add:
                    Intent intent2 = new Intent(Sorprenme.this, Add.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                    //finish();
                    break;
                case R.id.navigation_xat:
                    Intent intent3 = new Intent(Sorprenme.this, ListChat.class);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent3);
                    overridePendingTransition(0, 0);
                    break;
            }
            return false;
        }
    };
    SwipeRefreshLayout refreshLayout;
    TextView m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorprenme);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final ImageView Perfil_img = findViewById(R.id.img_perfil);

        refreshLayout = findViewById(R.id.refreshLayout_SP);

        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //RequestGetMatches();

        Perfil_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sorprenme.this, ListOffer.class);
                startActivity(intent);
                //finish();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //recreate();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
                refreshLayout.setRefreshing(false);
            }
        });
        m = findViewById(R.id.no_sorprenme_found);
        m.setVisibility(View.GONE);
        RequestGetOffers();
    }

    private void RequestGetOffers() {
        final String username = ControladoraPresentacio.getUsername();
        final ArrayList<Offer> offer_list = new ArrayList<>();
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(Sorprenme.this);

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/calculate-sorpren?un=" + ControladoraPresentacio.getUsername();

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); ++i) {
                        JSONObject arrays_offer = response.getJSONObject(i);
                        for (int j = 0; j < arrays_offer.length(); ++j) {
                            JSONObject info_offer = arrays_offer.getJSONObject("offer" + Integer.toString(j + 1));
                            String user = info_offer.getString("user");
                            String id = info_offer.getString("id"); //TODO: ACTUALITZA AMB CAMP ID
                            String name = info_offer.getString("name");
                            String category = info_offer.getString("category");
                            String type = info_offer.getString("type");
                            JSONArray keywords_array = info_offer.getJSONArray("keywords");
                            String keywords = "";
                            for (int k = 0; k < keywords_array.length(); ++k) {
                                String keyword = keywords_array.getString(k);
                                keywords += "#";
                                keywords += keyword;
                            }
                            String value = info_offer.getString("value");
                            String description = info_offer.getString("description");
                            Offer offer = new Offer(id, name, Integer.parseInt(category), type, keywords, Integer.parseInt(value), description, user);
                            offer_list.add(offer);

                        }
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
        ControladoraPresentacio.setOffer_List(ofertes_totals);
        int numBotones = offer_list.size();
        if (numBotones == 0) {
            m.setVisibility(View.VISIBLE);
        } else {


            //Obtenemos el linear layout donde colocar los botones
            LinearLayout llBotonera = findViewById(R.id.listaOffers_sp);

            //Creamos las propiedades de layout que tendrán los botones.
            //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout pareja = new LinearLayout(Sorprenme.this);
            //Creamos los botones en bucle
            //Antes debemos saber si hay un numero par o impar
            boolean mostrar_producto = true;
            boolean modo_impar = false;
            if (numBotones % 2 == 1) modo_impar = true;

            for (int i = 0; i < numBotones; ++i) {
                //Modo Layout con pareja, layout de layout con foto+precio+nombre
                //LinearLayout pareja;
                if (mostrar_producto && i % 2 == 0) {
                    pareja = new LinearLayout(Sorprenme.this);
                    pareja.setOrientation(LinearLayout.HORIZONTAL);
                    TableRow.LayoutParams paramsP = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                    if (i == 0) paramsP.setMargins(0, 20, 0, 20);
                    else paramsP.setMargins(0, 0, 0, 20);
                    pareja.setLayoutParams(paramsP);
                    //pareja.setBackgroundResource(R.drawable.logout_rounded);
                }
                //Definimos el layout y lo que contiene (foto+precio+nombre)
                LinearLayout ll = new LinearLayout(Sorprenme.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                final ImageView foto = new ImageView(Sorprenme.this);
                TextView preu_producte = new TextView(Sorprenme.this);
                TextView nom_producte = new TextView(Sorprenme.this);
                //Asignamos Texto a los textViews
                preu_producte.setText(offer_list.get(i).getValue() + "€");
                nom_producte.setText(offer_list.get(i).getName() + "");
                //Le damos el estilo que queremos
                //pareja.setBackgroundResource(R.drawable.button_rounded);
                ll.setBackgroundResource(R.drawable.button_rounded);
                //ll.setBackgroundColor(Color.WHITE);
                //ll.setBackgroundResource(R.drawable.custom_border_black);
                //foto.setImageURI();
                StorageReference Reference = storageRef.child("/products/" + offer_list.get(i).getId()).child("product_0");
                Reference.getBytes(1000000000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        //Redondeamos las esquinas de las fotos
                        bmp = ControladoraPresentacio.getRoundedCornerBitmap(bmp, 64 * 2);
                        foto.setImageBitmap(bmp);
                        foto.setVisibility(View.VISIBLE);
                    }
                });
                preu_producte.setTextColor(Sorprenme.this.getResources().getColor(R.color.colorLetraKatundu));
                nom_producte.setTextColor(Sorprenme.this.getResources().getColor(R.color.colorLetraKatundu));
                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                preu_producte.setTypeface(boldTypeface);
                nom_producte.setTypeface(boldTypeface);
                preu_producte.setTextSize(18);
                nom_producte.setTextSize(18);
                //Margenes del layout
                TableRow.LayoutParams paramsll = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                paramsll.weight = 1;
                paramsll.height = 800;
                //paramsll.setMargins(left, top, right, bottom);
                if (i % 2 == 0) paramsll.setMargins(0, 0, 10, 0);
                else paramsll.setMargins(10, 0, 0, 0);
                ll.setLayoutParams(paramsll);
                //Margenes de los textViews
                TableRow.LayoutParams paramsFoto = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                paramsFoto.weight = 1;
                paramsFoto.setMargins(25, 25, 25, 10);
                foto.setLayoutParams(paramsFoto);
                TableRow.LayoutParams paramsPrecio = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                paramsPrecio.setMargins(25, 10, 25, 10);
                preu_producte.setLayoutParams(paramsPrecio);
                TableRow.LayoutParams paramsN = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                //paramsN.weight = 1;
                paramsN.setMargins(25, 10, 25, 20);
                nom_producte.setLayoutParams(paramsN);
                //Le escondemos el nombre del producto en la descripcion
                ll.setContentDescription(offer_list.get(i).getName());
                //Asignamose el Listener al Layout dinamico
                ll.setOnClickListener(new Sorprenme.LayoutOnClickListener(Sorprenme.this));
                //Añadimos el layout dinamico al layout
                ll.addView(foto);
                ll.addView(preu_producte);
                ll.addView(nom_producte);
                if (!mostrar_producto) ll.setVisibility(View.INVISIBLE);
                pareja.addView(ll);
                if (mostrar_producto && i % 2 == 0) llBotonera.addView(pareja);

                if (modo_impar == true && i == numBotones - 1) {
                    --i;
                    mostrar_producto = false;
                    modo_impar = false;
                }
            }
        }
    }

    private class LayoutOnClickListener implements View.OnClickListener {
        public LayoutOnClickListener(Sorprenme Sorprenme) {
        }

        @Override
        public void onClick(View view) {
            //Provando que funciona el layout en modo boton
            Toast.makeText(getApplicationContext(), view.getContentDescription().toString(), Toast.LENGTH_SHORT).show();

            //Pasamos los datos del deseo a la controladora
            Offer info_offer = ControladoraPresentacio.getOffer_perName(view.getContentDescription().toString());
            System.out.println(info_offer);
            //Pasamos los datos del deseo a la controladora
            ControladoraPresentacio.setOffer_id(info_offer.getId());
            ControladoraPresentacio.setOffer_name(info_offer.getName());
            ControladoraPresentacio.setOffer_Categoria(info_offer.getCategory());
            ControladoraPresentacio.setOffer_user(info_offer.getUser());
            boolean type = true;
            String tipus = info_offer.getType();
            if (tipus.equals("Producte")) type = false;
            ControladoraPresentacio.setOffer_Service(type);
            ControladoraPresentacio.setOffer_PC(info_offer.getKeywords());
            ControladoraPresentacio.setOffer_Value(info_offer.getValue());
            ControladoraPresentacio.setOffer_Description(info_offer.getDescription());
            //Nos vamos a la ventana de EditOffer
            Intent intent = new Intent(Sorprenme.this, VisualizeOffer.class);
            startActivity(intent);
            //finish();
        }
    }
}
