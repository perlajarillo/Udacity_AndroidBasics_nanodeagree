package com.example.android.musicestructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgfind = (ImageView) findViewById(R.id.imgfind);
        imgfind.setOnClickListener(this);
        ImageView imglistening = (ImageView) findViewById(R.id.imglistening);
        imglistening.setOnClickListener(this);
        ImageView imgfavorites = (ImageView) findViewById(R.id.imgfavorites);
        //Set the clicklistener on the imgfavorites View
        imgfavorites.setOnClickListener(this);
        ImageView imghistory = (ImageView) findViewById(R.id.imghistory);
        //Set the clicklistener on the imgfhistory View
        imghistory.setOnClickListener(this);
        ImageView imgcollection = (ImageView) findViewById(R.id.imgcollection);
        //Set the clicklistener on the imgcollection View
        imgcollection.setOnClickListener(this);
        ImageView imgpayment = (ImageView) findViewById(R.id.imgpayment);
        //Set the clicklistener on the imgpayment View
        imgpayment.setOnClickListener(this);

    }

    public void onClick(View v) {
        // Perform action on click
        switch (v.getId()) {
            case R.id.imgfind:
                Intent findIntent = new Intent(MainActivity.this, FindMusicActivity.class);
                startActivity(findIntent);
                break;
            case R.id.imglistening:
                Intent listeningIntent = new Intent(MainActivity.this, ListeningToActivity.class);
                startActivity(listeningIntent);
                break;
            case R.id.imgfavorites:
                Intent favoritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(favoritesIntent);
                break;
            case R.id.imghistory:
                Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
                break;
            case R.id.imgcollection:
                Intent collectionIntent = new Intent(MainActivity.this, CollectionActivity.class);
                startActivity(collectionIntent);
                break;
            case R.id.imgpayment:
                Intent paymentIntent = new Intent(MainActivity.this, PaymentActivity.class);
                startActivity(paymentIntent);
                break;
        }
    }
}
