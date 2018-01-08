package com.example.android.webquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //This method shows the Score in a toast
    public void showScore(View view) {
        int finalScore = calculateScore();
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.scoreMessage, finalScore) + "%";
        Toasty.info(context, text, Toast.LENGTH_SHORT, true).show();
    }

    //This method calculates the Score
    private int calculateScore() {
        int score = 0;
        EditText aq1 = (EditText) findViewById(R.id.editx_aq1);
        EditText aq2 = (EditText) findViewById(R.id.editx_aq2);
        RadioButton aq3 = (RadioButton) findViewById(R.id.radio_aq3_false);
        RadioButton aq4 = (RadioButton) findViewById(R.id.radio_aq4_true);
        RadioButton aq5 = (RadioButton) findViewById(R.id.radio_aq5_1);
        RadioButton aq6 = (RadioButton) findViewById(R.id.radio_aq6_2);
        RadioButton aq7 = (RadioButton) findViewById(R.id.radio_aq7_1);
        String txtaq1 = aq1.getText().toString();
        String txtaq2 = aq2.getText().toString();
        CheckBox aq8_1 = (CheckBox) findViewById(R.id.checkbox_aq8_1);
        CheckBox aq8_2 = (CheckBox) findViewById(R.id.checkbox_aq8_2);
        CheckBox aq8_3 = (CheckBox) findViewById(R.id.checkbox_aq8_3);
        CheckBox aq8_4 = (CheckBox) findViewById(R.id.checkbox_aq8_4);
        CheckBox aq8_5 = (CheckBox) findViewById(R.id.checkbox_aq8_5);
        CheckBox aq9_1 = (CheckBox) findViewById(R.id.checkbox_aq9_1);
        CheckBox aq9_2 = (CheckBox) findViewById(R.id.checkbox_aq9_2);
        CheckBox aq9_3 = (CheckBox) findViewById(R.id.checkbox_aq9_3);
        CheckBox aq9_4 = (CheckBox) findViewById(R.id.checkbox_aq9_4);
        CheckBox aq10_1 = (CheckBox) findViewById(R.id.checkbox_aq10_1);
        CheckBox aq10_2 = (CheckBox) findViewById(R.id.checkbox_aq10_2);
        CheckBox aq10_3 = (CheckBox) findViewById(R.id.checkbox_aq10_3);
        Context context = getApplicationContext();
        //Score for question 1, we need to review if the EditText is empty first
        if (!txtaq1.isEmpty()) {
            if (txtaq1.equals("VIEWPORT")) {
                score++;
            }
        } else {
            Toasty.error(context, "There is no answer for question 1.", Toast.LENGTH_SHORT, true).show();

        }
        //Score for question 2, we need to review if the EditText is empty first
        if (!txtaq2.isEmpty()) {
            if (txtaq2.equals("$DIRTY")) {
                score++;
            }
        } else {
            Toasty.error(context, "There is no answer for question 2.", Toast.LENGTH_SHORT, true).show();

        }
        //Score for question 3
        if (aq3.isChecked()) {
            score++;
        }
        //Score for question 4
        if (aq4.isChecked()) {
            score++;
        }
        //Score for question 5
        if (aq5.isChecked()) {
            score++;
        }
        //Score for question 6
        if (aq6.isChecked()) {
            score++;
        }
        //Score for question 7
        if (aq7.isChecked()) {
            score++;
        }
        //Score for question 8
        if (!aq8_1.isChecked() && aq8_2.isChecked() && aq8_3.isChecked() && !aq8_4.isChecked() && aq8_5.isChecked()) {
            score++;
        }
        //Score for question 9
        if (!aq9_1.isChecked() && aq9_2.isChecked() && !aq9_3.isChecked() && aq9_4.isChecked()) {
            score++;
        }
        //Score for question 10
        if (aq10_1.isChecked() && !aq10_2.isChecked() && aq10_3.isChecked()) {
            score++;
        }
        //Returning score
        return score * 10;
    }
}
