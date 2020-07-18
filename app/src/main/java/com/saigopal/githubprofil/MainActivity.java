package com.saigopal.githubprofil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = findViewById(R.id.Name_Text);
        final Button search = findViewById(R.id.Search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NameSearch = name.getText().toString().trim();

                if (NameSearch.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter user name", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Functions.isNetworkAvailable(MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this,Profiles.class);
                        intent.putExtra("SearchName",NameSearch);
                        startActivity(intent);
                    }
                    else {
                        Snackbar.make(search,"No connection",Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }


    }