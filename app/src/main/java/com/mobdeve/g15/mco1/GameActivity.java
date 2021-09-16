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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

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

    private int round;
    private int roundMax;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String userId;
    private DatabaseReference reference;

    private static View CL_game;
    private int highscore;

    private TextView roundsDisplay;
    private ImageView playerImage;
    private String avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        roundMax = 6;
        this.mAuth = FirebaseAuth.getInstance();
        this.user = this.mAuth.getCurrentUser();
        this.userId = this.user.getUid();
        avatar = "kaiji";

        reference = FirebaseDatabase.getInstance("https://egame-55b1c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        reference.child(this.userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                roundMax = Integer.parseInt(snapshot.child("rounds").getValue().toString());
                avatar = snapshot.child("image").getValue().toString();
                highscore = Integer.parseInt(snapshot.child("highscore").getValue().toString());

                playerImage = findViewById(R.id.iv_gamePlayerImage);
                if(avatar.equals("kaiji"))
                {
                    playerImage.setImageResource(R.drawable.kaiji);
                }

                else if(avatar.equals("kazutaka_hyodo"))
                {
                    playerImage.setImageResource(R.drawable.kazutaka_hyodo);
                }

                else {
                    playerImage.setImageResource(R.drawable.kazuya_hyodou);
                }
                Log.i("IMAGE", avatar);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


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
        roundsDisplay = findViewById(R.id.tv_gameRoundsNum);

        CL_game = findViewById(R.id.CL_game);
        //Every round
        tv_score1.setText(String.valueOf(0));
        tv_score2.setText(String.valueOf(0));




        //Log.i("IMAGE ACTUAL", String.valueOf(R.drawable.kaiji));

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

            this.onButtonShowPopupWindowClick(this.activityView, R.layout.draw_round);
        }

        round++;
        roundsDisplay.setText(String.valueOf(round+1));


        //change based of settings
        if (round < roundMax) {

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

            if(score1 > highscore)
                reference.child(userId).child("highscore").setValue(score1);

            if (score1 > score2) {
                Intent intent = new Intent(GameActivity.this, WinActivity.class);
                GameActivity.this.startActivity(intent);
            } else if(score2 > score1) {
                Intent intent = new Intent(GameActivity.this, LoseActivity.class);
                GameActivity.this.startActivity(intent);
            } else {
                Intent intent = new Intent(GameActivity.this, activity_tie.class);
                GameActivity.this.startActivity(intent);
            }

        }


    }


    public void onButtonShowPopupWindowClick(View view, int layout) {


        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(layout, null);

        Log.i("GAME", "test");

        int width = dpToPx(300, this);
        int height = dpToPx(300, this);
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void showInstructions(View view) {


        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.instructions, null);

        Log.i("GAME", "test");

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


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




