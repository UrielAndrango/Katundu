
package com.example.katundu.ui.logged;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.example.katundu.R;
        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListChat extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(ListChat.this, MenuPrincipal.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //onNewIntent(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    overridePendingTransition(0,0);

                case R.id.navigation_surprise:
                    return true;
                case R.id.navigation_add:
                    Intent intent_2 = new Intent(ListChat.this, Add.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //onNewIntent(intent);
                    intent_2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent_2);
                    overridePendingTransition(0,0);
                    //finish();

                    //Si lo hacemos con ventanas independientes, quitamos los TRUES
                    //return true;
                case R.id.navigation_xat:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);
        //Escondemos la Action Bar porque usamos la ToolBar
        getSupportActionBar().hide();

        final FloatingActionButton search = findViewById(R.id.new_chat);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_xat);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListChat.this, SearchUserChat.class);
                startActivity(intent);
                //finish();
            }
        });
    }
}
