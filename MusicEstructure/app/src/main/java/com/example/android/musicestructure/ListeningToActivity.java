package com.example.android.musicestructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;

public class ListeningToActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_to);
        ImageView imghome = (ImageView) findViewById(R.id.imghome);
        //Set the clicklistener on the imghome View
        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeIntent = new Intent(ListeningToActivity.this, MainActivity.class);
                startActivity(HomeIntent);
            }
        });
    }
}
