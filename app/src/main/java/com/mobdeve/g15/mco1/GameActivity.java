package com.mobdeve.g15.mco1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private RecyclerView rvPlayer1;
    private RecyclerView rvPlayer2;

    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;
    private GameAdapterPlayer1 adapter1;
    private GameAdapterPlayer2 adapter2;

    private ArrayList<Card> hand1;
    private ArrayList<Card> hand2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        this.initRecyclerView();
    }

    private void initRecyclerView(){
        this.rvPlayer1 = findViewById(R.id.rv_player);
        this.rvPlayer2 = findViewById(R.id.rv_opponent);

        //Log.i("GAMEACTIVITY", this.rvPlayer1.toString());

        this.layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        this.rvPlayer1.setLayoutManager(this.layoutManager1);
        this.rvPlayer2.setLayoutManager(this.layoutManager2);

        KingPlayerHelper kingHelper = new KingPlayerHelper();
        this.hand1 = kingHelper.initializeData();

        SlavePlayerHelper slaveHelper = new SlavePlayerHelper();
        this.hand2 = slaveHelper.initializeData();

        this.adapter1 = new GameAdapterPlayer1(this.hand1);
        this.rvPlayer1.setAdapter(this.adapter1);

        this.adapter2 = new GameAdapterPlayer2(this.hand2);
        this.rvPlayer2.setAdapter(this.adapter2);

    }
}




