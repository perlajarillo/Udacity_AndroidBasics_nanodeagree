package com.example.android.musicplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button b1, b2, b3;
    public int maxVolume = 50;
    public float currVolume = 0.2f;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.play_button);
        b2 = (Button) findViewById(R.id.pause_button);
        b3 = (Button) findViewById(R.id.volume_up_button);
        mediaPlayer = MediaPlayer.create(this, R.raw.theuglydance);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(getApplicationContext(), "I am done!", Toast.LENGTH_SHORT).show();

                    }

                });

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Turning the volume up", Toast.LENGTH_SHORT).show();
                float log1 = (float) (Math.log(maxVolume - currVolume) / Math.log(maxVolume));
                mediaPlayer.setVolume(log1, log1);
                Log.v("volume", Float.toString(log1));
            }
        });
    }
}
