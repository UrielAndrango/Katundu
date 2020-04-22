package com.example.katundu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.ControladoraPresentacio;
import com.example.katundu.ui.logged.MenuPrincipal;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;//TODO:Esto se puede quitar?? No se para que sirve...
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Modo Vertical
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Oculta la barra que dice el nombre de la apliacion en la Action Bar (asi de momento)
        getSupportActionBar().hide();

        //TODO:Esto se puede quitar??
        //Firebase Anlytics de prueba
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.passwordEdit);
        final Button login_button = findViewById(R.id.login);
        final TextView no_registrado = findViewById(R.id.no_registrado);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //desactivar login momentaneamente
                login_button.setEnabled(false);
                no_registrado.setEnabled(false);

                RequestLogin(usernameEditText, passwordEditText, login_button, no_registrado);

                //Reactivar login
                //login_button.setEnabled(true);
                //no_registrado.setEnabled(true);
            }
        });

        no_registrado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        //TODO: Esto se puede quitar??
        Bundle bundle = new Bundle();
        //bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        //bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private void RequestLogin(final EditText usernameEditText, EditText passwordEditText, final Button login_button, final TextView no_registrado) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        final String url = "https://us-central1-test-8ea8f.cloudfunctions.net/login?" +
                "un=" + usernameEditText.getText() + "&" +
                "pw=" + passwordEditText.getText();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("0")) { //Successful login
                            //Inicialitzem amb les dades de l'usuari
                            RequestInicialitzaDadesUsuari(usernameEditText.getText().toString(), queue);

                            /*
                            //Idioma
                            //temporal lo de la controladora, porque debe ser con el idioma del usuario desde el servidor
                            String idioma_usuario = ControladoraPresentacio.getIdioma();
                            Locale localizacion;
                            switch (idioma_usuario) {
                                case "es":
                                    localizacion = new Locale("es");
                                    break;
                                case "ca":
                                    localizacion = new Locale("ca");
                                    break;
                                case "en":
                                    localizacion = new Locale("en");
                                    break;
                                default:
                                    idioma_usuario = "es";
                                    localizacion = new Locale("es");
                            }
                            ControladoraPresentacio.setIdioma(idioma_usuario);
                            Locale.setDefault(localizacion);
                            Configuration config = new Configuration();
                            config.locale = localizacion;
                            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                            */
                        }
                        else if(response.equals("2")){ //Incorrect password
                            String texterror = getString(R.string.incorrect_password);
                            Toast toast = Toast.makeText(LoginActivity.this, texterror, Toast.LENGTH_SHORT);
                            toast.show();
                            //Reactivar login
                            login_button.setEnabled(true);
                            no_registrado.setEnabled(true);
                        }
                        else if(response.equals("1")) { //No such user!
                            String texterror = getString(R.string.unregistered);
                            Toast toast = Toast.makeText(LoginActivity.this, texterror, Toast.LENGTH_SHORT);
                            toast.show();
                            //Reactivar login
                            login_button.setEnabled(true);
                            no_registrado.setEnabled(true);
                        }
                        else { //response == "-1" Error getting document + err
                            String texterror = getString(R.string.error);
                            Toast toast = Toast.makeText(LoginActivity.this, texterror, Toast.LENGTH_SHORT);
                            toast.show();
                            //Reactivar login
                            login_button.setEnabled(true);
                            no_registrado.setEnabled(true);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String texterror = getString(R.string.error);
                Toast toast = Toast.makeText(LoginActivity.this, texterror, Toast.LENGTH_SHORT);
                toast.show();
                //Reactivar login
                login_button.setEnabled(true);
                no_registrado.setEnabled(true);
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void RequestInicialitzaDadesUsuari(final String username, RequestQueue queue) {
        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/infouser?" + "username=" + username;

        // Request a JSONObject response from the provided URL.
        JsonObjectRequest jsObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    ControladoraPresentacio.setUsername(username);
                    ControladoraPresentacio.setNom_real(response.getString("name"));
                    ControladoraPresentacio.setPassword(response.getString("password"));
                    ControladoraPresentacio.setLatitud(response.getString("latitud"));
                    ControladoraPresentacio.setLongitud(response.getString("longitud"));
                    ControladoraPresentacio.setDistanciaMaxima(response.getString("distancia"));

                    String welcome = getString(R.string.welcome) + username;
                    Toast toast = Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT);
                    toast.show();

                    //Canviem de pantalla i anem al Menu Principal
                    Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

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
        queue.add(jsObjectRequest);
    }
}