package com.example.katundu.ui.logged;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.ControladoraPresentacio;
import com.example.katundu.ui.ControladoraSearchUsers;
import com.example.katundu.ui.Message;
import com.example.katundu.ui.Wish;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisualizeChat extends AppCompatActivity {

    LinearLayout llBotonera = findViewById(R.id.LinearLayout_Messages);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize_chat);

        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final String username1 = ControladoraPresentacio.getUsername();
        final TextView username2 = findViewById(R.id.nomUsuari);
        final ImageView Atras = findViewById(R.id.ViewChat_Atras);
        final String id_Chat = "id_chat"; //TODO: posar ControladorChat.getId()
        final ImageView Enviar_message = findViewById(R.id.enviar_message);
        final TextView contingut_message = findViewById(R.id.contingut_message);

        username2.setText("nomUsuari"); //TODO: posar ControladorChat.getusername2() --> usuari amb qui parles
 
        RequestGetMessages(id_Chat);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualizeChat.this, ListChat.class);
                onNewIntent(intent);
                finish();
            }
        });

        Enviar_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestAddMessage(id_Chat, username1, contingut_message);
            }
        });
    }

    private void RequestAddMessage(final String id_Chat, String username1, TextView contingut_message) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(VisualizeChat.this);

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/chat-addMessage?" +
                "id=" + id_Chat + "&" +
                "un=" + username1 + "&" +
                "message=" + contingut_message.getText();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("0")) { //wish added successfully
                            RequestGetMessages(id_Chat);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String texterror = getString(R.string.error);
                Toast toast = Toast.makeText(VisualizeChat.this, texterror, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void RequestGetMessages(String id_Chat) {
        final ArrayList<Message> message_list = new ArrayList<>();

        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(VisualizeChat.this);

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/chat-getMessages?" + "id=" + id_Chat;

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); ++i) {
                        JSONObject info_message = response.getJSONObject(i);

                        String username = info_message.getString("username");
                        String contingut_message = info_message.getString("message");
                        String time = info_message.getString("time");

                        Message message = new Message(username, contingut_message, time);

                        message_list.add(message);
                    }

                    InicialitzarLayoutsMissatges(llBotonera, message_list);

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

    private void InicialitzarLayoutsMissatges(LinearLayout llBotonera, ArrayList<Message> message_list) {
        //Borramos la busqueda anterior
        if (llBotonera.getChildCount() > 0) llBotonera.removeAllViews();

        /* Creación de la lista mensajes */
        //Esto es temporal, hay que hacer tanto botones como usuarios hagan match en la busqueda
        int numBotones = message_list.size();
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        //Creamos los botones en bucle
        for (int i=0; i<numBotones; i++){
            //Modo Layout con 2 TextViews
            LinearLayout ll = new LinearLayout(VisualizeChat.this);
            ll.setOrientation(LinearLayout.VERTICAL);

            TextView text_missatge = new TextView(VisualizeChat.this);
            //TODO: caldrà posar temps, username al missatge

            //Asignamos propiedades de layout al layout
            ll.setLayoutParams(lp);

            //Asignamos Texto a los textViews
            text_missatge.setText("HOLA AIXO ÉS UN MISSATGE");
            //TODO: posar text correcte:
            //text_missatge.setText(message_list.get(i).getMessage());

            //Le damos el estilo que queremos
            ll.setBackgroundResource(R.drawable.button_rounded);

            text_missatge.setTextColor(VisualizeChat.this.getResources().getColor(R.color.colorLetraKatundu));
            text_missatge.setTextSize(18);

            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            text_missatge.setTypeface(boldTypeface);


            //Margenes del layout
            TableRow.LayoutParams paramsll = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            //paramsll.setMargins(left, top, right, bottom);
            if (i==0) paramsll.setMargins(0, 20, 0, 20);
            else paramsll.setMargins(0, 0, 0, 20);
            ll.setLayoutParams(paramsll);

            //Margenes del textView
            TableRow.LayoutParams paramsU = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            paramsU.setMargins(25, 20, 25, 10);
            text_missatge.setLayoutParams(paramsU);

            //Asignamose el Listener al Layout dinamico
            //ll.setOnClickListener(new SearchUser.LayoutOnClickListener(SearchUser.this));

            //Añadimos el layout dinamico al layout
            ll.addView(text_missatge);

            llBotonera.addView(ll);
        }
    }
}
