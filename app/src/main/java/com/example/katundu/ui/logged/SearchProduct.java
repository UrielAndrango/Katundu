package com.example.katundu.ui.logged;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchProduct extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search_products:
                    return true;
                case R.id.navigation_search_users:
                    Intent intent = new Intent(SearchProduct.this, SearchUser.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();

                    //Si lo hacemos con ventanas independientes, quitamos los TRUES
                    return true;
                case R.id.navigation_home:
                    Intent intentHome = new Intent(SearchProduct.this, MenuPrincipal.class);
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
                    Intent intentAdd = new Intent(SearchProduct.this, Add.class);
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
        setContentView(R.layout.activity_search_product);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();
        //Barra Navegacio Tipus de Cerca
        BottomNavigationView typeSearch = findViewById(R.id.type_search);
        typeSearch.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //Barra Navegacio Principal
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        final ImageView Atras = findViewById(R.id.Search_Atras);
        final SearchView search = findViewById(R.id.search_SP);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchProduct.this, MenuPrincipal.class);
                onNewIntent(intent);
                finish();
            }
        });
    }
}
