package com.example.katundu.ui.logged;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.katundu.R;
import com.example.katundu.ui.ControladoraPresentacio;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditarPerfil extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    ImageView PreviewFoto;
    Button add_picButton, delete_picButton;
    Uri image_uri;
    ImageView profile_pic = ControladoraPresentacio.getProfile_picture();
    ImageView preview_profile_pic;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();
        if (mAuth.getCurrentUser() == null) signInAnonymously();

        final ImageView Atras = findViewById(R.id.DeleteAccount_Atras);
        final Button SaveButton = findViewById(R.id.save_button);
        final TextView usernameEditText = findViewById(R.id.TextNomUsuari);
        final EditText nameEditText = findViewById(R.id.editTextNom);
        final EditText passwordEditText = findViewById(R.id.editTextContrasenya);
        final EditText latitudeEditText = findViewById(R.id.editTextLatitud);
        final EditText longitudeEditText = findViewById(R.id.editTextLongitud);
        final EditText maximumDistanceEditText = findViewById(R.id.editTextMaximumDistance);
        final EditText descriptionEditText = findViewById(R.id.editTextDescription);
        final EditText birthdateEditText = findViewById(R.id.editTextBirthdate);

        //boto afegir foto
        add_picButton = findViewById(R.id.add_pic);
        add_picButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                add_picButton.setVisibility(View.GONE);
                delete_picButton.setVisibility(View.VISIBLE);
            }
        });

        final StorageReference imageRef  = storageRef.child("/users/" + ControladoraPresentacio.getUsername());
        System.out.println("IMAGEREF: " + imageRef.toString());

        //boto esborrar foto
        delete_picButton = findViewById(R.id.delete_pic);
        delete_picButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO eliminar fotiko
                ControladoraPresentacio.setProfile_picture(null);
                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(EditarPerfil.this, EditarPerfil.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        String texterror = getString(R.string.preview_fotoE_error);
                        Toast toast = Toast.makeText(EditarPerfil.this, texterror, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                add_picButton.setVisibility(View.VISIBLE);
                delete_picButton.setVisibility(View.GONE);
            }
        });

        preview_profile_pic = findViewById(R.id.foto_perfil);
        System.out.println("en teoria ha de sortir true " + (preview_profile_pic == null));

        //agafem la foto i mirem si existeix o no
        imageRef.getBytes(1000000000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                System.out.println("ON CREATE SUCCESS: " + imageRef.toString());
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                //Redondeamos las esquinas de las fotos
                bmp = ControladoraPresentacio.getRoundedCornerBitmap(bmp,64*2);
                preview_profile_pic.setImageBitmap(bmp);
                System.out.println("en teoria ha de sortir false " + (preview_profile_pic == null));
                preview_profile_pic.setVisibility(View.VISIBLE);
                add_picButton.setVisibility(View.GONE);
                delete_picButton.setVisibility(View.VISIBLE);
                System.out.println("TENIM FOTIKO");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                add_picButton.setVisibility(View.VISIBLE);
                delete_picButton.setVisibility(View.GONE);
                System.out.println("NO TENIM FOTIKO");
            }
        });

        if (profile_pic != null) {
            add_picButton.setVisibility(View.GONE);
            delete_picButton.setVisibility(View.VISIBLE);
            profile_pic.setVisibility(View.VISIBLE);
            ImageView imageView = profile_pic;
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            bmp = ControladoraPresentacio.getRoundedCornerBitmap(bmp,1024);
            preview_profile_pic.setImageBitmap(bmp);
            preview_profile_pic.setVisibility(View.VISIBLE);
        }
        else {
            delete_picButton.setVisibility(View.GONE);
            add_picButton.setVisibility(View.VISIBLE);
            //TODO mirar si cal posar algo x a tenir la foto x defecte
        }



        //DATOS DEL USUARIO
        usernameEditText.setText(ControladoraPresentacio.getUsername());
        nameEditText.setText(ControladoraPresentacio.getNom_real());
        passwordEditText.setText(ControladoraPresentacio.getPassword());
        latitudeEditText.setText(ControladoraPresentacio.getLatitud());
        longitudeEditText.setText(ControladoraPresentacio.getLongitud());
        maximumDistanceEditText.setText(ControladoraPresentacio.getDistanciaMaxima());
        descriptionEditText.setText(ControladoraPresentacio.getDescriptionUser());
        birthdateEditText.setText(ControladoraPresentacio.getBirthdate());

        Atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarPerfil.this, Ajustes.class);
                onNewIntent(intent);
                finish();
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = true;
                if (passwordEditText.getText().length() < 8) {
                    ok = false;
                    String texterror = getString(R.string.invalid_password);
                    Toast toast = Toast.makeText(EditarPerfil.this, texterror, Toast.LENGTH_SHORT);
                    toast.show();
                }
                if (birthdateEditText.getText().length() > 0 && !valid(birthdateEditText.getText().toString())) {
                    ok = false;
                    String texterror = getString(R.string.invalid_birthdate);
                    Toast toast = Toast.makeText(EditarPerfil.this, texterror, Toast.LENGTH_SHORT);
                    toast.show();
                }
                if (ok) RequestEditarPerfil(usernameEditText, passwordEditText, nameEditText, latitudeEditText, longitudeEditText, maximumDistanceEditText, descriptionEditText, birthdateEditText);
            }
        });
    }

    static boolean valid(String d) {
        if (d.length() != 10) return false;
        /*String day2s = new StringBuilder().append(d.charAt(0)).append(d.charAt(1)).toString();
        int day = Integer.parseInt(day2s);
        String month2s = new StringBuilder().append(d.charAt(3)).append(d.charAt(4)).toString();
        int month = Integer.parseInt(month2s);
        String year2s = new StringBuilder().append(d.charAt(6)).append(d.charAt(7)).append(d.charAt(8)).append(d.charAt(9)).toString();
        int year = Integer.parseInt(year2s);*/
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        date.setLenient(false);
        try {
            date.parse(d);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            private static final String TAG = "SignInStorage" ;
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(TAG, "signInAnonymously:FAILURE", exception);
            }
        });
    }

    private void RequestEditarPerfil(final TextView usernameEditText, final EditText passwordEditText, final EditText nameEditText,
                                     final EditText latitudeEditText, final EditText longitudeEditText, final EditText maximumDistanceEditText,
                                     final EditText descriptionEditText, final EditText birthdateEditText) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(EditarPerfil.this);

        String url = "https://us-central1-test-8ea8f.cloudfunctions.net/user-modify?" +
                "un=" + usernameEditText.getText() + "&" +
                "pw=" + passwordEditText.getText() + "&" +
                "n=" + nameEditText.getText() + "&" +
                "lat=" + latitudeEditText.getText() + "&" +
                "lon=" + longitudeEditText.getText() + "&" +
                "distanciamaxima=" + maximumDistanceEditText.getText();

        //url += "&descr=" + descriptionEditText.getText() + "&bdate=" + birthdateEditText.getText();
        System.out.println(url + "&descr=" + descriptionEditText.getText() + "&bdate=" + birthdateEditText.getText());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("0")) { //Account modified successfully
                            String account_modified_successfully = getString(R.string.account_modified_successfully);
                            Toast toast = Toast.makeText(getApplicationContext(), account_modified_successfully, Toast.LENGTH_SHORT);
                            toast.show();

                            //Actualizamos Controladora
                            ControladoraPresentacio.setUsername(usernameEditText.getText().toString());
                            ControladoraPresentacio.setPassword(passwordEditText.getText().toString());
                            ControladoraPresentacio.setNom_real(nameEditText.getText().toString());
                            ControladoraPresentacio.setLatitud(latitudeEditText.getText().toString());
                            ControladoraPresentacio.setLongitud(longitudeEditText.getText().toString());
                            ControladoraPresentacio.setDistanciaMaxima(maximumDistanceEditText.getText().toString());
                            ControladoraPresentacio.setDescriptionUser(descriptionEditText.getText().toString());
                            ControladoraPresentacio.setBirthdate(birthdateEditText.getText().toString());

                            if (preview_profile_pic != null) {
                                StorageReference imageRef = storageRef.child("/users/" + ControladoraPresentacio.getUsername());
                                System.out.println("UBICACIOOOO: " + imageRef.toString());
                                ImageView imageView = preview_profile_pic;
                                imageView.buildDrawingCache();
                                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();
                                UploadTask uploadTask = imageRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    }
                                });
                            }

                            //Volvemos a Ajustes
                            Intent intent = new Intent(EditarPerfil.this, Ajustes.class);
                            onNewIntent(intent);
                            //startActivity(intent);
                            finish();
                        }
                        else { //response == "1" No such user in the database
                            String texterror = getString(R.string.error);
                            Toast toast = Toast.makeText(EditarPerfil.this, texterror, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {  //TODO: aixo ho podem treure?
                String texterror = getString(R.string.error);
                Toast toast = Toast.makeText(EditarPerfil.this, texterror, Toast.LENGTH_SHORT);
                toast.show();
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
            //caso de si quiero seleccionar una foto que ya tengo en la galeria
            if (requestCode == PICK_IMAGE) image_uri = data.getData();

            preview_profile_pic.setImageURI(image_uri);
            ImageView imageView = preview_profile_pic;
            imageView.setImageURI(image_uri);
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            bmp = ControladoraPresentacio.getRoundedCornerBitmap(bmp,1024);
            preview_profile_pic.setImageBitmap(bmp);
            preview_profile_pic.setVisibility(View.VISIBLE);
            //Actualizamos la controladora
            ControladoraPresentacio.setProfile_picture(imageView);
            profile_pic = ControladoraPresentacio.getProfile_picture();
        }
    }
}

