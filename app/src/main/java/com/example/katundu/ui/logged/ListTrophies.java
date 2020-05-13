package com.example.katundu.ui.logged;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.example.katundu.R;

public class ListTrophies extends AppCompatActivity {

    //TODO: Sumar 4h (de 14:30 a 18:30) TROPHIES
    //TODO: Sumar 2h (de 20:30 a 22:30) Swipe Refresh
    //TODO: SUmar 0,5h (de 22:30 a 23:00) Trophies en MP, LW, LO, LF
    //TODO: Sumar 1,5h (de 23:00 a 24:00) Fotos desde la Galeria en AddP y Modify (no va bien)
    int numero_trofeos = 16;
    ImageView[] lista_trofeos = new ImageView[numero_trofeos];
    Drawable[] vector_icons = new Drawable[numero_trofeos];
    Boolean[] trofeos_usuario = new Boolean[]{true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_trophies);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final ImageView Atras = findViewById(R.id.ListTrophies_Atras);
        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTrophies.this, ListOffer.class);
                onNewIntent(intent);
                finish();
            }
        });

        lista_trofeos[0] = (ImageView) findViewById(R.id.image_lt0);
        lista_trofeos[1] = (ImageView) findViewById(R.id.image_lt1);
        lista_trofeos[2] = (ImageView) findViewById(R.id.image_lt2);
        lista_trofeos[3] = (ImageView) findViewById(R.id.image_lt3);
        lista_trofeos[4] = (ImageView) findViewById(R.id.image_lt4);
        lista_trofeos[5] = (ImageView) findViewById(R.id.image_lt5);
        lista_trofeos[6] = (ImageView) findViewById(R.id.image_lt6);
        lista_trofeos[7] = (ImageView) findViewById(R.id.image_lt7);
        lista_trofeos[8] = (ImageView) findViewById(R.id.image_lt8);
        lista_trofeos[9] = (ImageView) findViewById(R.id.image_lt9);
        lista_trofeos[10] = (ImageView) findViewById(R.id.image_lt10);
        lista_trofeos[11] = (ImageView) findViewById(R.id.image_lt11);
        lista_trofeos[12] = (ImageView) findViewById(R.id.image_lt12);
        lista_trofeos[13] = (ImageView) findViewById(R.id.image_lt13);
        lista_trofeos[14] = (ImageView) findViewById(R.id.image_lt14);
        lista_trofeos[15] = (ImageView) findViewById(R.id.image_lt15);
        //lista_trofeos = new ImageView[]{trofeo1, trofeo2, trofeo3, trofeo4, trofeo5, trofeo6, trofeo7, trofeo8, trofeo9, trofeo10, trofeo11, trofeo12};
        vector_icons[0] = getResources().getDrawable(R.drawable.icon_trophy_person_mirror);
        vector_icons[1] = getResources().getDrawable(R.drawable.icon_trophy_steps);
        vector_icons[2] = getResources().getDrawable(R.drawable.icon_trophy_lamp);
        vector_icons[3] = getResources().getDrawable(R.drawable.icon_trophy_sabio);
        vector_icons[4] = getResources().getDrawable(R.drawable.icon_trophy_heart_draw);
        vector_icons[5] = getResources().getDrawable(R.drawable.icon_trophy_dreams_are_true);
        vector_icons[6] = getResources().getDrawable(R.drawable.icon_trophy_favorites);
        vector_icons[7] = getResources().getDrawable(R.drawable.icon_trophy_1);
        vector_icons[8] = getResources().getDrawable(R.drawable.icon_trophy_5);
        vector_icons[9] = getResources().getDrawable(R.drawable.icon_trophy_12);
        vector_icons[10] = getResources().getDrawable(R.drawable.icon_trophy_25);
        vector_icons[11] = getResources().getDrawable(R.drawable.icon_trophy_50);
        vector_icons[12] = getResources().getDrawable(R.drawable.icon_trophy_100);
        vector_icons[13] = getResources().getDrawable(R.drawable.icon_trophy_250);
        vector_icons[14] = getResources().getDrawable(R.drawable.icon_trophy_500);
        vector_icons[15] = getResources().getDrawable(R.drawable.icon_trophy_1000);

        for (int i=0; i<numero_trofeos; ++i) {
            if (trofeos_usuario[i]) {
                //extraemos el drawable en un bitmap
                Bitmap originalBitmap = ((BitmapDrawable) vector_icons[i]).getBitmap();
                //creamos el drawable redondeado
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
                //asignamos el CornerRadius
                roundedDrawable.setCornerRadius(originalBitmap.getHeight());
                lista_trofeos[i].setImageDrawable(roundedDrawable);
            }
            else {
                lista_trofeos[i].setImageDrawable(getResources().getDrawable(R.drawable.icon_interrogante));
            }
        }
    }
}
