package com.mobdeve.g15.mco1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private static RecyclerView rvPlayer1;
    private static RecyclerView rvPlayer2;
    private static GameActivity[] gameArray;

    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;
    private static GameAdapterPlayer1 adapter1;
    private static GameAdapterPlayer2 adapter2;

    private static ArrayList<Card> hand1;
    private static ArrayList<Card> hand2;

    private static int score1;
    private static int score2;

    private static TextView tv_score1;
    private static TextView tv_score2;

    private static int round;

    private static View CL_game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        this.initRecyclerView();
        //this.tv_score1 = findViewById(R.id.tv_score_player);
        //this.tv_score2 = findViewById(R.id.tv_score_opponent);
        score1 = 0;
        score2 = 0;
        round = 0;


    }



    private void initRecyclerView(){
        rvPlayer1 = findViewById(R.id.rv_player);
        rvPlayer2 = findViewById(R.id.rv_opponent);
        gameArray = new GameActivity[2];
        Log.i("Main Activity", gameArray.toString());
        gameArray[0] = this;
        Log.i("Main Activity", gameArray[0].toString());

        tv_score1 = findViewById(R.id.tv_score_player);
        tv_score2 = findViewById(R.id.tv_score_opponent);

        CL_game = findViewById(R.id.CL_game);
        //Every round
        tv_score1.setText(String.valueOf(0));
        tv_score2.setText(String.valueOf(0));

        //Log.i("GAMEACTIVITY", this.rvPlayer1.toString());

        this.layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvPlayer1.setLayoutManager(this.layoutManager1);
        rvPlayer2.setLayoutManager(this.layoutManager2);

        KingPlayerHelper kingHelper = new KingPlayerHelper();
        hand1 = kingHelper.initializeData();

        SlavePlayerHelper slaveHelper = new SlavePlayerHelper();
        hand2 = slaveHelper.initializeData();

        //Call these 4 statements again with updated hand1 and hand2 every round
        adapter1 = new GameAdapterPlayer1(hand1, hand2, gameArray);
        rvPlayer1.setAdapter(adapter1);
        adapter2 = new GameAdapterPlayer2(hand2);
        rvPlayer2.setAdapter(adapter2);

    }

    //if 0 tie
    //if 1 player win
    //-1 player lose
    public void updateRound(int whoWon, int index, boolean winAsSlave)
    {

        if(whoWon == 1)
        {
            if(winAsSlave)
                score1 += 3;
            else
                score1++;

            tv_score1.setText(String.valueOf(score1));
        }
        else if(whoWon == -1)
        {
            if(winAsSlave)
                score2 += 3;
            else
                score2++;

            tv_score2.setText(String.valueOf(score2));
        }

        else{
            hand1.remove(index);
            if(hand2.get(0).getType() == "Citizen") {
                hand2.remove(0);
            }

            else
            {
                hand2.remove(1);
            }



            Log.i("GAMEACTIVITY", hand1.toString());
            Log.i("GAMEACTIVITY", hand2.toString());
            adapter1 = new GameAdapterPlayer1(hand1, hand2, gameArray);
            rvPlayer1.setAdapter(adapter1);
            adapter2 = new GameAdapterPlayer2(hand2);
            rvPlayer2.setAdapter(adapter2);
        }

        round++;

        //change based of settings
        if(round < 12) {
            if (round % 3 == 0) {
                KingPlayerHelper kingHelper = new KingPlayerHelper();
                SlavePlayerHelper slaveHelper = new SlavePlayerHelper();

                if (round % 6 == 0) {
                    hand1 = kingHelper.initializeData();
                    hand2 = slaveHelper.initializeData();
                } else {
                    hand2 = kingHelper.initializeData();
                    hand1 = slaveHelper.initializeData();
                }


                adapter1 = new GameAdapterPlayer1(hand1, hand2, gameArray);
                rvPlayer1.setAdapter(adapter1);
                adapter2 = new GameAdapterPlayer2(hand2);
                rvPlayer2.setAdapter(adapter2);
            }
        }

        else {
            GameActivity game = new GameActivity();
            if(score1 > score2){
                Intent intent = new Intent(GameActivity.this, WinActivity.class);
                GameActivity.this.startActivity(intent);
            }
            else{
                Intent intent = new Intent(GameActivity.this, LoseActivity.class);
                GameActivity.this.startActivity(intent);

            }

        }



    }

    public void onLOSEClick(View view) {



    }


    public void onWINClick() {
        Intent intent = new Intent(GameActivity.this, WinActivity.class);
        GameActivity.this.startActivity(intent);
    }
}




