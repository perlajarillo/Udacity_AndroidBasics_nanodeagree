package com.example.android.musicestructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ImageView imghome = (ImageView) findViewById(R.id.imghome);
        //Set the clicklistener on the imghome View
        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeIntent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(HomeIntent);
            }
        });
    }
}
