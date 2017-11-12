package com.example.android.taekwondoscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int scoreContenderA=0;
    int scoreContenderB=0;
    int foulsA=0;
    int foulsB=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Score Knock-Down: 3 points to Contender A.
     */
    public void plusKnockDownCA(View view)
    {
        scoreContenderA+=3;
        displayForContenderA(scoreContenderA);
        isThisContenderTheWinner(scoreContenderA,"A");
    }
    /**
     * Score Head or Neak: 2 points to Contender A.
     */
    public void plusHeadNeakCA(View view)
    {
        scoreContenderA+=2;
        displayForContenderA(scoreContenderA);
        isThisContenderTheWinner(scoreContenderA,"A");
    }

    /**
     * Score torso: 1 point to Contender A.
     */
    public void plusTorsoCA(View view)
    {
        scoreContenderA+=1;
        displayForContenderA(scoreContenderA);
        isThisContenderTheWinner(scoreContenderA,"A");
    }

    /**
     * Points are deducted for fouls such as hitting below the belt, hitting the back and hitting behind the head.
     */
    public void foulCA(View view)
    {
        scoreContenderA-=1;
        foulsA+=1;
        displayForContenderA(scoreContenderA);
        displayFoulsA(foulsA);
        isThisContenderTheWinner(scoreContenderA,"A");
    }



    /**
     * Displays the given score for Contender A.
     */
    public void displayForContenderA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.contender_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the fouls for Contender A.
     */
    public void displayFoulsA(int score) {
        TextView scoreFouls = (TextView) findViewById(R.id.contender_a_fouls);
        scoreFouls.setText("Fouls: "+String.valueOf(score));
    }

    /**
     * Score Knock-Down: 3 points to Contender B
     */
    public void plusKnockDownCB(View view)
    {
        scoreContenderB+=3;
        displayForContenderB(scoreContenderB);
        isThisContenderTheWinner(scoreContenderB,"B");
    }
    /**
     * Score Head or Neak: 2 points to Contender B.
     */
    public void plusHeadNeakCB(View view)
    {
        scoreContenderB+=2;
        displayForContenderB(scoreContenderB);
        isThisContenderTheWinner(scoreContenderB,"B");
    }

    /**
     * Score torso: 1 point to Contender B.
     */
    public void plusTorsoCB(View view)
    {
        scoreContenderB+=1;
        displayForContenderB(scoreContenderB);
        isThisContenderTheWinner(scoreContenderB,"B");
    }

    /**
     * Points are deducted for fouls such as hitting below the belt, hitting the back and hitting behind the head.
     */
    public void foulCB(View view)
    {
        scoreContenderB-=1;
        foulsB+=1;
        displayFoulsB(foulsB);
        displayForContenderB(scoreContenderB);
        isThisContenderTheWinner(scoreContenderB,"B");
    }

    /**
     * Displays the fouls for Contender B.
     */
    public void displayFoulsB(int fouls) {
        TextView scoreFouls = (TextView) findViewById(R.id.contender_b_fouls);
        scoreFouls.setText("Fouls: "+String.valueOf(fouls));
    }

    /**
     * Displays the given score for Contender B.
     */
    public void displayForContenderB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.contender_b_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Reset score for the Team B and the Team A.
     */
    public void resetScore(View view)
    {
        scoreContenderB=0;
        scoreContenderA=0;
        foulsA=0;
        foulsB=0;
        displayForContenderB(scoreContenderB);
        displayForContenderA(scoreContenderA);
        displayFoulsA(foulsA);
        displayFoulsB(foulsB);
        findViewById(R.id.btnknockDownA).setEnabled(true);
        findViewById(R.id.btnHeadNeakA).setEnabled(true);
        findViewById(R.id.btntorsoA).setEnabled(true);
        findViewById(R.id.btnfoulB).setEnabled(true);

        findViewById(R.id.btnknockDownB).setEnabled(true);
        findViewById(R.id.btnHeadNeakB).setEnabled(true);
        findViewById(R.id.btntorsoB).setEnabled(true);
        findViewById(R.id.btnfoulA).setEnabled(true);

        TextView winnerView = (TextView) findViewById(R.id.winnerText);
        winnerView.setText("");

    }

    //Reviewing if after score the contender is winning the match
    public void isThisContenderTheWinner(int score, String c)
    {
        TextView winnerView = (TextView) findViewById(R.id.winnerText);
        String winner="";
        //If a fighter reaches 12 points wins the match.
        if (score>=12)
        {
            winner="The winner is Contender "+ c;
            disableButtons();

        }
        //If a fighter gets seven points ahead he or she wins the match.
        if ((scoreContenderB-scoreContenderA)>=7) {
            winner = "The winner is Contender B";
            disableButtons();
        }
        if ((scoreContenderA-scoreContenderB)>=7) {
            winner = "The winner is Contender A";
            disableButtons();
        }

        winnerView.setText(String.valueOf(winner));

    }

    //Disable buttons to avoid score points when a contender is already declared the winner
    public void disableButtons(){
        findViewById(R.id.btnknockDownA).setEnabled(false);
        findViewById(R.id.btnHeadNeakA).setEnabled(false);
        findViewById(R.id.btntorsoA).setEnabled(false);
        findViewById(R.id.btnfoulA).setEnabled(false);



        findViewById(R.id.btnknockDownB).setEnabled(false);
        findViewById(R.id.btnHeadNeakB).setEnabled(false);
        findViewById(R.id.btntorsoB).setEnabled(false);
        findViewById(R.id.btnfoulB).setEnabled(false);

    }
}
