package com.example.katundu.ui.logged;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();
        //Barra Navegacio Tipus de Cerca
        BottomNavigationView typeSearch = findViewById(R.id.type_search);
        typeSearch.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //Barra Navegacio Principal
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final ImageView Atras = findViewById(R.id.Search_Atras);
        final EditText search = findViewById(R.id.search_SP);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchUser.this, MenuPrincipal.class);
                onNewIntent(intent);
                finish();
            }
        });

        /* Creación de la LISTA DE WISHES */
        //Esto es temporal, hay que hacer tanto botones como wishes tenga el usuario --> Controladora_numeroWishes() ??
        int numBotones = 15;
        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.listaUsers_SU);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        //Creamos los botones en bucle
        for (int i=0; i<numBotones; i++){
            Button button = new Button(this);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Boton "+String.format("%02d", i ));

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
            llBotonera.addView(button);
        }
    }

    private class ButtonsOnClickListener implements View.OnClickListener {
        public ButtonsOnClickListener(SearchUser searchUser) {
        }
        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            //Provando que funciona el boton
            //Toast.makeText(getApplicationContext(),b.getText(),Toast.LENGTH_SHORT).show();

            //Pasamos los datos del deseo a la controladora
            //ControladoraPresentacio.setWish_name("Clases Matematicas");
            //TODO: Solo deberia hacer falta el nombre, lo demas se deberia pedir al Servidor cuando se quiera modificar
            //ControladoraPresentacio.setWish_Categoria(5);
            //ControladoraPresentacio.setWish_Service(true);
            //ControladoraPresentacio.setWish_PC("Profesor");
            //Nos vamos a la ventana de User
            Intent intent = new Intent(SearchUser.this, VisualizeListOUser.class);
            startActivity(intent);
            //finish();
        }
    }
}
