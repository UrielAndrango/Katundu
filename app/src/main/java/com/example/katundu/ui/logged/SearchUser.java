package com.example.katundu.ui.logged;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.ControladoraSearchUsers;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchUser extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search_products:
                    Intent intent = new Intent(SearchUser.this, SearchProduct.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();

                    //Si lo hacemos con ventanas independientes, quitamos los TRUES
                    return true;
                case R.id.navigation_search_users:
                    return true;
                case R.id.navigation_home:
                    Intent intentHome = new Intent(SearchUser.this, MenuPrincipal.class);
                    intentHome.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentHome);
                    //onNewIntent(intentHome);
                    overridePendingTransition(0,0);
                    finish();
                    //Si lo hacemos con ventanas independientes, quitamos los TRUES
                    return true;
                case R.id.navigation_surprise:
                    return true;
                case R.id.navigation_add:
                    Intent intentAdd = new Intent(SearchUser.this, Add.class);
                    intentAdd.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intentAdd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentAdd);
                    //onNewIntent(intentAdd);
                    overridePendingTransition(0,0);
                    finish();

                    //Si lo hacemos con ventanas independientes, quitamos los TRUES
                    return true;
                case R.id.navigation_xat:
                    return true;
            }
            return false;
        }
    };

    //Obtenemos el linear layout donde colocar los botones
    LinearLayout llBotonera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();
        //Barra Navegacio Tipus de Cerca
        BottomNavigationView typeSearch = findViewById(R.id.type_search);
        typeSearch.setSelectedItemId(R.id.navigation_search_users);
        typeSearch.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //Barra Navegacio Principal
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final ImageView Atras = findViewById(R.id.Search_Atras);
        final SearchView search = findViewById(R.id.search_SU);
        //Obtenemos el linear layout donde colocar los botones
        llBotonera = (LinearLayout) findViewById(R.id.listaUsers_SU);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchUser.this, MenuPrincipal.class);
                onNewIntent(intent);
                finish();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                RequestSearchUser(search.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void RequestSearchUser(final String username) {
        final RequestQueue queue = Volley.newRequestQueue(SearchUser.this);
        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/user-search?" + "username=" + username;

        // Request a JSONObject response from the provided URL.
        JsonObjectRequest jsObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    ControladoraSearchUsers.setUsername(username);
                    ControladoraSearchUsers.setNombre_real(response.getString("name"));
                    //ControladoraSearchUsers.setValoracion();

                    String welcome = getString(R.string.search_user_good_search);
                    Toast toast = Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT);
                    toast.show();
                    InicialitzarBotons(username);

                } catch (JSONException e) {
                    e.printStackTrace();
                    String welcome = getString(R.string.search_user_bad_search);
                    Toast toast = Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "Error Respuesta en JSON: " + error.getMessage());
            }
        });
        queue.add(jsObjectRequest);
    }

    private void InicialitzarBotons(String username) {
        //Borramos la busqueda anterior
        if (llBotonera.getChildCount() > 0) llBotonera.removeAllViews();

        /* Creación de la LISTA DE WISHES */
        //Esto es temporal, hay que hacer tanto botones como usuarios hagan match en la busqueda
        int numBotones = 1;
        //Obtenemos el linear layout donde colocar los botones
        //LinearLayout llBotonera = (LinearLayout) findViewById(R.id.listaUsers_SU);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        //Creamos los botones en bucle
        for (int i=0; i<numBotones; i++){
            //Modo Layout con 2 TextViews
            LinearLayout ll = new LinearLayout(SearchUser.this);
            ll.setOrientation(LinearLayout.VERTICAL);
            TextView text_username = new TextView(SearchUser.this);
            TextView text_realName = new TextView(SearchUser.this);
            TextView text_valoracio = new TextView(SearchUser.this);
            //Asignamos propiedades de layout al layout
            ll.setLayoutParams(lp);
            //Asignamos Texto a los textViews
            text_username.setText(getString(R.string.search_user_username) + ": " + username);
            text_realName.setText(getString(R.string.search_user_real_name) + ": " + ControladoraSearchUsers.getNombre_real());
            text_valoracio.setText(getString(R.string.search_user_valoracio) + ": " + ControladoraSearchUsers.getValoracion() + "/5");
            //Le damos el estilo que queremos
            ll.setBackgroundResource(R.drawable.button_rounded);
            text_username.setTextColor(SearchUser.this.getResources().getColor(R.color.colorLetraKatundu));
            text_realName.setTextColor(SearchUser.this.getResources().getColor(R.color.colorLetraKatundu));
            text_valoracio.setTextColor(SearchUser.this.getResources().getColor(R.color.colorLetraKatundu));
            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            text_username.setTypeface(boldTypeface);
            text_realName.setTypeface(boldTypeface);
            text_valoracio.setTypeface(boldTypeface);
            text_username.setTextSize(18);
            text_realName.setTextSize(18);
            text_valoracio.setTextSize(18);
            //Margenes del layout
            TableRow.LayoutParams paramsll = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            //paramsll.setMargins(left, top, right, bottom);
            if (i==0) paramsll.setMargins(0, 20, 0, 20);
            else paramsll.setMargins(0, 0, 0, 20);
            ll.setLayoutParams(paramsll);
            //Margenes de los textViews
            TableRow.LayoutParams paramsU = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            paramsU.setMargins(25, 20, 25, 10);
            text_username.setLayoutParams(paramsU);
            TableRow.LayoutParams paramsRN = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            paramsRN.setMargins(25, 10, 25, 10);
            text_realName.setLayoutParams(paramsRN);
            TableRow.LayoutParams paramsV = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            paramsV.setMargins(25, 10, 25, 20);
            text_valoracio.setLayoutParams(paramsV);
            //Asignamose el Listener al Layout dinamico
            ll.setOnClickListener(new LayoutOnClickListener(SearchUser.this));
            //Añadimos el layout dinamico al layout
            ll.addView(text_username);
            ll.addView(text_realName);
            ll.addView(text_valoracio);
            llBotonera.addView(ll);
        }
    }

    private class LayoutOnClickListener implements View.OnClickListener {
        public LayoutOnClickListener(SearchUser searchUser) {
        }
        @Override
        public void onClick(View view) {
            //Pasamos los datos del deseo a la controladora
            //TODO: Se debe pedir las listas del usuario que se ha clicado
            //ControladoraSearchUsers.setUsername("?");

            //Nos vamos a la ventana de VisualizeListOUser
            Intent intent = new Intent(SearchUser.this, VisualizeListOUser.class);
            startActivity(intent);
            //finish();
        }
    }
}
