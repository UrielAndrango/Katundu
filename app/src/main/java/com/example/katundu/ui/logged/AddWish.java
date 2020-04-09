package com.example.katundu.ui.logged;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.ControladoraPresentacio;

public class AddWish extends AppCompatActivity {

    String[] categorias = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);
        //Escondemos la Action Bar porque usamos la ToolBar, aunque podriamos usar la ActionBar
        getSupportActionBar().hide();

        final ImageView Atras = findViewById(R.id.AddWish_Atras);
        final Button Add_Wish = findViewById(R.id.ok_button_AddWish);
        final String username = ControladoraPresentacio.getUsername();
        final EditText nameEditText = findViewById(R.id.editTextNom_AddWish);
        final Spinner categoriaSpace = findViewById(R.id.spinner_AddWish);
        final String[] categoria = {getString(R.string.add_product_category_technology)};
        final Switch tipusSwitch = findViewById(R.id.switch_wish);
        final String[] tipus = {"Producte"};
        final EditText paraulesClauEditText = findViewById(R.id.editTextParaulesClau_AddWish);
        final EditText valueEditText = findViewById(R.id.editTextValor_AddW);

        //Inicilizamos las categorias
        categorias[0] = getString(R.string.add_product_category_technology);
        categorias[1] = getString(R.string.add_product_category_home);
        categorias[2] = getString(R.string.add_product_category_beauty);
        categorias[3] = getString(R.string.add_product_category_sports);
        categorias[4] = getString(R.string.add_product_category_fashion);
        categorias[5] = getString(R.string.add_product_category_leisure);
        categorias[6] = getString(R.string.add_product_category_transport);
        /* SPINNER CATEGORIAS */
        Spinner spinner = (Spinner) findViewById(R.id.spinner_AddWish);
        //spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias));
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        //Spinner spinner = (Spinner)findViewById(R.id.spinner_AddP);
        spinner.setAdapter(adapter);

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWish.this, Add.class);
                onNewIntent(intent);
                //startActivity(intent);
                finish();
            }
        });

        Add_Wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean okay = false;
                //Comprovaciones de que ha puesto cosas
                if (nameEditText.length() == 0) {
                    String texterror = getString(R.string.add_product_no_hay_nombre);
                    Toast toast = Toast.makeText(AddWish.this, texterror, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (paraulesClauEditText.length() == 0) {
                        String texterror = getString(R.string.add_product_no_hay_palabras_clave);
                        Toast toast = Toast.makeText(AddWish.this, texterror, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        okay = true;
                    }
                }
                if (okay) {
                    RequestAddWish(categoriaSpace, categoria, tipusSwitch, tipus, username, nameEditText, paraulesClauEditText, valueEditText);
                }
            }
        });
    }

    private void RequestAddWish(Spinner categoriaSpace, String[] categoria, Switch tipusSwitch, String[] tipus, String username, EditText nameEditText, EditText paraulesClauEditText, EditText valueEditText) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(AddWish.this);

        if(categoriaSpace.getSelectedItemPosition() == 0) categoria[0] = getString(R.string.add_product_category_technology);
        else if(categoriaSpace.getSelectedItemPosition() == 1) categoria[0] = getString(R.string.add_product_category_home);
        else if(categoriaSpace.getSelectedItemPosition() == 2) categoria[0] = getString(R.string.add_product_category_beauty);
        else if(categoriaSpace.getSelectedItemPosition() == 3) categoria[0] = getString(R.string.add_product_category_sports);
        else if(categoriaSpace.getSelectedItemPosition() == 4) categoria[0] = getString(R.string.add_product_category_fashion);
        else if(categoriaSpace.getSelectedItemPosition() == 5) categoria[0] = getString(R.string.add_product_category_leisure);
        else if(categoriaSpace.getSelectedItemPosition() == 6) categoria[0] = getString(R.string.add_product_category_transport);

        if(tipusSwitch.isChecked()) tipus[0] = "Servei";
        else tipus[0] = "Producte";

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/addwish?" +
                "user=" + username + "&" +
                "name=" + nameEditText.getText() + "&" +
                "category=" + categoria[0] + "&" +
                "type=" + tipus[0] + "&" +
                "keywords=" + paraulesClauEditText.getText()+ "&" +
                "value=" + valueEditText.getText();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("-1")) { //wish added successfully
                            ControladoraPresentacio.setWish_id(response);
                            ControladoraPresentacio.afegir_wish_id(response);

                            String wish_added_successfully = getString(R.string.wish_added_successfully);//TODO: actualitzar string, ara no ho puc fer perque falta fer pull
                            Toast toast = Toast.makeText(getApplicationContext(), wish_added_successfully, Toast.LENGTH_SHORT);
                            toast.show();

                            //Volvemos a User
                            Intent intent = new Intent(AddWish.this, ListOffer.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {  //TODO: aixo ho podem treure?
                String texterror = getString(R.string.error);
                Toast toast = Toast.makeText(AddWish.this, texterror, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
