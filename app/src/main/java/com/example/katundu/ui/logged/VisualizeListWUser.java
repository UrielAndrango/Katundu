package com.example.katundu.ui.logged;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;
import com.example.katundu.ui.ControladoraPresentacio;
import com.example.katundu.ui.ControladoraSearchUsers;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VisualizeListWUser extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_visualize_wish_list:
                    return true;
                case R.id.navigation_visualize_offer_list:
                    Intent intent = new Intent(VisualizeListWUser.this, VisualizeListOUser.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //onNewIntent(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();

                    //Si lo hacemos con ventanas independientes, quitamos los TRUES
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize_list_w_user);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final TextView NomUsuari = findViewById(R.id.VLO_nomUsuari);
        final ImageView Atras = findViewById(R.id.VLO_User_Atras);

        //USERNAME DEL USUARIO
        NomUsuari.setText(ControladoraSearchUsers.getUsername());

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualizeListWUser.this, MenuPrincipal.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                onNewIntent(intent);
                //startActivity(intent);
                finish();
            }
        });

        //Valoracion Usuario
        final TextView valoracion_usuario = findViewById(R.id.textView_valoracio_numero_VLO_User);
        valoracion_usuario.setText(Double.toString(ControladoraSearchUsers.getValoracion()));
        final ImageView star1 = findViewById(R.id.imageViewStar1_VLO_User);
        final ImageView star2 = findViewById(R.id.imageViewStar2_VLO_User);
        final ImageView star3 = findViewById(R.id.imageViewStar3_VLO_User);
        final ImageView star4 = findViewById(R.id.imageViewStar4_VLO_User);
        final ImageView star5 = findViewById(R.id.imageViewStar5_VLO_User);
        ImageView[] stars = {star1, star2, star3, star4, star5};
        int valoracion = (int)ControladoraPresentacio.getValoracion();
        for (int i=0; i<valoracion; ++i) {
            stars[i].setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }

        //Barra Navegacio Llistes
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //navView.setSelectedItemId(R.id.navigation_visualize_offer_list);
        //navView.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
