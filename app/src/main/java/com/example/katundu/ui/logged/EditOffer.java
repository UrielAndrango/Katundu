package com.example.katundu.ui.logged;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.ControladoraEditOffer;
import com.example.katundu.ui.ControladoraPresentacio;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
public class EditOffer extends AppCompatActivity {

    String[] categorias = new String[7];
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    final int[] numImages = {0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String folder_product = "/products/Q9KGzX0vB7rC5aakqBEp/";
        StorageReference imagesRef = storageRef.child(folder_product);
        setContentView(R.layout.activity_edit_offer);
        final ImageView Atras = findViewById(R.id.EditOffer_Atras);
        final Button Modify_Offer = findViewById(R.id.ok_button_EditOffer);

        final String id = ControladoraPresentacio.getOffer_id();
        final EditText nameEditText = findViewById(R.id.editTextNom_EditOffer);
        final Spinner categoriaSpinner = findViewById(R.id.spinner_EditOffer);
        final Switch tipusSwitch = findViewById(R.id.switch3);
        final String[] tipus = {"Producte"};
        final EditText paraulesClauEditText = findViewById(R.id.editTextParaulesClau_EditOffer);
        final EditText valueEditText = findViewById(R.id.editTextValor_EditOffer);

        // FOTOS
        Button Camara;
        ImageView PreviewFoto0, PreviewFoto1, PreviewFoto2, PreviewFoto3, PreviewFoto4;
        final ImageView[] PreviewFotos;
        Camara = findViewById(R.id.camaraButton_AddP);
        PreviewFoto0 = findViewById(R.id.previewFoto_EditOffer);
        PreviewFoto1 = findViewById(R.id.previewFoto2_EditOffer);
        PreviewFoto2 = findViewById(R.id.previewFoto3_EditOffer);
        PreviewFoto3 = findViewById(R.id.previewFoto4_EditOffer);
        PreviewFoto4 = findViewById(R.id.previewFoto5_EditOffer);
        PreviewFotos = new ImageView[]{PreviewFoto0, PreviewFoto1, PreviewFoto2, PreviewFoto3, PreviewFoto4};

        //nombre_fotos();

        System.out.println("Entrem al bucle per carregar imatges");
        for (int i = 0; i < 5; ++i) {
                final int finalI = i;
                StorageReference Reference2 = storageRef.child("/products/" + ControladoraPresentacio.getOffer_id()).child("product_" + i);
                System.out.println(Reference2.toString());
                Reference2.getBytes(1000000000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        PreviewFotos[finalI].setImageBitmap(bmp);
                        PreviewFotos[finalI].setVisibility(View.VISIBLE);
                    }
                });
        }



        PreviewFoto0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar datos
                ControladoraEditOffer.setNombre_producto(nameEditText.getText().toString());
                ControladoraEditOffer.setNumero_Categoria(categoriaSpinner.getSelectedItemPosition());
                ControladoraEditOffer.setValor(valueEditText.getText().toString());
                ControladoraEditOffer.setEs_servicio(tipusSwitch.isChecked());
                ControladoraEditOffer.setPalabras_clave(paraulesClauEditText.getText().toString());
                //ControladoraEditOffer.setDescripcion(descripcion.getText().toString());
                //Indicamos la foto a la controladora que querremos ver
                ControladoraEditOffer.setNumero_imagen(0);
                //Nos vamos a la ventana de Preview
                Intent intent = new Intent(EditOffer.this, PreviewFotoEdit.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar datos
                ControladoraEditOffer.setNombre_producto(nameEditText.getText().toString());
                ControladoraEditOffer.setNumero_Categoria(categoriaSpinner.getSelectedItemPosition());
                ControladoraEditOffer.setValor(valueEditText.getText().toString());
                ControladoraEditOffer.setEs_servicio(tipusSwitch.isChecked());
                ControladoraEditOffer.setPalabras_clave(paraulesClauEditText.getText().toString());
                //ControladoraEditOffer.setDescripcion(descripcion.getText().toString());
                //Indicamos la foto a la controladora que querremos ver
                ControladoraEditOffer.setNumero_imagen(1);
                //Nos vamos a la ventana de Preview
                Intent intent = new Intent(EditOffer.this, PreviewFotoEdit.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar datos
                ControladoraEditOffer.setNombre_producto(nameEditText.getText().toString());
                ControladoraEditOffer.setNumero_Categoria(categoriaSpinner.getSelectedItemPosition());
                ControladoraEditOffer.setValor(valueEditText.getText().toString());
                ControladoraEditOffer.setEs_servicio(tipusSwitch.isChecked());
                ControladoraEditOffer.setPalabras_clave(paraulesClauEditText.getText().toString());
                //ControladoraEditOffer.setDescripcion(descripcion.getText().toString());
                //Indicamos la foto a la controladora que querremos ver
                ControladoraEditOffer.setNumero_imagen(2);
                //Nos vamos a la ventana de Preview
                Intent intent = new Intent(EditOffer.this, PreviewFotoEdit.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar datos
                ControladoraEditOffer.setNombre_producto(nameEditText.getText().toString());
                ControladoraEditOffer.setNumero_Categoria(categoriaSpinner.getSelectedItemPosition());
                ControladoraEditOffer.setValor(valueEditText.getText().toString());
                ControladoraEditOffer.setEs_servicio(tipusSwitch.isChecked());
                ControladoraEditOffer.setPalabras_clave(paraulesClauEditText.getText().toString());
                //ControladoraEditOffer.setDescripcion(descripcion.getText().toString());
                //Indicamos la foto a la controladora que querremos ver
                ControladoraEditOffer.setNumero_imagen(3);
                //Nos vamos a la ventana de Preview
                Intent intent = new Intent(EditOffer.this, PreviewFotoEdit.class);
                startActivity(intent);
                //finish();
            }
        });
        PreviewFoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar datos
                ControladoraEditOffer.setNombre_producto(nameEditText.getText().toString());
                ControladoraEditOffer.setNumero_Categoria(categoriaSpinner.getSelectedItemPosition());
                ControladoraEditOffer.setValor(valueEditText.getText().toString());
                ControladoraEditOffer.setEs_servicio(tipusSwitch.isChecked());
                ControladoraEditOffer.setPalabras_clave(paraulesClauEditText.getText().toString());
                //ControladoraEditOffer.setDescripcion(descripcion.getText().toString());
                //Indicamos la foto a la controladora que querremos ver
                ControladoraEditOffer.setNumero_imagen(4);
                //Nos vamos a la ventana de Preview
                Intent intent = new Intent(EditOffer.this, PreviewFotoEdit.class);
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
        /* SPINNER CATEGORIAS */
        //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias));
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        //Spinner spinner = (Spinner)findViewById(R.id.spinner_AddP);
        categoriaSpinner.setAdapter(adapter);

        //Inicializamos los editText con nuestros datos
        nameEditText.setText(ControladoraPresentacio.getOffer_name());
        categoriaSpinner.setSelection(ControladoraPresentacio.getOffer_Categoria()); //esto es para cambiar el elemento seleccionado por defecto del spinner
        tipusSwitch.setChecked(ControladoraPresentacio.isOffer_Service()); //esto es para cambiar el switch
        paraulesClauEditText.setText(ControladoraPresentacio.getOffer_PC());
        valueEditText.setText(ControladoraPresentacio.getOffer_Value().toString());

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditOffer.this, ListOffer.class);
                onNewIntent(intent);
                //startActivity(intent);
                finish();
            }
        });

        Modify_Offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean okay = false;
                //Comprovaciones de que ha puesto cosas
                if (nameEditText.length() == 0) {
                    String texterror = getString(R.string.add_product_no_hay_nombre);
                    Toast toast = Toast.makeText(EditOffer.this, texterror, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (paraulesClauEditText.length() == 0) {
                        String texterror = getString(R.string.add_product_no_hay_palabras_clave);
                        Toast toast = Toast.makeText(EditOffer.this, texterror, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        okay = true;
                    }
                }
                if (okay) {
                    RequestEditOffer(categoriaSpinner, tipusSwitch, tipus, id, nameEditText, paraulesClauEditText, valueEditText);
                }
            }
        });
    }
    private void nombre_fotos()
    {
        StorageReference Reference = storageRef.child("/products/"+ControladoraPresentacio.getOffer_id());
        Reference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                System.out.println("The list is : " + listResult.getItems().size());
                numImages[0] = listResult.getItems().size();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println( "error");
            }});
    }
    private void RequestEditOffer(Spinner categoriaSpinner, Switch tipusSwitch, String[] tipus, String id, EditText nameEditText, EditText paraulesClauEditText, EditText valueEditText) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(EditOffer.this);

        if(tipusSwitch.isChecked()) tipus[0] = "Servei";
        else tipus[0] = "Producte";

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/modifyoffer?" +
                "id=" + id + "&" +
                "name=" + nameEditText.getText().toString() + "&" +
                "category=" + categoriaSpinner.getSelectedItemPosition() + "&" +
                "type=" + tipus[0] + "&" +
                "keywords=" + paraulesClauEditText.getText()+ "&" +
                "value=" + valueEditText.getText()+"&"+
                "description= " + "hola";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("0")) { //Account modified successfully
                            String offer_modified_successfully = getString(R.string.offer_modified_successfully);
                            Toast toast = Toast.makeText(getApplicationContext(), offer_modified_successfully, Toast.LENGTH_SHORT);
                            toast.show();

                            //Volvemos a User
                            Intent intent = new Intent(EditOffer.this, ListOffer.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(response.equals("3")) { //Account modified successfully
                            String offer_empty_values = getString(R.string.empty_values);
                            Toast toast = Toast.makeText(getApplicationContext(), offer_empty_values, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else { //response == "1" No such user in the database
                            String texterror = getString(R.string.error);
                            Toast toast = Toast.makeText(EditOffer.this, texterror, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String texterror = getString(R.string.error);
                Toast toast = Toast.makeText(EditOffer.this, texterror, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
