package com.mobdeve.g15.mco1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class GameAdapterPlayer1 extends RecyclerView.Adapter<ViewHolderPlayer1> {
    private ArrayList<Card> hand;
    private ArrayList<Card> choices;
    private GameActivity[] game;


    public GameAdapterPlayer1(ArrayList<Card> hand, ArrayList<Card> opponentHand, GameActivity[] game)
    {
        this.hand = hand;
        this.choices = opponentHand;
        this.game = game;
        Log.i("ADAPTER", this.game.toString());

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderPlayer1 onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cards, parent, false);

        ViewHolderPlayer1 holder = new ViewHolderPlayer1(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderPlayer1 holder, int position) {
        holder.setCard(hand.get(position).getImage());

        holder.setCardOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = hand.get(position).getType();
                Collections.shuffle(choices);
                String opponentType = choices.get(0).getType();

                if(type.equals("Emperor") && opponentType.equals("Citizen"))
                {
                    Log.i("Adapter", "You win");
                    Log.i("Adapter", "Type: " + type);
                    Log.i("Adapter", "OpponentType: " + opponentType);
                    game[0].updateRound(1, position, false);
                    Toast.makeText(v.getContext(), "Hirotomi Citizens bow to your Emperor's might! you win! you gain +1 point", Toast.LENGTH_LONG).show();
                }

                else if(type.equals("Emperor") && opponentType.equals("Slave"))
                {
                    Log.i("Adapter", "You lose");
                    Log.i("Adapter", "Type: " + type);
                    Log.i("Adapter", "OpponentType: " + opponentType);
                    game[0].updateRound(-1, position, true);
                    Toast.makeText(v.getContext(), "Hirotomi Slave killed your Emperor! you lose! Hirotomi gains +3 points", Toast.LENGTH_LONG).show();
                }

                else if(type.equals("Citizen") && opponentType.equals("Slave"))
                {
                    Log.i("Adapter", "You win B");
                    Log.i("Adapter", "Type: " + type);
                    Log.i("Adapter", "OpponentType: " + opponentType);
                    game[0].updateRound(1, position, false);
                    Toast.makeText(v.getContext(), "Your Citizens have punished Hirotomi's slave through death! you win! you gain +1 points", Toast.LENGTH_LONG).show();
                }


                else if(type == "Slave" && opponentType == "Emperor")
                {
                    Log.i("Adapter", "You Win Slave");
                    Log.i("Adapter", "Type: " + type);
                    Log.i("Adapter", "OpponentType: " + opponentType);
                    game[0].updateRound(1, position, true);
                    Toast.makeText(v.getContext(), "Your Slave's insurrection has toppled Hirotomi's Emperor! you win! you gain +3 points", Toast.LENGTH_LONG).show();
                }

                else if(type == "Citizen" && opponentType == "Emperor")
                {
                    Log.i("Adapter", "You Lose Citizen");
                    Log.i("Adapter", "Type: " + type);
                    Log.i("Adapter", "OpponentType: " + opponentType);
                    game[0].updateRound(-1, position, false);
                    Toast.makeText(v.getContext(), "Hirotomi's Emperor executes your citizen for treason! you lose! Hirotomi gains +1 point", Toast.LENGTH_LONG).show();
                }

                else if(type == "Slave" && opponentType == "Citizen")
                {
                    Log.i("Adapter", "You lose Slave");
                    Log.i("Adapter", "Type: " + type);
                    Log.i("Adapter", "OpponentType: " + opponentType);
                    game[0].updateRound(-1, position, false);
                    Toast.makeText(v.getContext(), "Your Slave was stoned by Hirotomi's Citizens! you lose! Hirotomi gains +1 points", Toast.LENGTH_LONG).show();
                }

                else {
                    Log.i("Adapter", "Tie");
                    Log.i("Adapter", "Type: " + type);
                    Log.i("Adapter", "OpponentType: " + opponentType);
                    game[0].updateRound(0, position, false);
                    Toast.makeText(v.getContext(), "Hirotomi used Citizen! it's a draw! +0 points", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return this.hand.size();
    }


}
