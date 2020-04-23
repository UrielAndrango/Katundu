package com.example.katundu.ui.logged;

        import androidx.appcompat.app.AppCompatActivity;

        import android.Manifest;
        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.katundu.R;
        import com.example.katundu.ui.ControladoraEditOffer;
        import com.example.katundu.ui.ControladoraPresentacio;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.material.textfield.TextInputEditText;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;

public class VisualizeFavorite extends AppCompatActivity {

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
        setContentView(R.layout.activity_visualize_favorite);

        final ImageView Atras = findViewById(R.id.VisualizeFavorite_Atras);

        final String id = ControladoraPresentacio.getfavorite_id();
        final TextView nameFavorite = findViewById(R.id.TextNom_VisualizeFavorite);
        final TextView categoriaFavorite  = findViewById(R.id.spinner_VisualizeFavorite);
        final Switch tipusFavorite  = findViewById(R.id.switch3);
        final TextView paraulesClauFavorite  = findViewById(R.id.ParaulesClau_VisualizeFavorite);
        final TextView valueFavorite  = findViewById(R.id.TextValor_VisualizeFavorite);
        final TextView descriptionFavorite  = findViewById(R.id.textDescripcion_VisualizeFavorite);

        // FOTOS
        PreviewFoto0 = findViewById(R.id.previewFoto_VisualizeFavorite);
        PreviewFoto1 = findViewById(R.id.previewFoto2_VisualizeFavorite);
        PreviewFoto2 = findViewById(R.id.previewFoto3_VisualizeFavorite);
        PreviewFoto3 = findViewById(R.id.previewFoto4_VisualizeFavorite);
        PreviewFoto4 = findViewById(R.id.previewFoto5_VisualizeFavorite);
        PreviewFotos = new ImageView[]{PreviewFoto0, PreviewFoto1, PreviewFoto2, PreviewFoto3, PreviewFoto4};

        //nombre_fotos();

        System.out.println("Entrem al bucle per carregar imatges");
        for (int i = 0; i < 5; ++i) {
            final int finalI = i;
            StorageReference Reference2 = storageRef.child("/products/" + ControladoraPresentacio.getfavorite_id()).child("product_" + i);
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
                Intent intent = new Intent(VisualizeFavorite.this, PreviewFotoShow.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladoraEditOffer.add_foto(PreviewFotos[1],1);
                ControladoraEditOffer.setNumero_imagen(1);
                Intent intent = new Intent(VisualizeFavorite.this,  PreviewFotoShow.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladoraEditOffer.add_foto(PreviewFotos[2],2);
                ControladoraEditOffer.setNumero_imagen(2);
                Intent intent = new Intent(VisualizeFavorite.this,  PreviewFotoShow.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladoraEditOffer.add_foto(PreviewFotos[3],3);
                ControladoraEditOffer.setNumero_imagen(3);
                Intent intent = new Intent(VisualizeFavorite.this,  PreviewFotoShow.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladoraEditOffer.add_foto(PreviewFotos[4],4);
                ControladoraEditOffer.setNumero_imagen(4);
                Intent intent = new Intent(VisualizeFavorite.this,  PreviewFotoShow.class);
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
        nameFavorite .setText(ControladoraPresentacio.getFavorite_name());
        categoriaFavorite .setText(categorias[ControladoraPresentacio.getWish_Categoria()]); //esto es para cambiar el elemento seleccionado por defecto del spinner
        tipusFavorite .setChecked(ControladoraPresentacio.isfavorite_Service()); //esto es para cambiar el switch
        tipusFavorite .setClickable(false);
        paraulesClauFavorite .setText(ControladoraPresentacio.getfavorite_PC());
        valueFavorite .setText(ControladoraPresentacio.getfavorite_Value().toString());
        descriptionFavorite .setText(ControladoraPresentacio.getfavorite_Description());

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualizeFavorite.this, ListFavorites.class);
                onNewIntent(intent);
                //startActivity(intent);
                finish();
            }
        });
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
        }
    }
}
