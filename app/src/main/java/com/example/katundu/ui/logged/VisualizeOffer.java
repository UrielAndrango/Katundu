package com.example.katundu.ui.logged;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AutomaticZenRule;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.ControladoraEditOffer;
import com.example.katundu.ui.ControladoraPresentacio;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VisualizeOffer extends AppCompatActivity {

    String[] categorias = new String[7];
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    final int[] numImages = {0};
    Uri image_uri;
    ImageView PreviewFoto0, PreviewFoto1, PreviewFoto2, PreviewFoto3, PreviewFoto4;
    ImageView[] PreviewFotos;
    final Integer[] cantidad_fotos = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String folder_product = "/products/Q9KGzX0vB7rC5aakqBEp/";
        final StorageReference imagesRef = storageRef.child(folder_product);
        setContentView(R.layout.activity_visualize_offer);

        final ImageView Atras = findViewById(R.id.EditOffer_Atras);

        final String id = ControladoraPresentacio.getOffer_id();
        final TextView nameOffer = findViewById(R.id.editTextNom_EditOffer);
        final TextView categoriaOffer = findViewById(R.id.spinner_EditOffer);
        final Switch tipusOffer = findViewById(R.id.switch3);
        final TextView paraulesClauOffer = findViewById(R.id.editTextParaulesClau_EditOffer);
        final TextView valueOffer = findViewById(R.id.editTextValor_VisualizeOffer);
        final TextView descriptionOffer = findViewById(R.id.textDescripcion_EditOffer);
        final ImageView afegirFavorite = findViewById(R.id.imageView_Favorite);

        // FOTOS
        PreviewFoto0 = findViewById(R.id.previewFoto_EditOffer);
        PreviewFoto1 = findViewById(R.id.previewFoto2_EditOffer);
        PreviewFoto2 = findViewById(R.id.previewFoto3_EditOffer);
        PreviewFoto3 = findViewById(R.id.previewFoto4_EditOffer);
        PreviewFoto4 = findViewById(R.id.previewFoto5_EditOffer);
        PreviewFotos = new ImageView[]{PreviewFoto0, PreviewFoto1, PreviewFoto2, PreviewFoto3, PreviewFoto4};

        afegirFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

        //nombre_fotos();

        System.out.println("Entrem al bucle per carregar imatges");
        for (int i = 0; i < 5; ++i) {
            final int finalI = i;
            StorageReference Reference2 = storageRef.child("/products/" + ControladoraPresentacio.getOffer_id()).child("product_" + i);
            System.out.println(Reference2.toString());
            Reference2.getBytes(1000000000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    cantidad_fotos[0] +=1;
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    PreviewFotos[finalI].setImageBitmap(bmp);
                    PreviewFotos[finalI].setVisibility(View.VISIBLE);
                }
            });
        }
        ControladoraEditOffer.setFotos(PreviewFotos);
        //System.out.println("Cantidad de fotoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooos" + cantidad_fotos[0]);

        PreviewFoto0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ControladoraEditOffer.add_foto(PreviewFotos[0],0);
                ControladoraEditOffer.setNumero_imagen(0);
                Intent intent = new Intent(VisualizeOffer.this, PreviewFotoShow.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladoraEditOffer.add_foto(PreviewFotos[1],1);
                ControladoraEditOffer.setNumero_imagen(1);
                Intent intent = new Intent(VisualizeOffer.this,  PreviewFotoShow.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladoraEditOffer.add_foto(PreviewFotos[2],2);
                ControladoraEditOffer.setNumero_imagen(2);
                Intent intent = new Intent(VisualizeOffer.this,  PreviewFotoShow.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladoraEditOffer.add_foto(PreviewFotos[3],3);
                ControladoraEditOffer.setNumero_imagen(3);
                Intent intent = new Intent(VisualizeOffer.this,  PreviewFotoShow.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladoraEditOffer.add_foto(PreviewFotos[4],4);
                ControladoraEditOffer.setNumero_imagen(4);
                Intent intent = new Intent(VisualizeOffer.this,  PreviewFotoShow.class);
                startActivity(intent);
                //finish();
            }
        });

        //Escondemos la Action Bar porque usamos la ToolBar, aunque podriamos usar la ActionBar
        getSupportActionBar().hide();


        //Inicilizamos las categorias
        categorias[0] = getString(R.string.add_product_category_technology);
        categorias[1] = getString(R.string.add_product_category_home);
        categorias[2] = getString(R.string.add_product_category_beauty);
        categorias[3] = getString(R.string.add_product_category_sports);
        categorias[4] = getString(R.string.add_product_category_fashion);
        categorias[5] = getString(R.string.add_product_category_leisure);
        categorias[6] = getString(R.string.add_product_category_transport);


        //Inicializamos los editText con nuestros datos
        nameOffer.setText(ControladoraPresentacio.getOffer_name());
        categoriaOffer.setText(categorias[ControladoraPresentacio.getWish_Categoria()]); //esto es para cambiar el elemento seleccionado por defecto del spinner
        tipusOffer.setChecked(ControladoraPresentacio.isOffer_Service()); //esto es para cambiar el switch
        tipusOffer.setClickable(false);
        paraulesClauOffer.setText(ControladoraPresentacio.getOffer_PC());
        valueOffer.setText(ControladoraPresentacio.getOffer_Value().toString());
        descriptionOffer.setText(ControladoraPresentacio.getOffer_Description());

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualizeOffer.this, VisualizeListOUser.class);
                onNewIntent(intent);
                //startActivity(intent);
                finish();
            }
        });

        afegirFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestAddFavorite(Atras, afegirFavorite);
            }
        });
    }

    private void RequestAddFavorite(final ImageView Atras, final ImageView afegirFavorite) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(VisualizeOffer.this);

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/addfavorite?" +
                "user=" + ControladoraPresentacio.getUsername() + "&" +
                "objectId=" + ControladoraPresentacio.getOffer_id();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("0")) { //favorite added successfully
                            String favorite_added_successfully = getString(R.string.favorite_added_successfully);
                            Toast toast = Toast.makeText(getApplicationContext(), favorite_added_successfully, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String texterror = getString(R.string.error);
                Toast toast = Toast.makeText(VisualizeOffer.this, texterror, Toast.LENGTH_SHORT);
                toast.show();
                //reactivar atras y subir wish
                Atras.setEnabled(true);
                afegirFavorite.setEnabled(true);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //called when image was captured from camera
        if (resultCode == RESULT_OK) {
            //set the image captured to our ImageView
            //int longitud = fotos.length;
            PreviewFotos[cantidad_fotos[0]].setImageURI(image_uri);
            PreviewFotos[cantidad_fotos[0]].setImageURI(image_uri);
            cantidad_fotos[0]++;
            /*
            int longitud = ControladoraAddProduct.getFotos().length;
            int i = 0;
            boolean foto_subida_con_exito = false;
            while ((i < longitud) && (foto_subida_con_exito == false)) {
                if (fotos[i] == null) {
                    PreviewFotos[i].setImageURI(image_uri);
                    PreviewFotos[i].setVisibility(View.VISIBLE);

                    //Actualizamos la controladora
                    ControladoraAddProduct.add_foto(image_uri, i);
                    fotos = ControladoraAddProduct.getFotos();
                    cantidad_fotos = ControladoraAddProduct.getCantidad_fotos();
                    //Salimos del bucle
                    foto_subida_con_exito = true;

                }
                else {
                    //Siguiente hueco de foto
                    ++i;
                }
            }
            */
        }
    }
}
