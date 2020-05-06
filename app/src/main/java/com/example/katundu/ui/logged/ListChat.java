
package com.example.katundu.ui.logged;

        import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.katundu.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListChat extends AppCompatActivity {
        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(ListChat.this, MenuPrincipal.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //onNewIntent(intent);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    //finish();
                    break;
                case R.id.navigation_surprise:
                    return true;
                case R.id.navigation_add:
                    Intent intent_2 = new Intent(ListChat.this, Add.class);
                    intent_2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent_2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent_2);
                    overridePendingTransition(0,0);
                    //finish();
                    break;
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

        BottomNavigationView navView = findViewById(R.id.nav_view_chat);
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
