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
    private View activityView;
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
        this.activityView = getWindow().getDecorView().getRootView();

        findViewById(R.id.CL_game).post(new Runnable() {
            public void run() {

                showInstructions(activityView);
            }
        });




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
    public void updateRound(int whoWon, int index, boolean winAsSlave) {

        if (whoWon == 1) {
            if (winAsSlave) {
                score1 += 3;
                this.onButtonShowPopupWindowClick(this.activityView, R.layout.win_round3);
            }
            else {
                score1++;
                this.onButtonShowPopupWindowClick(this.activityView, R.layout.win_round);
            }

            tv_score1.setText(String.valueOf(score1));
        } else if (whoWon == -1) {

            if (winAsSlave)
            {
                score2 += 3;
                this.onButtonShowPopupWindowClick(this.activityView, R.layout.lose_round3);
            }
            else
            {
                score2++;
                this.onButtonShowPopupWindowClick(this.activityView, R.layout.lose_round);
            }

            tv_score2.setText(String.valueOf(score2));
        } else {
            hand1.remove(index);
            if (hand2.get(0).getType() == "Citizen") {
                hand2.remove(0);
            } else {
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
        if (round < 12) {

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
                Log.i("GAME OUTSIDE", "Testing 2");


            }
        } else {
            GameActivity game = new GameActivity();
            if (score1 > score2) {
                Intent intent = new Intent(GameActivity.this, WinActivity.class);
                GameActivity.this.startActivity(intent);
            } else {
                Intent intent = new Intent(GameActivity.this, LoseActivity.class);
                GameActivity.this.startActivity(intent);

            }

        }


    }


    public void onButtonShowPopupWindowClick(View view, int layout) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(layout, null);

        Log.i("GAME", "test");
        // create the popup window
        int width = dpToPx(300, this);
        int height = dpToPx(300, this);
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void showInstructions(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.instructions, null);

        Log.i("GAME", "test");
        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


}




