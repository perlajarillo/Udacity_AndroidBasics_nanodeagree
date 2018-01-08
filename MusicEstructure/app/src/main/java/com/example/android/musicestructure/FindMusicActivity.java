package com.example.android.musicestructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import static android.R.id.list;

public class FindMusicActivity extends AppCompatActivity {
    private String artists[] = new String[]{"Florence and the Machine", "James Blunt", "The Do"};
    private Integer[] imgid = {
            R.drawable.florence,
            R.drawable.james,
            R.drawable.thedo
    };
    private ListView artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_music);
        ArtistListAdapter adapter = new ArtistListAdapter(this, artists, imgid);
        artistList = (ListView) findViewById(R.id.list);
        artistList.setAdapter(adapter);
        artistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem = artists[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                //When the user select a song will see the Listening to activity
                Intent ListeningtoIntent = new Intent(FindMusicActivity.this, ListeningToActivity.class);
                startActivity(ListeningtoIntent);
            }
        });
        ImageView imghome = (ImageView) findViewById(R.id.imghome);
        //Set the clicklistener on the imghome View
        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomeIntent = new Intent(FindMusicActivity.this, MainActivity.class);
                startActivity(HomeIntent);
            }
        });
    }
}
